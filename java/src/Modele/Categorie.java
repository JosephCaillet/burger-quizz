package Modele;

public class Categorie
{
	protected String nom;


	public Categorie(String nom) {
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String toString() {
		return nom;
	}
}