package com.imooc.miaosha.vo;

import com.imooc.miaosha.validator.IsMobile;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yangqian
 * @date 2018/1/14
 */
public class LoginVo implements Serializable{

    private static final long serialVersionUID = -9051168135829443066L;
    @NotNull
    @IsMobile
    private String mobile;
    @NotNull
    @Length(max = 32)
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginVo{"
                + " mobile='" + mobile
                + " password='" + password
                + '}';
    }
}
