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

	public void createReponses(String nomCategorie, String reponse1, String reponse2)
	{
		String rq = "INSERT INTO reponses(nom_cat,reponse1,reponse2)" +
				" VALUES(?,?,?)";
		try
		{
			PreparedStatement preparedStatement = bdd.prepareStatement(rq);
			preparedStatement.setString(1, nomCategorie);
			preparedStatement.setString(2, reponse1);
			preparedStatement.setString(3, reponse2);
			preparedStatement.executeUpdate();

			preparedStatement.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteReponses(String rep1, String rep2)
	{
		String rq ="DELETE FROM reponses" +
				" WHERE reponse1 = ? AND reponse2 = ?";
		try
		{
			PreparedStatement preparedStatement = bdd.prepareStatement(rq);
			preparedStatement.setString(1, rep1);
			preparedStatement.setString(2, rep2);
			preparedStatement.executeUpdate();

			preparedStatement.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void updateReponses(String categorie, String oldRep1, String oldRep2, String newRep1, String newRep2)
	{
		String rq ="UPDATE reponses" +
				" SET nom_cat = ?, reponse1 = ?, reponse2 = ?" +
				" WHERE reponse1 = ? AND reponse2 = ?";
		try
		{
			PreparedStatement preparedStatement = bdd.prepareStatement(rq);
			preparedStatement.setString(1, categorie);
			preparedStatement.setString(2, newRep1);
			preparedStatement.setString(3, newRep2);
			preparedStatement.setString(4, oldRep1);
			preparedStatement.setString(5, oldRep2);
			preparedStatement.executeUpdate();

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