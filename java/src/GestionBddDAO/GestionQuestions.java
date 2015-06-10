package GestionBddDAO;

import Modele.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe effectuant les modifications en base de données pour les questions.
 * @author joseph
 */
public class GestionQuestions
{
	/**
	 * Connexion à la base de données.
	 */
	private Connection bdd;
	/**
	 * Tableau contenant la liste de toutes les questions stockées en base de données.
	 */
	private ArrayList<Question> listeQuestions;

	/**
	 * Constructeur donnant accées à la base de données.
	 * @param bdd La connexion à la base de données.
	 */
	public GestionQuestions(Connection bdd)
	{
		this.bdd = bdd;
		this.listeQuestions = new ArrayList<Question>();
	}

	/**
	 * Remplis le tableau interne des questions liées à un jeu de réponses depuis la base de données.
	 * @param reponse1 La réponse une du jeu de réponses lié.
	 * @param reponse2 la réponse deux du jeu de réponses lié.
	 * @throws SQLException Si une erreur SQL survient.
	 */
	public void readQuestions(String reponse1, String reponse2) throws SQLException
	{
		PreparedStatement preparedStatement = null;
		ResultSet resultat = null;
		String rq = "SELECT *" +
				" FROM questions" +
				" WHERE reponse1 = ? AND reponse2 = ?";

		try
		{
			preparedStatement = bdd.prepareStatement(rq);
			preparedStatement.setString(1, reponse1);
			preparedStatement.setString(2, reponse2);
			resultat = preparedStatement.executeQuery();

			listeQuestions.clear();

			while(resultat.next())
			{
				listeQuestions.add(new Question(resultat.getString("intitule"), resultat.getString("reponse1"), resultat.getString("reponse2"), resultat.getInt("num_reponse")));
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
	 * Crée une nouvelle question.
	 * @param intitule Intitulé de la question.
	 * @param reponse1 Réponse une du jeu de question lié.
	 * @param reponse2 Réponse une du jeu de question lié.
	 * @param num_reponse Numéro de la bonne réponses (1: reponse1, 2: réponse2, 0: les deux)
	 * @throws SQLException Si une erreur mysql survient.
	 */
	public void createQuestion(String intitule, String reponse1, String reponse2, int num_reponse) throws SQLException
	{
		PreparedStatement preparedStatement = null;
		String rq = "INSERT INTO questions(intitule, reponse1, reponse2, num_reponse)" +
				" VALUES(?, ?, ?, ?)";
		try
		{
			preparedStatement = bdd.prepareStatement(rq);
			preparedStatement.setString(1, intitule);
			preparedStatement.setString(2, reponse1);
			preparedStatement.setString(3, reponse2);
			preparedStatement.setInt(4, num_reponse);
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
	 * Supprime une question de la base de données.
	 * @param intitule Intitulé de la question à supprimer.
	 * @param reponse1 Réponse une du jeu de question lié à la question à supprimer.
	 * @param reponse2 Réponse deux du jeu de question lié à la question à supprimer.
	 * @throws SQLException Si une erreur SQL survient.
	 */
	public void deleteQuestion(String intitule, String reponse1, String reponse2) throws SQLException
	{
		PreparedStatement preparedStatement = null;
		String rq ="DELETE FROM questions" +
				" WHERE reponse1 = ? AND reponse2 = ? AND intitule = ?";
		try
		{
			preparedStatement = bdd.prepareStatement(rq);
			preparedStatement.setString(1, reponse1);
			preparedStatement.setString(2, reponse2);
			preparedStatement.setString(3, intitule);
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
	 * Modifie une question.
	 * @param oldIntitule Ancien intitulé de la question à modifier.
	 * @param newIntitule Nouvel intitulé de la question à modifier.
	 * @param reponse1 Réponse une du jeu de question lié.
	 * @param reponse2 Réponse deux du jeu de question lié.
	 * @param newNum_reponse Nouveau numéro de la bonne réponse (1: reponse1, 2: réponse2, 0: les deux)
	 * @throws SQLException
	 */
	public void updateQuestion(String oldIntitule, String newIntitule, String reponse1, String reponse2, int newNum_reponse) throws SQLException
	{
		PreparedStatement preparedStatement = null;
		String rq ="UPDATE questions" +
				" SET intitule = ?, num_reponse = ?" +
				" WHERE intitule = ? AND reponse1 = ? AND reponse2 = ?";
		try
		{
			preparedStatement = bdd.prepareStatement(rq);
			preparedStatement.setString(1, newIntitule);
			preparedStatement.setInt(2, newNum_reponse);
			preparedStatement.setString(3, oldIntitule);
			preparedStatement.setString(4, reponse1);
			preparedStatement.setString(5, reponse2);
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
	 * Renvoie le tableau interne contenant les questions.
	 * @return Un tableau d'objets Question.
	 */
	public ArrayList<Question> getListeQuestions() {
		return listeQuestions;
	}
}