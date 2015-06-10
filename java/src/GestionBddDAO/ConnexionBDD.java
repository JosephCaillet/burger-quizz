package GestionBddDAO;

import GestionBddDAO.GestionCategories;
import GestionBddDAO.GestionQuestions;
import GestionBddDAO.GestionReponses;
import GestionErreurs.BDDException;
import Modele.Categorie;
import Modele.Question;
import Modele.Reponses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe DAO offrant une surcouche pour l'interaction avec la base de données.
 * @author joseph
 */
public class ConnexionBDD
{
	/**
	 * Represente la connexion à la base de données.
	 */
	private Connection connexionbdd;

	/**
	 * Objet permettant la gestion des catégories dans la base de données.
	 */
	private GestionCategories gestionCategories;

	/**
	 * Objet permettant la gestion des réponses dans la base de données.
	 */
	private GestionReponses gestionReponses;

	/**
	 * Objet permettant la gestion des questions dans la base de données.
	 */
	private GestionQuestions gestionQuestions;

	/**
	 * Constructeur par defaut. Charge le driver mysql. Arret du programme si il n'est pas trouvé.
	 */
	public ConnexionBDD()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(ClassNotFoundException e)
		{
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}

	/**
	 * Initie la connexion à la base de données, avec un time-out de 5 secondes.
	 * @param nomBdd Le nom de la base.
	 * @param port Le port du serveur.
	 * @param ip L'adresse IP du serveur.
	 * @param login Le login de l'utilisateur sur le serveur.
	 * @param password Le mot de passe de l'utilisateur sur le serveur.
	 * @return true si la connexion est effectué, false sinon.
	 */
	public boolean connect(String nomBdd, long port, String ip, String login, String password)
	{
		try
		{
			String url = "jdbc:mysql://" + ip + ":" + port + "/" + nomBdd;
			DriverManager.setLoginTimeout(5);
			connexionbdd = DriverManager.getConnection(url, login, password);

			gestionCategories = new GestionCategories(connexionbdd);
			gestionReponses = new GestionReponses(connexionbdd);
			gestionQuestions = new GestionQuestions(connexionbdd);

			return true;
		}
		catch(SQLException e)
		{
			System.out.println("Erreur de conexion à la base de données: " + e.getMessage());
			return false;
		}
	}

	//Gestion catégorie

	/**
	 * Récupére la liste des catégorie depuis la base de données.
	 * @return Un tableau d'objet catégorie.
	 * @throws BDDException Si une erreur SQL survient.
	 */
	public ArrayList<Categorie> getListeCategorie() throws BDDException
	{
		try
		{
			gestionCategories.readCategorie();
		}
		catch(SQLException e)
		{
			throw new BDDException("Impossible de charger la liste des catégories. La bdd indique: " + e.getMessage());
		}
		return gestionCategories.getListCategories();
	}

	/**
	 * Crée une nouvelle catégorie.
	 * @param categorieName Le nom de la catégorie à créer.
	 * @throws BDDException Si une erreur SQL survient.
	 */
	public void createCategorie(String categorieName) throws BDDException
	{
		try
		{
			gestionCategories.createCategorie(categorieName);
		}
		catch(SQLException e)
		{
			throw new BDDException("Impossible de créer une nouvelle catégorie. La bdd indique: " + e.getMessage());
		}
	}

	/**
	 * Supprime une catégorie.
	 * @param categorieName Le nom de la catégorie à supprimer.
	 * @throws BDDException Si une erreur SQL survient.
	 */
	public void deleteCategorie(String categorieName) throws BDDException
	{
		try
		{
			gestionCategories.deleteCategorie(categorieName);
		}
		catch(SQLException e)
		{
			throw new BDDException("Impossible de suprimer la catégorie. La bdd indique: " + e.getMessage());
		}
	}

	/**
	 * Rennome une catégorie.
	 * @param oldCategorieName Le nom de la catégorie à rennomer.
	 * @param newCategorieName Le nouveau nom de la catégorie.
	 * @throws BDDException Si une erreur SQL survient.
	 */
	public void renameCategorie(String oldCategorieName, String newCategorieName) throws BDDException
	{
		try
		{
			gestionCategories.updateCategorie(oldCategorieName, newCategorieName);
		}
		catch(SQLException e)
		{
			throw new BDDException("Impossible de renommer catégorie. La bdd indique: " + e.getMessage());
		}
	}

	//Gestion réponses

	/**
	 * Permet d'obtenir la liste des jeux de réponses depuis la base de données.
	 * @param catName La catégorie des jeux de réponses à récupérer.
	 * @return Un tableau d'objet Réponses.
	 * @throws BDDException Si une erreur SQL survient.
	 */
	public ArrayList<Reponses> getListeReponses(String catName) throws BDDException
	{
		try
		{
			gestionReponses.readReponses(catName);
		}
		catch(SQLException e)
		{
			throw new BDDException("Impossible de charger la liste des réponses. La bdd indique: " + e.getMessage());
		}
		return gestionReponses.getListReponses();
	}

	/**
	 * Crée un nouveau jeu de réponses.
	 * @param nomCategorie La catégorie du jeu de réponses.
	 * @param reponse1 La réponse une du jeu à créer.
	 * @param reponse2 La réponse deux du jeu à créer.
	 * @throws BDDException Si une erreur SQL survient.
	 */
	public void createReponses(String nomCategorie, String reponse1, String reponse2) throws BDDException
	{
		try
		{
			gestionReponses.createReponses(nomCategorie, reponse1, reponse2);
		}
		catch(SQLException e)
		{
			throw new BDDException("Impossible de créer une nouvelle réponse. La bdd indique: " + e.getMessage());
		}
	}

	/**
	 * Supprime un jeu de réponses.
	 * @param reponse1 La réponse une du jeu à supprimer.
	 * @param reponse2 La réponse deux du jeu à supprimer.
	 * @throws BDDException Si une erreur SQL survient.
	 */
	public void deleteReponses(String reponse1, String reponse2) throws BDDException
	{
		try
		{
			gestionReponses.deleteReponses(reponse1, reponse2);
		}
		catch(SQLException e)
		{
			throw new BDDException("Impossible de suprimmer la réponse. La bdd indique: " + e.getMessage());
		}
	}

	/**
	 * Modifie un jeu de question.
	 * @param categorie Nouvelle catégorie.
	 * @param oldRep1 Ancienne réponse une.
	 * @param oldRep2 Ancienne réponse deux.
	 * @param newRep1 Nouvelle réponse une.
	 * @param newRep2 Nouvelle réponse deux.
	 * @throws BDDException Si une erreur SQL survient.
	 */
	public void modifyReponses(String categorie, String oldRep1, String oldRep2, String newRep1, String newRep2) throws BDDException
	{
		try
		{
			gestionReponses.updateReponses(categorie, oldRep1, oldRep2, newRep1, newRep2);
		}
		catch(SQLException e)
		{
			throw new BDDException("Impossible de modifier la réponse. La bdd indique: " + e.getMessage());
		}
	}

	//Gestion questions

	/**
	 * Récupére la liste des questions liées à un jeu de réponses depuis la base de données.
	 * @param reponse1 La réponse une du jeu de réponses lié.
	 * @param reponse2 la réponse deux du jeu de réponses lié.
	 * @return Un tableau d'objet Question.
	 * @throws BDDException Si une erreur SQL survient.
	 */
	public ArrayList<Question> getListeQuestions(String reponse1, String reponse2) throws BDDException
	{
		try
		{
			gestionQuestions.readQuestions(reponse1, reponse2);
		}
		catch(SQLException e)
		{
			throw new BDDException("Impossible de charger la liste des questions. La bdd indique: " + e.getMessage());
		}
		return gestionQuestions.getListeQuestions();
	}

	/**
	 * Crée une nouvelle question.
	 * @param intitule Intitulé de la question.
	 * @param reponse1 Réponse une du jeu de question lié.
	 * @param reponse2 Réponse une du jeu de question lié.
	 * @param num_reponse Numéro de la bonne réponses (1: reponse1, 2: réponse2, 0: les deux)
	 * @throws BDDException Si une erreur mysql survient.
	 */
	public void createQuestion(String intitule, String reponse1, String reponse2, int num_reponse) throws BDDException
	{
		try
		{
			gestionQuestions.createQuestion(intitule, reponse1, reponse2, num_reponse);
		}
		catch(SQLException e)
		{
			throw new BDDException("Impossible de créer une nouvelle question. La bdd indique: " + e.getMessage());
		}
	}

	/**
	 * Supprime une question de la base de données.
	 * @param intitule Intitulé de la question à supprimer.
	 * @param reponse1 Réponse une du jeu de question lié à la question à supprimer.
	 * @param reponse2 Réponse deux du jeu de question lié à la question à supprimer.
	 * @throws BDDException Si une erreur SQL survient.
	 */
	public void deleteQuestion(String intitule, String reponse1, String reponse2) throws BDDException
	{
		try
		{
			gestionQuestions.deleteQuestion(intitule, reponse1, reponse2);
		}
		catch(SQLException e)
		{
			throw new BDDException("Impossible de suprimer la question. La bdd indique: " + e.getMessage());
		}
	}

	/**
	 * Modifie une question.
	 * @param oldIntitule Ancien intitulé de la question à modifier.
	 * @param newIntitule Nouvel intitulé de la question à modifier.
	 * @param reponse1 Réponse une du jeu de question lié.
	 * @param reponse2 Réponse deux du jeu de question lié.
	 * @param newNum_reponse Nouveau numéro de la bonne réponse (1: reponse1, 2: réponse2, 0: les deux)
	 * @throws BDDException
	 */
	public void modifyQuestion(String oldIntitule, String newIntitule, String reponse1, String reponse2, int newNum_reponse) throws BDDException
	{
		try
		{
			gestionQuestions.updateQuestion(oldIntitule, newIntitule, reponse1, reponse2, newNum_reponse);
		}
		catch(SQLException e)
		{
			throw new BDDException("Impossible de modifier la question. La bdd indique: " + e.getMessage());
		}
	}
}