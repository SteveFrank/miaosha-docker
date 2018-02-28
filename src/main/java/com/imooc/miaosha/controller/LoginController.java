package com.imooc.miaosha.controller;

import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.MiaoShaUserService;
import com.imooc.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author yangqian
 * @date 2018/1/14
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private MiaoShaUserService miaoShaUserService;

    @RequestMapping("/to_login")
    public String login(Model model) {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<CodeMsg> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
        logger.info(loginVo.toString());
        // 登陆
        CodeMsg codeMsg = miaoShaUserService.login(response, loginVo);
        if (codeMsg.getCode() == 0) {
            return Result.success(CodeMsg.SUCCESS);
        } else {
            return Result.error(codeMsg);
        }
    }

}
