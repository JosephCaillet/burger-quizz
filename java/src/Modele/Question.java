package Modele;

/**
 * Modéle représentant une question.
 * @author joseph
 */
public class Question
{
	protected String intitule;
	protected String reponse1;
	protected String reponse2;
	protected int reponse;

	public Question(String intitule, String reponse1, String reponse2, int reponse) {
		this.intitule = intitule;
		this.reponse1 = reponse1;
		this.reponse2 = reponse2;
		this.reponse = reponse;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	public String getReponse1() {
		return reponse1;
	}

	public void setReponse1(String reponse1) {
		this.reponse1 = reponse1;
	}

	public String getReponse2() {
		return reponse2;
	}

	public void setReponse2(String reponse2) {
		this.reponse2 = reponse2;
	}

	public int getReponse() {
		return reponse;
	}

	public void setReponse(int reponse) {
		this.reponse = reponse;
	}

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