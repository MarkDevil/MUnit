package cn.autotest.framework.runner;

import cn.autotest.framework.annotation.TestCase;
import org.junit.Test.None;
import org.junit.internal.runners.statements.ExpectException;
import org.junit.internal.runners.statements.FailOnTimeout;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.util.List;

public class MUnitClassRunner extends BlockJUnit4ClassRunner {


	public MUnitClassRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}
	
	@Override
	protected List<FrameworkMethod> computeTestMethods() {
		return getTestClass().getAnnotatedMethods(TestCase.class);
	}
	
	protected void validateTestMethods(List<Throwable> errors) {
		validatePublicVoidNoArgMethods(TestCase.class, false, errors);
	}
	
	/**
	 * Returns a {@link Statement}: if {@code method}'s {@code @Test} annotation
	 * has the {@code expecting} attribute, return normally only if {@code next}
	 * throws an exception of the correct type, and throw an exception
	 * otherwise.
	 * 
	 * @deprecated Will be private soon: use Rules instead
	 */
	@Deprecated
	protected Statement possiblyExpectingExceptions(FrameworkMethod method,
			Object test, Statement next) {
		TestCase annotation= method.getAnnotation(TestCase.class);
		return expectsException(annotation) ? new ExpectException(next,
				getExpectedException(annotation)) : next;
	}

	/**
	 * Returns a {@link Statement}: if {@code method}'s {@code @Test} annotation
	 * has the {@code timeout} attribute, throw an exception if {@code next}
	 * takes more than the specified number of milliseconds.
	 * 
	 * @deprecated Will be private soon: use Rules instead
	 */
	@Deprecated
	protected Statement withPotentialTimeout(FrameworkMethod method,
			Object test, Statement next) {
		long timeout= getTimeout(method.getAnnotation(TestCase.class));
		return timeout > 0 ? new FailOnTimeout(next, timeout) : next;
	}

	private Class<? extends Throwable> getExpectedException(TestCase annotation) {
		if (annotation == null || annotation.expected() == None.class)
			return null;
		else
			return annotation.expected();
	}

	private boolean expectsException(TestCase annotation) {
		return getExpectedException(annotation) != null;
	}

	private long getTimeout(TestCase annotation) {
		if (annotation == null)
			return 0;
		return annotation.timeout();
	}

//	@Override
//	protected void runChild(FrameworkMethod method, RunNotifier notifier) {
//		logger.info(Thread.currentThread().getName());
//		Annotation[] methodannotations = method.getAnnotations();
//		TestCase testcase =  method.getMethod().getAnnotation(TestCase.class);
//		if (testcase.clientnum()< 0 || testcase.threadnum()<0){
//			try {
//				throw new Exception("Input annotation is incorrect ");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}else {
//			ThreadUtils threadUtils = new ThreadUtils(testcase.threadnum(),testcase.clientnum());
//			if (methodannotations.length>0){
//				logger.info("class : " + method.getMethod().getDeclaringClass()+ " name : " + method.getMethod().getName());
//				logger.info("Thread num : " + testcase.threadnum() + "\t" + "Client num : " + testcase.clientnum());
//				threadUtils.executeThread(method.getMethod().getDeclaringClass(),method.getMethod().getName());
//			}
//			try {
//				Thread.currentThread().join(2000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//
//	}



}

