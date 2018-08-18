package com.bk.hotel.test.utils;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;

public class SpringTestContainersExtension extends SpringExtension {

	private GenericContainer<?> container;
	private boolean restartContainerForEveryTest = false;

	public SpringTestContainersExtension(GenericContainer<?> container) {
		this.container = container;
	}

	public SpringTestContainersExtension(GenericContainer<?> container, boolean restartContainerForEveryTest) {
		this.container = container;
		this.restartContainerForEveryTest = restartContainerForEveryTest;
	}

	@Override
	public void afterAll(ExtensionContext context) throws Exception {
		if (container.isRunning()) {
			container.stop();
		}
		super.afterAll(context);
	}

	@Override
	public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
		if (!container.isRunning()) {
			container.start();
		}
		super.postProcessTestInstance(testInstance, context);
	}

	@Override
	public void afterEach(ExtensionContext context) throws Exception {
		if (restartContainerForEveryTest) {
			container.stop();
		}
		super.afterEach(context);
	}

}
