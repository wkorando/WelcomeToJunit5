package com.bk.hotel.service.impl;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.testcontainers.containers.GenericContainer;

public class TestContainersExtension implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback,
		AfterEachCallback, TestInstancePostProcessor {

	private GenericContainer<?> container;
	private boolean classLevelContainer = true;

	public TestContainersExtension(GenericContainer<?> container) {
		this.container = container;
	}

	public TestContainersExtension(GenericContainer<?> container, boolean classLevelContainer) {
		this.container = container;
		this.classLevelContainer = classLevelContainer;
	}

	@Override
	public void afterAll(ExtensionContext context) throws Exception {
		if (classLevelContainer) {
			container.stop();
		}
	}

	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
		if (classLevelContainer) {
			container.start();
		}
	}

	@Override
	public void afterEach(ExtensionContext context) throws Exception {
		if (!classLevelContainer) {
			container.stop();
		}
	}

	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
		if (!classLevelContainer) {
			container.start();
		}
	}

	@Override
	public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
		container.start();
	}
}