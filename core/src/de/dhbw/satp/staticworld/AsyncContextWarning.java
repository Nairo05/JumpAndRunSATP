package de.dhbw.satp.staticworld;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface AsyncContextWarning {

    boolean doLogging() default false;
    boolean dumpName() default true;
}
