import java.util.ArrayList;

public class GestionCategories
{
	private ConnexionBDD bdd;
	private ArrayList<Categorie> listCategories;

	public GestionCategories(ConnexionBDD bdd)
	{
		this.bdd = bdd;
	}
}