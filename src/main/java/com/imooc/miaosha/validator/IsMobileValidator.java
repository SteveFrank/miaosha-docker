package com.imooc.miaosha.validator;

import com.alibaba.druid.util.StringUtils;
import com.imooc.miaosha.utils.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author yangqian
 * @date 2018/1/14
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(required) {
            return ValidatorUtil.isMobile(value);
        }else {
            if(StringUtils.isEmpty(value)) {
                return true;
            }else {
                return ValidatorUtil.isMobile(value);
            }
        }
    }
}
