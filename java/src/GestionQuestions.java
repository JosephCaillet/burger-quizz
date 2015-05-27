import java.sql.Connection;
import java.util.ArrayList;

public class GestionQuestions
{
	private Connection bdd;
	private ArrayList<Question> listeQuestions;

	public GestionQuestions(Connection bdd)
	{
		this.bdd = bdd;
	}
}