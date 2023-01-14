package io.github.franzli347.foss.utils;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.franzli347.foss.annotation.FiledExistInTable;
import lombok.extern.slf4j.Slf4j;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class FiledExistInTableValidator implements ConstraintValidator<FiledExistInTable,Object> {

    private Class<? extends IService<?>> serviceClz;

    private String colum;
    @Override
    public void initialize(FiledExistInTable constraintAnnotation) {
        serviceClz = constraintAnnotation.serviceClz();
        colum = constraintAnnotation.colum();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return SpringUtil.getBean(serviceClz).query().eq(colum, value).count() > 0;
    }
}
