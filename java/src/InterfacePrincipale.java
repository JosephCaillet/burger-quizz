import javax.swing.*;
import java.awt.*;

public class InterfacePrincipale extends JFrame
{
	//Panel des catégories
	private JPanel panCategories;
	private Bouton addC;
	private Bouton delC;
	private Bouton editC;
	private JList listC;

	//Panel des réponses
	private JPanel panReponses;
	private Bouton addR;
	private Bouton delR;
	private Bouton editR;
	private JComboBox comboRepCat;
	private JList listR;

	//Panel des questions
	private JPanel panQuestions;
	private Bouton addQ;
	private Bouton delQ;
	private Bouton editQ;
	private JComboBox comboQueRep;
	private JList list;

	public InterfacePrincipale()
	{
		setLocationRelativeTo(null);
		setTitle("Administration base de données de l'aplication BurgerQuizz ce titre est trop long");
		setVisible(true);
	}

	private void createPanelCategories(){}
	private void createPanelReponses(){}
	private void createPanelQuestion(){}
}