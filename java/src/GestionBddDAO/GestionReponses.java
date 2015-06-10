package GestionBddDAO;

import Modele.Reponses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GestionReponses
{
	/**
	 * Connexion à la base de données.
	 */
	private Connection bdd;
	/**
	 * Tableau contenant la liste de toutes les réponses stockées en base de données.
	 */
	private ArrayList<Reponses> listReponses;

	/**
	 * Constructeur donnant accées à la base de données.
	 * @param bdd La connexion à la base de données.
	 */
	public GestionReponses(Connection bdd)
	{
		this.bdd = bdd;
		listReponses = new ArrayList<Reponses>();
	}

	/**
	 * Remplis le tableau interne avec la liste des jeux de réponses depuis la base de données.
	 * @param nomCategorie La catégorie des jeux de réponses à récupérer.
	 * @throws SQLException Si une erreur SQL survient.
	 */
	public void readReponses(String nomCategorie) throws SQLException
	{
		PreparedStatement preparedStatement = null;
		ResultSet resultat = null;
		String rq = "SELECT *" +
				" FROM reponses" +
				" WHERE nom_cat = ?";

		try
		{
			preparedStatement = bdd.prepareStatement(rq);
			preparedStatement.setString(1, nomCategorie);
			resultat = preparedStatement.executeQuery();

			listReponses.clear();

			while(resultat.next())
			{
				listReponses.add(new Reponses(resultat.getString("reponse1"), resultat.getString("reponse2")));
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
	 * Crée un nouveau jeu de réponses.
	 * @param nomCategorie La catégorie du jeu de réponses.
	 * @param reponse1 La réponse une du jeu à créer.
	 * @param reponse2 La réponse deux du jeu à créer.
	 * @throws SQLException Si une erreur SQL survient.
	 */
	public void createReponses(String nomCategorie, String reponse1, String reponse2) throws SQLException
	{
		PreparedStatement preparedStatement = null;
		String rq = "INSERT INTO reponses(nom_cat,reponse1,reponse2)" +
				" VALUES(?,?,?)";
		try
		{
			preparedStatement = bdd.prepareStatement(rq);
			preparedStatement.setString(1, nomCategorie);
			preparedStatement.setString(2, reponse1);
			preparedStatement.setString(3, reponse2);
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
	 * Supprime un jeu de réponses.
	 * @param reponse1 La réponse une du jeu à supprimer.
	 * @param reponse2 La réponse deux du jeu à supprimer.
	 * @throws SQLException Si une erreur SQL survient.
	 */
	public void deleteReponses(String reponse1, String reponse2) throws SQLException
	{
		PreparedStatement preparedStatement = null;
		String rq ="DELETE FROM reponses" +
				" WHERE reponse1 = ? AND reponse2 = ?";
		try
		{
			preparedStatement = bdd.prepareStatement(rq);
			preparedStatement.setString(1, reponse1);
			preparedStatement.setString(2, reponse2);
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
	 * Modifie un jeu de réponses.
	 * @param categorie Nouvelle catégorie.
	 * @param oldRep1 Ancienne réponse une.
	 * @param oldRep2 Ancienne réponse deux.
	 * @param newRep1 Nouvelle réponse une.
	 * @param newRep2 Nouvelle réponse deux.
	 * @throws SQLException Si une erreur SQL survient.
	 */
	public void updateReponses(String categorie, String oldRep1, String oldRep2, String newRep1, String newRep2) throws SQLException
	{
		PreparedStatement preparedStatement = null;
		String rq ="UPDATE reponses" +
				" SET nom_cat = ?, reponse1 = ?, reponse2 = ?" +
				" WHERE reponse1 = ? AND reponse2 = ?";
		try
		{
			preparedStatement = bdd.prepareStatement(rq);
			preparedStatement.setString(1, categorie);
			preparedStatement.setString(2, newRep1);
			preparedStatement.setString(3, newRep2);
			preparedStatement.setString(4, oldRep1);
			preparedStatement.setString(5, oldRep2);
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
	 * Renvoie le tableau interne contenant les réponses.
	 * @return Un tableau d'objets Réponses.
	 */
	public ArrayList<Reponses> getListReponses()
	{
		return listReponses;
	}
}