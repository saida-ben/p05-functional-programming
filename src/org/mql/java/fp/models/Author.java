package org.mql.java.fp.models;

public class Author {
	private String nom;
	private int yearBorn;
	
	public Author(String nom, int yearBorn) {
		super();
		this.nom = nom;
		this.yearBorn = yearBorn;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getYearBorn() {
		return yearBorn;
	}

	public void setYearBorn(int yearBorn) {
		this.yearBorn = yearBorn;
	}

	public Author() {
		// TODO Auto-generated constructor stub
	}

}
