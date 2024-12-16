package org.mql.java.fp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.*;

import javax.swing.JButton;

import org.mql.java.fp.functions.Logger;
import org.mql.java.fp.services.DataManager;

public class LambdaExpressions {

	public LambdaExpressions() {
		exp07();
	}
	
	void exp01() {
		
		JButton b1 = new JButton("Exit");
		b1.addActionListener(new ActionListener() {
			//raisonner sur quelque chose de reutillisable;
			
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		});
	}
	
	
	void exp02() {
		
		JButton b1 = new JButton("Exit");
		// expression lambda : JDK 8.0
		// cest raisonnement fonctionnel
		b1.addActionListener((ActionEvent e) -> {
				System.exit(0);
		});
	}
	
	
	void exp03() {
			// inference de type : raccoricie num 1  ActionEvent e -> e
			// un seul param on utilise pas parenthese (e)
			// enleve {}return x => x
			// type b1 inferee 
		var b1 = new JButton("Exit");
			//cette synataxe ni possible qu'avec ce qu'on appelle des interfaces fonctionnelles ayan une et une seule fonction
		b1.addActionListener(e -> System.exit(0));
	}
	// ne s'utilise qu'avec interface fonctionnel - et dispose un syntaxe exceptionnel lambda 
	void test() {
		Method methods[] = getClass().getDeclaredMethods();
		for(Method method : methods) {
			System.out.println(method);
		}
	}
	
	// journaliser 
	void exp04() {
		DataManager<Integer> dm = new DataManager <Integer>();
		dm.setLogger(message -> System.out.println("## " +message + "##"));
		for (int i=0; i<10; i++) {
			dm.add((int)(Math.random()* 900 + 100));
		}
		for(int i=1; i< 5; i++) {
			int index = (int)(Math.random() * dm.size());
			dm.remove(index);
		}
	}
	
	void exp05() {
		DataManager<Integer> dm = new DataManager <Integer>();
		Logger logger1 = message -> System.out.println("## " +message + "##");
		Logger logger2 = System.out::println; //Method Reference (1/4)
		dm.setLogger(logger2);
		System.out.println(logger1.getClass().getName());
		Supplier<Integer> s1 = () -> (int)(Math.random()* 900 + 100);
		Supplier<Integer> s2 = ( ) -> (int)(Math.random() * dm.size());
		for (int i= 0; i < 10; i++) {
			dm.add(s1.get());
		}
		for(int i=1; i< 5; i++) {
			dm.remove(s2.get());
		}
	}
	
	
	//on raisonne fonction qui englobe dans n objet 
	// raisonner reuitillisation du code
	//grefer le fonctionnel au sein de l objet // rubrique sur raisonner fonctionnellement que objet stream
	// solution fonctionnel 
	void exp06() {
		DataManager<Integer> dm = new DataManager <Integer>();

		for (int i=0; i<10; i++) {
			dm.add((int)(Math.random()* 900 + 100));
		}
		List<Integer> d1 = dm.select(e -> e % 2 == 0);
		System.out.println("d1 = " +d1);
		List<Integer> d2 = dm.select(e -> e > 500);
		System.out.println("d2 =" +d2);

	}
	
	void exp07() {
		DataManager<Integer> dm = new DataManager <Integer>();
		for (int i=0; i<10; i++) {
			dm.add((int)(Math.random()* 900 + 100));
		}
		List<Integer> d1 = dm.extract(e -> e % 2 == 0);
		System.out.println("d1 = " +d1);
		List<Integer> d2 = dm.extract(e -> e > 500);
		System.out.println("d2 =" +d2);

	}
	
	void exp08() {
		Predicate<String> p1;
		Consumer<String> c1;  // logger. au lieu de logger utiliser consumer
		Supplier<String> s1; 
	}
	
	
	public static void main(String[] args) {
		new LambdaExpressions();
	}

}
