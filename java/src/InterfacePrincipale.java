import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;
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

	//barre de statut
	private JPanel statusBar;
	private JLabel statusText;

	//objet bdd
	private ConnexionBDD bdd;

	public InterfacePrincipale()
	{
		bdd = new ConnexionBDD("burgerquizz", 3306, "localhost", "alain", "chabat");

		setTitle("Administration base de données de l'aplication BurgerQuizz");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		loadImgBouton();
		createPanelCategories();
		createPanelReponses();
		createPanelQuestion();
		createStatusBar();


		JSplitPane sp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,panReponses,panQuestions);
		JSplitPane sp1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,panCategories,sp2);
		sp1.setBorder(BorderFactory.createLineBorder(new Color(238,238,238),5));
		sp2.setBorder(BorderFactory.createEmptyBorder());
		sp1.setDividerSize(10);
		sp2.setDividerSize(10);

		JPanel conteneur = new JPanel();
		conteneur.setLayout(new BorderLayout());
		conteneur.setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
		conteneur.add(sp1, BorderLayout.CENTER);

		Color bg = new Color(220,220,220);
		panQuestions.setBackground(bg);
		panReponses.setBackground(bg);
		panCategories.setBackground(bg);

		getContentPane().add(new JLabel(new ImageIcon("rsc/burgerquizz.png")), BorderLayout.NORTH);
		getContentPane().add(conteneur, BorderLayout.CENTER);
		getContentPane().add(statusBar, BorderLayout.SOUTH);

		pack();

		sp1.setDividerLocation(0.30);
		sp2.setDividerLocation(0.50);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void loadImgBouton()
	{
		plusImg = new ImageIcon("rsc/plus.png");
		delImg = new ImageIcon("rsc/del.png");
		editImg = new ImageIcon("rsc/edit.png");
	}

	private void createStatusBar()
	{
		statusBar = new JPanel();
		statusText = new JLabel("La licorne magique mange une tomate");

		Border border = BorderFactory.createMatteBorder(3,0,0,0, new Color(220,220,220));
		statusBar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10,70,5,70),border));

		statusBar.add(statusText);
	}

	private void createPanelCategories()
	{
		panCategories = new JPanel();
		addC = new Bouton("Ajouter une catégorie", plusImg);
		delC = new Bouton("Supprimer la catégorie", delImg);
		editC = new Bouton("Modifier la catégorie", editImg);

		String tab[] = {"a","b","c","d","e","f"};
		listC = new JList(bdd.getListeCategorie().toArray());
		JScrollPane sp = new JScrollPane(listC,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


		panCategories.setLayout(new BoxLayout(panCategories,Y_AXIS));
		panCategories.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		addC.setAlignmentX(CENTER_ALIGNMENT);
		delC.setAlignmentX(CENTER_ALIGNMENT);
		editC.setAlignmentX(CENTER_ALIGNMENT);
		addC.setMaximumSize(new Dimension(208,34));
		delC.setMaximumSize(new Dimension(208,34));
		editC.setMaximumSize(new Dimension(208,34));

		panCategories.add(addC);
		panCategories.add(Box.createRigidArea(new Dimension(1,5)));
		panCategories.add(delC);
		panCategories.add(Box.createRigidArea(new Dimension(1,10)));
		panCategories.add(sp);
		panCategories.add(Box.createRigidArea(new Dimension(1,10)));
		panCategories.add(editC);


		PanCategoriesListener pcl = new PanCategoriesListener();
		addC.addActionListener(pcl);
		delC.addActionListener(pcl);
		editC.addActionListener(pcl);
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
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


		panReponses.setLayout(new BoxLayout(panReponses,Y_AXIS));
		panReponses.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		addR.setAlignmentX(CENTER_ALIGNMENT);
		delR.setAlignmentX(CENTER_ALIGNMENT);
		editR.setAlignmentX(CENTER_ALIGNMENT);

		comboRepCat.setAlignmentX(CENTER_ALIGNMENT);
		addR.setMaximumSize(new Dimension(300,34));
		delR.setMaximumSize(new Dimension(300,34));
		editR.setMaximumSize(new Dimension(300,34));
		comboRepCat.setMaximumSize(new Dimension(1000,34));

		panReponses.add(addR);
		panReponses.add(Box.createRigidArea(new Dimension(1,5)));
		panReponses.add(delR);
		panReponses.add(Box.createRigidArea(new Dimension(1, 10)));
		panReponses.add(sp);
		panReponses.add(Box.createRigidArea(new Dimension(1, 10)));
		panReponses.add(comboRepCat);
		panReponses.add(Box.createRigidArea(new Dimension(1, 5)));
		panReponses.add(editR);


		PanReponsesListener prl = new PanReponsesListener();
		addR.addActionListener(prl);
		delR.addActionListener(prl);
		editR.addActionListener(prl);
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
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


		panQuestions.setLayout(new BoxLayout(panQuestions,Y_AXIS));
		panQuestions.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		addQ.setAlignmentX(CENTER_ALIGNMENT);
		delQ.setAlignmentX(CENTER_ALIGNMENT);
		editQ.setAlignmentX(CENTER_ALIGNMENT);
		addQ.setMaximumSize(new Dimension(208,34));
		delQ.setMaximumSize(new Dimension(208,34));
		editQ.setMaximumSize(new Dimension(208,34));
		comboQueRep.setMaximumSize(new Dimension(1000,34));

		panQuestions.add(addQ);
		panQuestions.add(Box.createRigidArea(new Dimension(1,5)));
		panQuestions.add(delQ);
		panQuestions.add(Box.createRigidArea(new Dimension(1, 10)));
		panQuestions.add(sp);
		panQuestions.add(Box.createRigidArea(new Dimension(1, 10)));
		panQuestions.add(comboQueRep);
		panQuestions.add(Box.createRigidArea(new Dimension(1, 5)));
		panQuestions.add(editQ);


		PanQuestionsListener pql = new PanQuestionsListener();
		addQ.addActionListener(pql);
		delQ.addActionListener(pql);
		editQ.addActionListener(pql);
	}

	public void modem56k()
	{
		int i =0;
		while(i != 500)
		{
			this.setSize(1000, i);
			try
			{
				sleep(100);
			}
			catch(InterruptedException e)
			{}
			i++;
		}
	}

	public void nyan()
	{
		BorderLayout bl = (BorderLayout)getContentPane().getLayout();
		JLabel l = (JLabel)bl.getLayoutComponent(BorderLayout.NORTH);
		l.setIcon(new ImageIcon("rsc/nyan.gif"));
		setLocationRelativeTo(null);
		pack();
	}


	private class PanCategoriesListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == addC)
			{
				JOptionPane jop = new JOptionPane();

				String catName = jop.showInputDialog(null,
						"Nom de la nouvelle categorie:",
						"Nouvelle catégorie",
						JOptionPane.QUESTION_MESSAGE);

				if(catName == null)
				{
					return ;
				}
				else if(catName.isEmpty())
				{
					statusText.setText("Une categorie ne peut porter un nom vide.");
					return ;
				}

				bdd.createCategorie(catName);
				listC.setListData(bdd.getListeCategorie().toArray());
			}
			else if(e.getSource() == delC)
			{
				JOptionPane jop = new JOptionPane();

				Categorie c = (Categorie) listC.getSelectedValue();

				if(c == null)
				{
					statusText.setText("Veuiller d'abord selectionner une categorie.");
					return;
				}

				String categorieName = c.getNom();

				if(jop.showConfirmDialog(null,"Voulez vous vraiment supprimer la catégorie " + categorieName + " ?\nCela supprimera aussi toute les reponses et questions associé à cette catégorie.", "Supression de catégorie", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
				{
					bdd.deleteCategorie(categorieName);
					listC.setListData(bdd.getListeCategorie().toArray());
				}
			}
			else if(e.getSource() == editC)
			{
				Categorie c = (Categorie) listC.getSelectedValue();

				if(c == null)
				{
					statusText.setText("Veuiller d'abord selectionner une categorie.");
					return;
				}

				JOptionPane jop = new JOptionPane();

				String oldCatName = c.getNom();

				String newCatName = jop.showInputDialog(null,
						"Nouveau nom pour la categorie " + oldCatName + ":",
						"Renomer catégorie",
						JOptionPane.QUESTION_MESSAGE);

				if(newCatName == null)
				{
					return ;
				}
				else if(newCatName.isEmpty())
				{
					statusText.setText("Une categorie ne peut porter un nom vide.");
					return ;
				}

				bdd.renameCategorie(oldCatName, newCatName);
				listC.setListData(bdd.getListeCategorie().toArray());
			}
		}
	}

	private class PanReponsesListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == addR)
			{
				statusText.setText("Création de réponses");
			}
			else if(e.getSource() == delR)
			{
				statusText.setText("Supression de réponses");
			}
			else if(e.getSource() == editR)
			{
				statusText.setText("Modification de réponses");
			}
		}
	}

	private class PanQuestionsListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == addQ)
			{
				statusText.setText("Création de question");
			}
			else if(e.getSource() == delQ)
			{
				statusText.setText("Supression de question");
			}
			else if(e.getSource() == editQ)
			{
				statusText.setText("Modification de question");
			}
		}
	}
}