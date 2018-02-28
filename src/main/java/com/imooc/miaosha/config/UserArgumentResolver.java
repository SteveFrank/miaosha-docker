package com.imooc.miaosha.config;

import com.alibaba.druid.util.StringUtils;
import com.imooc.miaosha.access.context.UserContext;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.service.MiaoShaUserService;
import com.imooc.miaosha.service.impl.MiaoShaUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author yangqian
 * @date 2018/1/27
 */
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private MiaoShaUserService miaoShaUserService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz = parameter.getParameterType();
        return clazz == MiaoshaUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        return UserContext.getUser();
    }

}
