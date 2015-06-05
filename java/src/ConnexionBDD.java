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
			e.printStackTrace();
			System.exit(1);
		}
	}

	public boolean connect(String nomBdd, int port, String ip, String login, String password)
	{
		try
		{
			String url = "jdbc:mysql://" + ip + ":" + port + "/" + nomBdd;
			connexionbdd = DriverManager.getConnection(url, login, password);

			gestionCategories = new GestionCategories(connexionbdd);
			gestionReponses = new GestionReponses(connexionbdd);
			gestionQuestions = new GestionQuestions(connexionbdd);

			return true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println("Erreur de conexion à la base de données: " + e.getMessage());
			return false;
			//System.exit(1);
		}
	}

	//Gestion catégorie
	public ArrayList<Categorie> getListeCategorie()
	{
		gestionCategories.readCategorie();
		return gestionCategories.getListCategories();
	}

	public void createCategorie(String categorieName)
	{
		gestionCategories.createCategorie(categorieName);
	}

	public void deleteCategorie(String categorieName)
	{
		gestionCategories.deleteCategorie(categorieName);
	}

	public void renameCategorie(String oldCategorieName, String newCategorieName)
	{
		gestionCategories.updateCategorie(oldCategorieName, newCategorieName);
	}

	//Gestion réponses
	public ArrayList<Reponses> getListeReponses(String catName)
	{
		gestionReponses.readReponses(catName);
		return gestionReponses.getListReponses();
	}

	public void createReponses(String nomCategorie, String reponse1, String reponse2)
	{
		gestionReponses.createReponses(nomCategorie, reponse1, reponse2);
	}

	public void deleteReponses(String reponse1, String reponse2)
	{
		gestionReponses.deleteReponses(reponse1, reponse2);
	}

	public void modifyReponses(String categorie, String oldRep1, String oldRep2, String newRep1, String newRep2)
	{
		gestionReponses.updateReponses(categorie, oldRep1, oldRep2, newRep1, newRep2);
	}

	//Gestion questions
	public ArrayList<Question> getListeQuestions(String reponse1, String reponse2)
	{
		gestionQuestions.readQuestions(reponse1, reponse2);
		return gestionQuestions.getListeQuestions();
	}

	public void createQuestion(String intitule, String reponse1, String reponse2, int num_reponse)
	{
		gestionQuestions.createQuestion(intitule, reponse1, reponse2, num_reponse);
	}

	public void deleteQuestion(String intitule, String reponse1, String reponse2)
	{
		gestionQuestions.deleteQuestion(intitule, reponse1, reponse2);
	}

	public void modifyQuestion(String oldIntitule, String newIntitule, String reponse1, String reponse2, int newNum_reponse)
	{
		gestionQuestions.updateQuestion(oldIntitule, newIntitule, reponse1, reponse2, newNum_reponse);
	}
}