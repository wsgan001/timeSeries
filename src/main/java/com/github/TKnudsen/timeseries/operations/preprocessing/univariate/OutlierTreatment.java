package com.github.TKnudsen.timeseries.operations.preprocessing.univariate;

import java.util.ArrayList;
import java.util.List;

import com.github.TKnudsen.ComplexDataObject.model.processors.IDataProcessor;
import com.github.TKnudsen.ComplexDataObject.model.processors.ParameterSupportTools;
import com.github.TKnudsen.ComplexDataObject.model.processors.complexDataObject.DataProcessingCategory;
import com.github.TKnudsen.timeseries.data.univariate.ITimeSeriesUnivariate;
import com.github.TKnudsen.timeseries.operations.tools.TimeSeriesStatistics;

/**
 * <p>
 * Title: OutlierTreatment
 * </p>
 * 
 * *
 * <p>
 * Description: Replaces the value domain for values higher/lower than a given
 * multiple of the standard deviation. Replaces with the minimum maximum allowed
 * value (+-std), or a pre-defined outlier values (e.g. Double.NaN). The
 * temporal domain is untouched.
 * 
 * Disclaimer: uses a global std and not local. Implementation is not really
 * sophisticated.
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2017
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.04
 */

public class OutlierTreatment implements ITimeSeriesUnivariatePreprocessor {

	// standard deviation ratio
	double stdDeviationRatio;

	// the value that is assigned to an outlier
	Double outlierValue;

	public OutlierTreatment() {
		this(2.96, null);
	}

	public OutlierTreatment(double stdDeviationRatio) {
		this(stdDeviationRatio, null);
	}

	public OutlierTreatment(double stdDeviationRatio, Double outlierValue) {
		this.stdDeviationRatio = stdDeviationRatio;
		this.outlierValue = outlierValue;
	}

	@Override
	public void process(List<ITimeSeriesUnivariate> data) {
		for (ITimeSeriesUnivariate timeSeries : data)
			process(timeSeries);
	}

	private void process(ITimeSeriesUnivariate timeSeries) {

		if (timeSeries == null)
			return;

		TimeSeriesStatistics statistics = new TimeSeriesStatistics(timeSeries);

		double means = statistics.getMean();
		double std = statistics.getStandardDeviation();
		std *= stdDeviationRatio;

		for (int i = 0; i < timeSeries.size(); i++) {
			if (Math.abs(timeSeries.getValue(i) - means) > std) {
				if (outlierValue == null) {
					if ((timeSeries.getValue(i) - means) > std)
						timeSeries.replaceValue(i, means + std);
					else
						timeSeries.replaceValue(i, means - std);
				} else
					timeSeries.replaceValue(i, outlierValue);
			}
		}
	}

	@Override
	public DataProcessingCategory getPreprocessingCategory() {
		return DataProcessingCategory.DATA_CLEANING;
	}

	@Override
	public List<IDataProcessor<ITimeSeriesUnivariate>> getAlternativeParameterizations(int count) {
		List<IDataProcessor<ITimeSeriesUnivariate>> alternatives = new ArrayList<>();

		List<Double> alternativeDoubles = ParameterSupportTools.getAlternativeDoubles(stdDeviationRatio, count);

		for (Double std : alternativeDoubles) {
			if (std > 0)
				alternatives.add(new OutlierTreatment(std));
			if (alternatives.size() == count)
				return alternatives;
		}

		return alternatives;
	}
}
