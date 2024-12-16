package org.mql.java.fp.functions;

// @FunctionalInterface un verrou de protecton il doit ajouter qu'un seul methode dans cet interface

@FunctionalInterface
public interface Logger {
	public void log(String message);


}
