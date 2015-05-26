import javax.swing.*;
import java.awt.*;

import static javax.swing.BoxLayout.*;

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
	private JList listQ;

	//image pour les boutons
	private ImageIcon plusImg;
	private ImageIcon delImg;
	private ImageIcon editImg;

	public InterfacePrincipale()
	{
		setTitle("Administration base de données de l'aplication BurgerQuizz");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		loadImgBouton();
		createPanelCategories();
		createPanelReponses();
		createPanelQuestion();

		getContentPane().add(new JLabel(new ImageIcon("rsc/burgerquizz.png")), BorderLayout.NORTH);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void loadImgBouton()
	{
		plusImg = new ImageIcon("rsc/plus.png");
		delImg = new ImageIcon("rsc/del.png");
		editImg = new ImageIcon("rsc/edit.png");
	}

	private void createPanelCategories()
	{
		panCategories = new JPanel();
		addC = new Bouton("Ajouter une catégorie", plusImg);
		delC = new Bouton("Supprimer la catégorie", delImg);
		editC = new Bouton("Modifier la catégorie", editImg);

		String tab[] = {"a","b","c","d","e","f"};
		listC = new JList(tab);
		JScrollPane sp = new JScrollPane(listC,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


		panCategories.setLayout(new BoxLayout(panCategories,Y_AXIS));
		addC.setAlignmentX(CENTER_ALIGNMENT);
		delC.setAlignmentX(CENTER_ALIGNMENT);
		editC.setAlignmentX(CENTER_ALIGNMENT);

		panCategories.add(addC);
		panCategories.add(delC);
		panCategories.add(sp);
		panCategories.add(editC);
		getContentPane().add(panCategories, BorderLayout.WEST);
	}

	private void createPanelReponses()
	{
		panReponses = new JPanel();
		addR = new Bouton("Ajouter un jeu de réponses", plusImg);
		delR = new Bouton("Supprimer le jeu de réponses", delImg);
		editR = new Bouton("Modifier le jeu de réponse", editImg);
		comboRepCat = new JComboBox();

		String tab[] = {"a","b","c","d","e","f"};
		listR = new JList(tab);
		JScrollPane sp = new JScrollPane(listR,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


		panReponses.setLayout(new BoxLayout(panReponses,Y_AXIS));
		addR.setAlignmentX(CENTER_ALIGNMENT);
		delR.setAlignmentX(CENTER_ALIGNMENT);
		editR.setAlignmentX(CENTER_ALIGNMENT);

		panReponses.add(addR);
		panReponses.add(delR);
		panReponses.add(sp);
		panReponses.add(editR);
		panReponses.add(comboRepCat);
		getContentPane().add(panReponses, BorderLayout.CENTER);
	}

	private void createPanelQuestion()
	{
		panQuestions = new JPanel();
		addQ = new Bouton("Ajouter une question", plusImg);
		delQ = new Bouton("Supprimer la question", delImg);
		editQ = new Bouton("Modifier la question", editImg);
		comboQueRep = new JComboBox();

		String tab[] = {"a","b","c","d","e","f"};
		listQ = new JList(tab);
		JScrollPane sp = new JScrollPane(listQ,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


		panQuestions.setLayout(new BoxLayout(panQuestions,Y_AXIS));
		addQ.setAlignmentX(CENTER_ALIGNMENT);
		delQ.setAlignmentX(CENTER_ALIGNMENT);
		editQ.setAlignmentX(CENTER_ALIGNMENT);

		panQuestions.add(addQ);
		panQuestions.add(delQ);
		panQuestions.add(sp);
		panQuestions.add(editQ);
		panQuestions.add(comboQueRep);
		getContentPane().add(panQuestions, BorderLayout.EAST);
	}
}