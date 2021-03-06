package com.github.TKnudsen.timeseries.operations.preprocessing.multivariate.dimensionBased;

import java.util.List;

import com.github.TKnudsen.ComplexDataObject.data.uncertainty.Double.NumericalUncertainty;
import com.github.TKnudsen.ComplexDataObject.model.processors.IUncertainDataProcessor;
import com.github.TKnudsen.timeseries.data.multivariate.ITimeSeriesMultivariate;
import com.github.TKnudsen.timeseries.operations.preprocessing.ITimeSeriesPreprocessor;

/**
 * <p>
 * Title: ITimeSeriesMultivariatePreprocessor
 * </p>
 * 
 * <p>
 * Description: interface for preprocessing multivariate time series.
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2016-2018
 * </p>
 * 
 * @author Juergen Bernard
 * @version 1.05
 */
public interface ITimeSeriesMultivariatePreprocessor extends ITimeSeriesPreprocessor<ITimeSeriesMultivariate>,
		IUncertainDataProcessor<ITimeSeriesMultivariate, NumericalUncertainty> {

	public void process(List<ITimeSeriesMultivariate> data);

}