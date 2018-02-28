package com.imooc.miaosha.controller.demo;

import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yangqian
 * @date 2018/1/14
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "Hello World!";
    }

    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello() {
        return Result.success("hello, imooc");
    }

    @RequestMapping("/helloHtml")
    public String hello(Model model) {
        model.addAttribute("name", "admin");
        return "hello";
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet() {
        User user = userService.findById(1);
        return Result.success(user);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx() {
        userService.tx();
        return Result.success(true);
    }
}
