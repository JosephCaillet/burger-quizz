import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GestionCategories
{
	private Connection bdd;
	private ArrayList<Categorie> listCategories;

	public GestionCategories(Connection bdd)
	{
		this.bdd = bdd;
		listCategories = new ArrayList<Categorie>();
	}

	public void selectCategorie()
	{
		String rq = "SELECT *" +
				"FROM categorie";
		try {
			PreparedStatement preparedStatement = bdd.prepareStatement(rq);
			ResultSet resultat = preparedStatement.executeQuery();

			listCategories.clear();

			while(resultat.next())
			{
				listCategories.add(new Categorie(resultat.getString("nom_cat")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Categorie> getListCategories()
	{
		return listCategories;
	}
}