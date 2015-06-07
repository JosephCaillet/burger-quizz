package GestionBddDAO;

import GestionErreurs.BDDException;
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

	public void readCategorie() throws SQLException
	{
		PreparedStatement preparedStatement = null;
		ResultSet resultat = null;
		String rq = "SELECT *" +
				"FROM categorie";
		try
		{
			preparedStatement = bdd.prepareStatement(rq);
			resultat = preparedStatement.executeQuery();

			listCategories.clear();

			while(resultat.next())
			{
				listCategories.add(new Categorie(resultat.getString("nom_cat")));
			}
		}
		catch (SQLException e)
		{
			throw e;
		}
		finally
		{
			if(resultat != null)
			{
				resultat.close();
			}
			if(preparedStatement != null) {
				preparedStatement.close();
			}
		}
	}

	public void createCategorie(String categorieName) throws SQLException
	{
		PreparedStatement preparedStatement = null;
		String rq ="INSERT INTO categorie(nom_cat)" +
				" VALUES(?)";
		try
		{
			preparedStatement = bdd.prepareStatement(rq);
			preparedStatement.setString(1,categorieName);
			preparedStatement.executeUpdate();
		}
		catch (SQLException e)
		{
			throw e;
		}
		finally
		{
			if(preparedStatement != null)
			{
				preparedStatement.close();
			}
		}
	}

	public void deleteCategorie(String categorieName) throws SQLException
	{
		PreparedStatement preparedStatement = null;
		String rq ="DELETE FROM categorie" +
				" WHERE nom_cat = ?";
		try
		{
			preparedStatement = bdd.prepareStatement(rq);
			preparedStatement.setString(1,categorieName);
			preparedStatement.executeUpdate();
		}
		catch (SQLException e)
		{
			throw e;
		}
		finally
		{
			if(preparedStatement != null)
			{
				preparedStatement.close();
			}
		}
	}

	public void updateCategorie(String oldCategorieName, String newCategorieName) throws SQLException
	{
		PreparedStatement preparedStatement = null;
		String rq ="UPDATE categorie" +
				" SET nom_cat = ?" +
				" WHERE nom_cat = ?";
		try
		{
			preparedStatement = bdd.prepareStatement(rq);
			preparedStatement.setString(1, newCategorieName);
			preparedStatement.setString(2, oldCategorieName);
			preparedStatement.executeUpdate();
		}
		catch (SQLException e)
		{
			throw e;
		}
		finally
		{
			if(preparedStatement != null)
			{
				preparedStatement.close();
			}
		}
	}

	public ArrayList<Categorie> getListCategories()
	{
		return listCategories;
	}
}