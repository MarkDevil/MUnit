package cn.autotest.framework.annotation;

import org.junit.Test.None;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface TestCase {
	long timeout() default 0L;
	Class<? extends Throwable> expected() default None.class;
	String tag() default "";
	String desc() default "";

	/**
	 * 线程数
	 * @return
     */
	int threadnum() default 1;

	/**
	 * 客户端数目
	 * @return
     */
	int clientnum() default 1;

	/**
	 * 测试初始化数据
	 * @return
     */
	String initdata() default "";



}
