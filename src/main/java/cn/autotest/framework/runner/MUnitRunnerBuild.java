package cn.autotest.framework.runner;

import org.junit.runner.Runner;
import org.junit.runners.model.RunnerBuilder;

public class MUnitRunnerBuild extends RunnerBuilder {
	@Override
	public Runner runnerForClass(Class<?> testClass) throws Throwable {
		return new MUnitClassRunner(testClass);
	}
}
