package com.bk.hotel.service.impl;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

public class SacrificalCondition implements ExecutionCondition {

	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
		if(context.getTags().contains("sacrificalLamb")) {
			return ConditionEvaluationResult.enabled("demo gods have been appeased by your sacrifice");
		} else {
			return ConditionEvaluationResult.disabled("The demo gods demand a sacrifice!");
		}
	}

}
