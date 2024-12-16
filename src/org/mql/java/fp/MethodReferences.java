package org.mql.java.fp;

import java.awt.Point;
import java.util.function.BiFunction;

public class MethodReferences {
/*
 * il y a 4 types de references possibles : 
 * 1. Static Method Reference
 * 2. Particular Instance Method Reference
 * 3. Arbirary Instance Method refernce
 * 4. Constructor Reference
 */
	public MethodReferences() {
		// TODO Auto-generated constructor stub
	}

	void constructorReference(){
		BiFunction<Integer, Integer, Point> f1 = Point::new;
		
		Point p1 = f1.apply(20, 30);
	}
}
