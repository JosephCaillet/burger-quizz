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

public class ConnexionBDD
{
	Connection connexionbdd;
	GestionCategories gestionCategories;
	GestionReponses gestionReponses;
	GestionQuestions gestionQuestions;

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