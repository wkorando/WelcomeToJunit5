package com.bk.hotel.test.utils;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

public class DisableOnMacCondition implements ExecutionCondition {

	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
		String osName = System.getProperty("os.name");
		if (osName.equalsIgnoreCase("Mac OS X")) {
			return ConditionEvaluationResult.disabled("Test disabled on mac");
		} else {
			return ConditionEvaluationResult.enabled("Test enabled");
		}
	}

}
