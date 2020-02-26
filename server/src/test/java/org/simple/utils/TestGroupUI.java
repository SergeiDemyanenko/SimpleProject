package org.simple.utils;

import org.springframework.test.context.junit.jupiter.EnabledIf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnabledIf("#{systemProperties['test.group'] != null " +
        "&& (systemProperties['test.group'] == 'all' " +
        "   || systemProperties['test.group'].contains('ui'))}")
public @interface TestGroupUI {
}
