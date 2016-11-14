package cn.autotest.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface RunOption {
	Class<?>[] testSuite();
	String[] testFiles() default {};
	String[] tags() default {};
	String[] service() default {};
	String propertyFile() default "";
}
