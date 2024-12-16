package org.mql.java.fp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.function.*;

import javax.swing.JButton;

import org.mql.java.fp.functions.Logger;
import org.mql.java.fp.models.Author;
import org.mql.java.fp.services.DataManager;

/*
 * Une " Lambda Expression " est une syntaxe simplifiee permettant de manipuler les fonction (methodes) comme des objets . 
 * Ainsi une fonction sera enveloppee automatiquement a l'interieur d'un objet cree depuis une classe (elle meme generee automatiquement)
 * qui implemente ce qu'on appelle une interface fonctionnelle : une interface avec une et une seule fonction.
 * Les " Lambda Expressio " seront utilisees, de facon benefique, dans toute les situations ou l'on a besoin d'un raisonnement oriente fonction et qu'il n'y pas lieu a raisonner classe et reuitilisabilite de code.
 * Une autre syntaxe qui pourra etre utilisee dans les memes situations :
 * Les references a des methodes existantes : Method References. et il y a 4 situations possible qui seront decrites dans le paragraphe suivant 
 */

public class LambdaExpressions {

	public LambdaExpressions() {
		exp11();
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
		//interfaces fonctionnel existance
		Predicate<String> p1;
		Consumer<String> c1;  // logger. au lieu de logger utiliser consumer
		Supplier<String> s1; // fournusse que vous voullez
		Function<String, Integer> f1; // entrer avec integer sortir avec string par exemple
		BiFunction<String, String, Integer> bf;
	}
	
	void exp09() {
		/*
		 * Etant donnee une liste d'autheur, on aimerait recuperer la liste des noms des auteurs qui sont nes avant 1960, triee et afficher la liste.
		 */
		//cree list immuable
		// pipling
		// JDK 8 nauvaeute : Lambda Expretion method-refernces stream 
		List<Author> authors = Arrays.asList(
				new Author("Eric Gamma", 1961),
				new Author("James Gosling", 1955),
				new Author("Brenden Eich", 1961),
				new Author("Tim Berners-Lee", 1955 ),
				new Author("Dennis Ritchie", 1941)
				);
		authors.stream()
				.filter(new Predicate<Author>(){
					public boolean test(Author t) {
						System.out.println(" >> test");
						return t.getYearBorn() < 1960;
					}
		})
		.map(new Function<Author, String>(){
					public String apply(Author t) {
						System.out.println(" >> apply");
						return t.getNom();
								
					}
		})
		.sorted(new Comparator<String>(){
			public int compare(String o1, String o2) {
				System.out.println(" >> compare"); // ce syso pour verifier que s on leve FOREACHE les fcts pipline ne fonctionne pas
				return o1.compareTo(o2);
			}
		})
		.forEach(new Consumer<String>() {
			public void accept(String t) {
				System.out.println(t);
			}
			
		}); //foreach operation terminal pipline ne s execute que c est op terminale  les map / filter / sorted ne s'execute que si forEach existe

	}
	
	void exp10() {
		/*
		 * Etant donnee une liste d'autheur, on aimerait recuperer la liste des noms des auteurs qui sont nes avant 1960, triee et afficher la liste.
		 */
		//cree list immuable
		// pipling
		// JDK 8 nauvaeute : Lambda Expretion method-refernces stream 
		List<Author> authors = Arrays.asList(
				new Author("Eric Gamma",1961),
				new Author("James Gosling", 1955),
				new Author("Brenden Eich", 1961),
				new Author("Tim Berners-Lee", 1955 ),
				new Author("Dennis Ritchie", 1941)
				);
		authors.stream()
				.filter(a -> a.getYearBorn() < 1960)
				.map(Author::getNom)
				.sorted(String ::compareTo)
				.forEach(System.out::println); //foreach operation terminal pipline ne s execute que c est op terminale
		
		
		/*
		 * String s = "ahhhh ahhhhhh";
		 * 	s
			.toUpperCase()
			.substring(0, 0)
			.charAt();
			
		 */


	}
	
	void exp11() {
		Stream.of(12, 20, 25, 14, 10, 87, 45)
			   .filter(new Predicate<Integer>() {
			      public boolean test(Integer t) {
			    	  System.out.println("test()");
			    	  return t % 2 == 0 ;
			      }
		       })
			   .limit(3);
	}
	
	public static void main(String[] args) {
		new LambdaExpressions();
	}

}
