package com.imooc.miaosha.exception;

import com.imooc.miaosha.result.CodeMsg;

/**
 * 全局的异常拦截器
 * @author yangqian
 * @date 2018/1/14
 */
public class GlobalException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private CodeMsg cm;

    public GlobalException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }

}
