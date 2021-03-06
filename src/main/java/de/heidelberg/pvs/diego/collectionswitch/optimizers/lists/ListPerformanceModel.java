package de.heidelberg.pvs.diego.collectionswitch.optimizers.lists;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;

import de.heidelberg.pvs.diego.collectionswitch.context.ListCollectionType;
import de.heidelberg.pvs.diego.collectionswitch.monitors.lists.ListMetrics;

public class ListPerformanceModel {

	private final ListCollectionType type;

	private final UnivariateFunction contains;
	private final UnivariateFunction populate;
	private final UnivariateFunction iterate;
	private  UnivariateFunction index;

	@Deprecated
	public ListPerformanceModel(ListCollectionType type, double[] populate, double[] contains, 
			double[] iterate, double[] index) {
		super();
		this.type = type;
		this.contains = new PolynomialFunction(contains);
		this.populate = new PolynomialFunction(populate);
		this.iterate = new PolynomialFunction(iterate);
		this.index = new PolynomialFunction(index);
	}
	
	public ListPerformanceModel(ListCollectionType type, double[] populate, double[] contains, 
			double[] iterate) {
		super();
		this.type = type;
		this.contains = new PolynomialFunction(contains);
		this.populate = new PolynomialFunction(populate);
		this.iterate = new PolynomialFunction(iterate);
	}
	
	

	public double calculatePerformance(ListMetrics state) {

		int size = state.getMaxSize();

		return populate.value(size) 
				+ state.getContainsOp() * contains.value(size)
				+ state.getIterationOp() * iterate.value(size);
		}

	public ListCollectionType getType() {
		return type;
	}

}
