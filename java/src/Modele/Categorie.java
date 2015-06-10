package Modele;

/**
 * Modele pour la représentation d'une catégorie.
 * @author joseph
 */
public class Categorie
{
	/**
	 * Nom de la catégorie.
	 */
	protected String nom;

	/**
	 * Constructeur initiant le nom de la catégorie.
	 * @param nom Nom de la catégorie.
	 */
	public Categorie(String nom) {
		this.nom = nom;
	}

	/**
	 * Renvoie le nom de la catégorie.
	 * @return Nom de la catégorie.
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Modifie le nom de la catégorie.
	 * @param nom Nom de la catégorie.
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Renvoie le nom de la catégorie.
	 * @return Nom de la catégorie.
	 */
	public String toString() {
		return nom;
	}
}