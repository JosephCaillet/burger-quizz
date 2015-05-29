import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GestionReponses
{
	private Connection bdd;
	private ArrayList<Reponses> listReponses;

	public GestionReponses(Connection bdd)
	{
		this.bdd = bdd;
		listReponses = new ArrayList<Reponses>();
	}

	public void readReponses(String nomCategorie)
	{
		String rq = "SELECT *" +
				" FROM reponses" +
				" WHERE nom_cat = ?";
		try
		{
			PreparedStatement preparedStatement = bdd.prepareStatement(rq);
			preparedStatement.setString(1, nomCategorie);
			ResultSet resultat = preparedStatement.executeQuery();

			listReponses.clear();

			while(resultat.next())
			{
				listReponses.add(new Reponses(resultat.getString("reponse1"), resultat.getString("reponse2")));
			}

			resultat.close();
			preparedStatement.close();

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public ArrayList<Reponses> getListReponses()
	{
		return listReponses;
	}
}