package Modele;

public class Reponses
{
	protected String reponse1;
	protected String reponse2;

	public Reponses(String reponse1, String reponse2) {
		this.reponse1 = reponse1;
		this.reponse2 = reponse2;
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

	public String toString() {
		return  reponse1 + ", " + reponse2 + " ou les deux?";
	}
}