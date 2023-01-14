package io.github.franzli347.foss.annotation;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.franzli347.foss.support.fileSupport.FiledExistInTableValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 判断value在colum中是否在表中
 * @author FranzLi
 */
@Target({ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {FiledExistInTableValidator.class})
@Documented
public @interface FiledExistInTable {
    String message();

    Class<? extends IService<?>> serviceClz();

    String colum();


    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        FiledExistInTable[] value();
    }

}
