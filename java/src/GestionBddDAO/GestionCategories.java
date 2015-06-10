package GestionBddDAO;

import GestionErreurs.BDDException;
import Modele.Categorie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe effectuant les modifications en base de données pour les catégories.
 * @author joseph
 */
public class GestionCategories
{
	/**
	 * Connexion à la base de données.
	 */
	private Connection bdd;
	/**
	 * Tableau contenant la liste de toutes les categories stockées en base de données.
	 */
	private ArrayList<Categorie> listCategories;

	/**
	 * Constructeur donnant accées à la base de données.
	 * @param bdd La connexion à la base de données.
	 */
	public GestionCategories(Connection bdd)
	{
		this.bdd = bdd;
		listCategories = new ArrayList<Categorie>();
	}

	/**
	 * Remplis le tableau interne avec les catégorie trouvées en base de données.
	 * @throws SQLException Si une erreur SQL survient.
	 */
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

	/**
	 * Crée une nouvelle catégorie.
	 * @param categorieName Le nom de la catégorie à créer.
	 * @throws SQLException Si une erreur SQL survient.
	 */
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

	/**
	 * Supprime une catégorie.
	 * @param categorieName Le nom de la catégorie à supprimer.
	 * @throws SQLException Si une erreur SQL survient.
	 */
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

	/**
	 * Rennome une catégorie.
	 * @param oldCategorieName Le nom de la catégorie à rennomer.
	 * @param newCategorieName Le nouveau nom de la catégorie.
	 * @throws SQLException Si une erreur SQL survient.
	 */
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

	/**
	 * Renvoie le tableau interne contenant les catégories.
	 * @return Un tableau d'objets Catégorie.
	 */
	public ArrayList<Categorie> getListCategories()
	{
		return listCategories;
	}
}