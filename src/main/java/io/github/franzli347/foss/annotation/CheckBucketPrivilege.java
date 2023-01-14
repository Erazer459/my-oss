package io.github.franzli347.foss.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckBucketPrivilege {
    String[] privilege();
    String argType();
    String spelString();
}
