package Modele;

/**
 * Modéle représentant un jeu de réponses.
 * @author joseph
 */
public class Reponses
{
	/**
	 * Réponse 1
	 */
	protected String reponse1;
	/**
	 * Réponse 2
	 */
	protected String reponse2;

	/**
	 * Constructeur d'initialisation des réponses du jeu.
	 * @param reponse1 Réponse une.
	 * @param reponse2 Réponse deux.
	 */
	public Reponses(String reponse1, String reponse2) {
		this.reponse1 = reponse1;
		this.reponse2 = reponse2;
	}

	/**
	 * Renvoie la réponse une.
	 * @return la réponse une.
	 */
	public String getReponse1() {
		return reponse1;
	}

	/**
	 * Modifie la réponse une.
	 * @return la réponse une.
	 */
	public void setReponse1(String reponse1) {
		this.reponse1 = reponse1;
	}

	/**
	 * Renvoie la réponse deux.
	 * @return la réponse deux.
	 */
	public String getReponse2() {
		return reponse2;
	}

	/**
	 * Modifie la réponse deux.
	 * @return la réponse deux.
	 */
	public void setReponse2(String reponse2) {
		this.reponse2 = reponse2;
	}

	/**
	 * Renvoie une chaine représentant l'objet, sous la forme: "reponse1, reponse2 ou les deux?".
	 * @return une chaine représentant l'objet.
	 */
	public String toString() {
		return  reponse1 + ", " + reponse2 + " ou les deux?";
	}
}