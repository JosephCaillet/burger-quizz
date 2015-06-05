package GestionBddDAO;

import Modele.Categorie;

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

	public void readCategorie()
	{
		String rq = "SELECT *" +
				"FROM categorie";
		try
		{
			PreparedStatement preparedStatement = bdd.prepareStatement(rq);
			ResultSet resultat = preparedStatement.executeQuery();

			listCategories.clear();

			while(resultat.next())
			{
				listCategories.add(new Categorie(resultat.getString("nom_cat")));
			}

			resultat.close();
			preparedStatement.close();

		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}

	public void createCategorie(String categorieName)
	{
		String rq ="INSERT INTO categorie(nom_cat)" +
				" VALUES(?)";
		try
		{
			PreparedStatement preparedStatement = bdd.prepareStatement(rq);
			preparedStatement.setString(1,categorieName);
			preparedStatement.executeUpdate();

			preparedStatement.close();
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}

	public void deleteCategorie(String categorieName)
	{
		String rq ="DELETE FROM categorie" +
				" WHERE nom_cat = ?";
		try
		{
			PreparedStatement preparedStatement = bdd.prepareStatement(rq);
			preparedStatement.setString(1,categorieName);
			preparedStatement.executeUpdate();

			preparedStatement.close();
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}

	public void updateCategorie(String oldCategorieName, String newCategorieName)
	{

		String rq ="UPDATE categorie" +
				" SET nom_cat = ?" +
				" WHERE nom_cat = ?";
		try
		{
			PreparedStatement preparedStatement = bdd.prepareStatement(rq);
			preparedStatement.setString(1, newCategorieName);
			preparedStatement.setString(2, oldCategorieName);
			preparedStatement.executeUpdate();

			preparedStatement.close();
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}

	public ArrayList<Categorie> getListCategories()
	{
		return listCategories;
	}
}