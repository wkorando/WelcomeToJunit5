package com.bk.hotel.service.impl;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.platform.commons.util.PreconditionViolationException;

public class DateValidationBeanAggregator implements ArgumentsAggregator {

	@Override
	public DateValidationBean aggregateArguments(ArgumentsAccessor arguments, ParameterContext context) {
		DateValidationBean dateValidation;
		try {
			dateValidation = new DateValidationBean(arguments.getString(0), //
					arguments.getString(1), //
					arguments.getString(2), //
					StringArrayConverter.convert(arguments.get(3)));
		} catch (PreconditionViolationException exception) {
			// Used when scenario has no messages being returned
			dateValidation = new DateValidationBean(arguments.getString(0), //
					arguments.getString(1), //
					arguments.getString(2));
		}
		return dateValidation;
	}
}