import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnexionBDD
{
	Connection connexionbdd;
	String nomBdd;
	GestionCategories gestionCategories;
	GestionReponses gestionReponses;
	GestionQuestions gestionQuestions;

	public ConnexionBDD(String nomBdd, int port, String ip, String login, String password)
	{
		this.nomBdd = nomBdd;

		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		try
		{
			String url = "jdbc:mysql://" + ip + ":" + port + "/" + nomBdd;
			connexionbdd = DriverManager.getConnection(url, login, password);

			gestionCategories = new GestionCategories(connexionbdd);
			gestionReponses = new GestionReponses(connexionbdd);
			gestionQuestions = new GestionQuestions(connexionbdd);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println("Erreur de conexion à la base de données");
			System.exit(1);
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
		gestionCategories.insertCategorie(categorieName);
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
}