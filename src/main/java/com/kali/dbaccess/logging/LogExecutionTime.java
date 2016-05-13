package com.kali.dbaccess.logging;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface LogExecutionTime {

    Severity severity() default Severity.DEBUG;

}
