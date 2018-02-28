package com.imooc.miaosha.config.filter;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.imooc.miaosha.access.AccessLimit;
import com.imooc.miaosha.access.context.UserContext;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.redis.key.AccessKey;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.MiaoShaUserService;
import com.imooc.miaosha.service.impl.MiaoShaUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Objects;

/**
 * @author yangqian
 * @date 2018/2/19
 */
@Service
public class AccessInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private MiaoShaUserService miaoShaUserService;
    @Autowired
    private RedisService redisService;

    /**
     * 方法执行之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (handler instanceof HandlerMethod) {
            MiaoshaUser user = getUser(request, response);
            UserContext.setUser(user);

            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            if (Objects.isNull(accessLimit)) {
                return true;
            }

            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();

            String key = request.getRequestURI();

            if (needLogin) {
                if (Objects.isNull(user)) {
                    response.sendRedirect(request.getContextPath()+"/login/to_login");
                    return false;
                }
                key += "_" + user.getId();
            } else {
                // do nothing
            }

            // 避免频繁刷新
            AccessKey ak = AccessKey.withExpire(seconds);
            Integer count = redisService.get(ak, key, Integer.class);
            if(count  == null) {
                redisService.set(ak, key, 1);
            }else if(count < maxCount) {
                redisService.incr(ak, key);
            }else {
                render(response, CodeMsg.ACCESS_LIMIT_REACHED);
                return false;
            }
        }

        return true;

    }

    private void render(HttpServletResponse response, CodeMsg cm)
            throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str  = JSON.toJSONString(Result.error(cm));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }

    /**
     * 获取用户
     * @param request
     * @param response
     * @return
     */
    private MiaoshaUser getUser(HttpServletRequest request, HttpServletResponse response) {
        String paramToken = request.getParameter(MiaoShaUserServiceImpl.COOKI_NAME_TOKEN);
        String cookieToken = getCookieValue(request, MiaoShaUserServiceImpl.COOKI_NAME_TOKEN);
        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return null;
        }
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        return miaoShaUserService.getByToken(response, token);
    }

    /**
     * 获取cookie
     * @param request
     * @param cookiName
     * @return
     */
    private String getCookieValue(HttpServletRequest request, String cookiName) {
        Cookie[]  cookies = request.getCookies();
        if(cookies == null || cookies.length <= 0){
            return null;
        }
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(cookiName)) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
