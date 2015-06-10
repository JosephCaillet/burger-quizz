package Modele;

/**
 * Modéle représentant une question.
 * @author joseph
 */
public class Question
{
	/**
	 * Intitulé de la qustion.
	 */
	protected String intitule;
	/**
	 * Réponse une.
	 */
	protected String reponse1;
	/**
	 * Réponse deux.
	 */
	protected String reponse2;
	/**
	 * Numéro bonne réponses.
	 */
	protected int reponse;

	/**
	 * Constructeur initiant les champs de la question.
	 * @param intitule Intitulé de la question.
	 * @param reponse1 Réponse une.
	 * @param reponse2 Réponse deux.
	 * @param reponse Numéro bonne réponse.
	 */
	public Question(String intitule, String reponse1, String reponse2, int reponse) {
		this.intitule = intitule;
		this.reponse1 = reponse1;
		this.reponse2 = reponse2;
		this.reponse = reponse;
	}

	/**
	 * Renvoie l'intitulé de la question.
	 * @return l'intitulé de la question.
	 */
	public String getIntitule() {
		return intitule;
	}

	/**
	 * Modifie l'intitulé de la question.
	 */
	public void setIntitule(String intitule) {
		this.intitule = intitule;
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
	 */
	public void setReponse2(String reponse2) {
		this.reponse2 = reponse2;
	}

	/**
	 * Renvoie le numéro de la bonne réponse.
	 * @return le numéro de la bonne réponse.
	 */
	public int getReponse() {
		return reponse;
	}

	/**
	 * Modifie le numéro de la bonne réponse.
	 */
	public void setReponse(int reponse) {
		this.reponse = reponse;
	}

	/**
	 * Renvoie une chaine représentant l'objet, sous la forme: "intitulé | bonneRéponse".
	 * @return une chaine représentant l'objet.
	 */
	public String toString() {
		String bonneReponse = new String();
		if(reponse == 0)
		{
			bonneReponse = "Les deux";
		}
		else if(reponse == 1)
		{
			bonneReponse = reponse1;
		}
		else
		{
			bonneReponse = reponse2;
		}

		return intitule + " | " + bonneReponse;
	}
}