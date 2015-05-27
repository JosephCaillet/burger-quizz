import java.util.ArrayList;

public class GestionQuestions
{
	private ConnexionBDD bdd;
	private ArrayList<Question> listeQuestions;

	public GestionQuestions(ConnexionBDD bdd)
	{
		this.bdd = bdd;
	}
}