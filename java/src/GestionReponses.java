import java.sql.Connection;
import java.util.ArrayList;

public class GestionReponses
{
	private Connection bdd;
	private ArrayList<Reponses> listReponses;

	public GestionReponses(Connection bdd)
	{
		this.bdd = bdd;
	}
}