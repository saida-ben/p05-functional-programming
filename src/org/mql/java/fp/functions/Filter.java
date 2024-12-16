package org.mql.java.fp.functions;

@FunctionalInterface
public interface Filter<T> {
	public boolean accept(T value);
	
}
