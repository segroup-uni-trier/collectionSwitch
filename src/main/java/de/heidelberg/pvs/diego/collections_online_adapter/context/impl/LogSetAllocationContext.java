package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContextInfo;

public class LogSetAllocationContext implements SetAllocationContext {
	
	private static final int FREQUENCY = 10;

	SetAllocationContextInfo context;
	
	PrintWriter writer;
	
	int count = 0;
	
	public LogSetAllocationContext(SetAllocationContextInfo context, String identifier, String dir) {
		super();
		this.context = context;
		long currentTimeMillis = System.currentTimeMillis();
		
		try{
			writer = new PrintWriter(dir + "/" + identifier + "__-__" + currentTimeMillis + ".txt", "UTF-8");
		    writer.println("Context initialized");
		    writer.println("First Status: " + this.context.getAllocationContextState());
		    writer.flush();
		} catch (IOException e) {
			if(writer != null) {
				writer.close();
			}
		   // do something
		}
	}

	
	@Override
	public void updateCollectionSize(int size) {
		AllocationContextState beforeState = context.getAllocationContextState();
		context.updateCollectionSize(size);
		AllocationContextState afterState = context.getAllocationContextState();
		
		writer.println("State updated from " + beforeState + " -- to --" + afterState);
		writer.println("New Initial Capacity = " + context.getInitialCapacity());
		writer.flush();
		
	}

	
	@Override
	public void noSizeConvergence() {
		writer.println("No size convergence");
		writer.flush();
		context.noSizeConvergence();
		
	}

	public void noCollectionTypeConvergence(int mode, int medianInitialCapacity) {
		// TODO To implement
	}
	

	@Override
	public <E> Set<E> createSet() {
		count++;
		if(count % FREQUENCY == 0) {
			writer.println(String.format("Created %d sets \n\t-- initialCapacity (analyzed=%d || described=10)  ", count, this.context.getInitialCapacity() ));
			writer.flush();
		}
		return context.createSet();
	}


	@Override
	public <E> Set<E> createSet(int initialCapacity) {
		count++;
		if(count % FREQUENCY == 0) {
			writer.println(String.format("Created %d sets \n\t-- initialCapacity (analyzed=%d || described=%s)  ", count, this.context.getInitialCapacity(), initialCapacity));
			writer.flush();
		}
		return context.createSet(initialCapacity);
	}


	@Override
	public <E> Set<E> createSet(Collection<? extends E> set) {
		count++;
		if(count % FREQUENCY == 0) {
			writer.println(String.format("Copied %d sets \n\t-- initialCapacity (analyzed=%d || described=%s)  ", count, this.context.getInitialCapacity(), set.size()));
			writer.flush();
		}
		return context.createSet(set);
	}

	

	
	

}