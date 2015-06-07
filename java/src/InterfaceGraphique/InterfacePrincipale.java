package InterfaceGraphique;

import InterfaceGraphique.DialogBoxes.ConnexionBddDialog;
import InterfaceGraphique.DialogBoxes.NouvelleQuestionDialog;
import InterfaceGraphique.DialogBoxes.NouvelleReponseDialog;
import GestionBddDAO.ConnexionBDD;
import Modele.Categorie;
import GestionBddDAO.ConfigBDD;
import Modele.Question;
import Modele.Reponses;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;

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
	private JList listR;

	//Panel des questions
	private JPanel panQuestions;
	private Bouton addQ;
	private Bouton delQ;
	private Bouton editQ;
	private JList listQ;

	//image pour les boutons
	private ImageIcon plusImg;
	private ImageIcon delImg;
	private ImageIcon editImg;
	private ImageIcon setupImg;

	//barre de statut
	private JPanel statusBar;
	private JLabel statusText;
	private Bouton config;

	//objet bdd
	private ConnexionBDD bdd;
	private ConfigBDD configBDD;

	public InterfacePrincipale()
	{
		configBDD = new ConfigBDD();
		configBDD.loadConf();
		bdd = new ConnexionBDD();

		tryToConnect();

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
		setupImg = new ImageIcon("rsc/cle.png");
	}

	private void createStatusBar()
	{
		statusBar = new JPanel(new BorderLayout(0,0));
		statusText = new JLabel("Application demarrée, connexion à la base de donné effective. ");

		Border border = BorderFactory.createMatteBorder(3,0,0,0, new Color(220,220,220));
		statusBar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10,70,5,70),border));

		statusText.setHorizontalAlignment(SwingConstants.CENTER);

		config = new Bouton("Configuration", setupImg);
		config.setPreferredSize(new Dimension(200, 34));
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(config, BorderLayout.CENTER);
		p.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));

		statusBar.add(statusText, BorderLayout.CENTER);
		statusBar.add(p, BorderLayout.EAST);

		config.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				configureBDD(false);
				tryToConnect();
				listC.setListData(bdd.getListeCategorie().toArray());
				listR.setListData(new Vector(0));
				listQ.setListData(new Vector(0));
			}
		});
	}

	private void createPanelCategories()
	{
		panCategories = new JPanel();
		addC = new Bouton("Ajouter une catégorie", plusImg);
		delC = new Bouton("Supprimer la catégorie", delImg);
		editC = new Bouton("Modifier la catégorie", editImg);

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

		JLabel labC = new JLabel("Catégories");
		labC.setAlignmentX(Component.CENTER_ALIGNMENT);
		labC.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

		panCategories.add(labC);
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
		listC.addListSelectionListener(pcl);
	}

	private void createPanelReponses()
	{
		panReponses = new JPanel();
		addR = new Bouton("Ajouter un jeu de réponses", plusImg);
		delR = new Bouton("Supprimer le jeu de réponses", delImg);
		editR = new Bouton("Modifier le jeu de réponse", editImg);

		listR = new JList();
		JScrollPane sp = new JScrollPane(listR,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


		panReponses.setLayout(new BoxLayout(panReponses,Y_AXIS));
		panReponses.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		addR.setAlignmentX(CENTER_ALIGNMENT);
		delR.setAlignmentX(CENTER_ALIGNMENT);
		editR.setAlignmentX(CENTER_ALIGNMENT);

		addR.setMaximumSize(new Dimension(300,34));
		delR.setMaximumSize(new Dimension(300,34));
		editR.setMaximumSize(new Dimension(300,34));

		JLabel labR = new JLabel("Réponses");
		labR.setAlignmentX(Component.CENTER_ALIGNMENT);
		labR.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

		panReponses.add(labR);
		panReponses.add(addR);
		panReponses.add(Box.createRigidArea(new Dimension(1,5)));
		panReponses.add(delR);
		panReponses.add(Box.createRigidArea(new Dimension(1, 10)));
		panReponses.add(sp);
		panReponses.add(Box.createRigidArea(new Dimension(1, 10)));
		panReponses.add(editR);


		PanReponsesListener prl = new PanReponsesListener();
		addR.addActionListener(prl);
		delR.addActionListener(prl);
		editR.addActionListener(prl);
		listR.addListSelectionListener(prl);
	}

	private void createPanelQuestion()
	{
		panQuestions = new JPanel();
		addQ = new Bouton("Ajouter une question", plusImg);
		delQ = new Bouton("Supprimer la question", delImg);
		editQ = new Bouton("Modifier la question", editImg);

		listQ = new JList();
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

		JLabel labQ = new JLabel("Questions");
		labQ.setAlignmentX(Component.CENTER_ALIGNMENT);
		labQ.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

		panQuestions.add(labQ);
		panQuestions.add(addQ);
		panQuestions.add(Box.createRigidArea(new Dimension(1,5)));
		panQuestions.add(delQ);
		panQuestions.add(Box.createRigidArea(new Dimension(1, 10)));
		panQuestions.add(sp);
		panQuestions.add(Box.createRigidArea(new Dimension(1, 10)));
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

	public void configureBDD(boolean showExitButton)
	{
		ConnexionBddDialog cbddd = new ConnexionBddDialog(configBDD.getNomBdd(), configBDD.getPort(),
				configBDD.getIp(), configBDD.getLogin(),
				configBDD.getPassword(), null, showExitButton);
		if(cbddd.afficher() == true)
		{
			configBDD.setNomBdd(cbddd.getNomBdd());
			configBDD.setIp(cbddd.getIp());
			configBDD.setPort(cbddd.getPort());
			configBDD.setLogin(cbddd.getLogin());
			configBDD.setPassword(cbddd.getPassword());

			configBDD.saveConf();
		}
	}

	private void tryToConnect()
	{
		boolean conOK = false;
		do
		{
			conOK = bdd.connect(configBDD.getNomBdd(), configBDD.getPort(), configBDD.getIp(), configBDD.getLogin(), configBDD.getPassword());
			if(conOK == false)
			{
				JOptionPane.showMessageDialog(this, "Impossible d'établir la connexion à la base de données.", "Erreur conexion base de données", JOptionPane.ERROR_MESSAGE);
				configureBDD(true);
			}
		}while(!conOK);
	}

	private void setStatusText(String message, Color color)
	{
		statusText.setForeground(color);
		statusText.setText(message);
	}

	private void reSelectCategorie(String newCatName)
	{
		Object[] tabObject = bdd.getListeCategorie().toArray();
		Categorie[] tabCategorie = Arrays.copyOf(tabObject, tabObject.length, Categorie[].class);
		listC.setListData(tabCategorie);

		for(int i=0; i<tabCategorie.length; i++)
		{
			if(tabCategorie[i].getNom().equals(newCatName))
			{
				listC.setSelectedValue(tabCategorie[i], true);
				break;
			}
		}
	}

	private void reSelectReponses(String rep1, String rep2)
	{
		Object[] tabObject = bdd.getListeReponses(listC.getSelectedValue().toString()).toArray();
		Reponses[] tabReponses = Arrays.copyOf(tabObject, tabObject.length, Reponses[].class);
		listR.setListData(tabReponses);

		for(int i=0; i<tabReponses.length; i++)
		{
			if(tabReponses[i].getReponse1().equals(rep1) && tabReponses[i].getReponse2().equals(rep2))
			{
				listR.setSelectedValue(tabReponses[i], true);
				break;
			}
		}
	}

	private void reSelectQuestion(String intitule)
	{
		Reponses r = (Reponses) listR.getSelectedValue();
		Object[] tabObject = bdd.getListeQuestions(r.getReponse1(), r.getReponse2()).toArray();
		Question[] tabQuestions = Arrays.copyOf(tabObject, tabObject.length, Question[].class);
		listQ.setListData(tabQuestions);

		for(int i=0; i<tabQuestions.length; i++)
		{
			if(tabQuestions[i].getIntitule().equals(intitule))
			{
				listQ.setSelectedValue(tabQuestions[i], true);
				break;
			}
		}
	}

	private String[] getCategorieList()
	{
		ListModel model = listC.getModel();
		String[] tabCategories = new String[model.getSize()];

		for(int i=0; i < model.getSize(); i++)
		{
			Categorie c =  (Categorie)model.getElementAt(i);
			tabCategories[i] = c.getNom();
		}
		return tabCategories;
	}

	private class PanCategoriesListener implements ActionListener, ListSelectionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == addC)
			{
				String catName = JOptionPane.showInputDialog(null,
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
				reSelectCategorie(catName);
				listQ.setListData(new Vector(0));
			}
			else if(e.getSource() == delC)
			{
				Categorie c = (Categorie) listC.getSelectedValue();

				if(c == null)
				{
					statusText.setText("Veuiller d'abord selectionner une categorie.");
					return;
				}

				String categorieName = c.getNom();

				if(JOptionPane.showConfirmDialog(null, "Voulez vous vraiment supprimer la catégorie " + categorieName + " ?\nCela supprimera aussi toute les reponses et questions associé à cette catégorie.", "Supression de catégorie", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
				{
					bdd.deleteCategorie(categorieName);
					listC.setListData(bdd.getListeCategorie().toArray());
					listR.setListData(new Vector(0));
					listQ.setListData(new Vector(0));
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

				String oldCatName = c.getNom();

				String newCatName = JOptionPane.showInputDialog(null,
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
				reSelectCategorie(newCatName);
			}
		}

		public void valueChanged(ListSelectionEvent e)
		{
			if(!listC.isSelectionEmpty())
			{
				listR.setListData(bdd.getListeReponses(listC.getSelectedValue().toString()).toArray());
				listQ.setListData(new Vector(0));
			}
		}
	}

	private class PanReponsesListener implements ActionListener, ListSelectionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(listC.isSelectionEmpty())
			{
				statusText.setText("Veuillez selectioner une catégorie.");
				return;
			}

			if(e.getSource() == addR)
			{
				NouvelleReponseDialog nrd = new NouvelleReponseDialog("Nouveau jeu de réponses", "", "", null, null, null);
				if(nrd.afficher() == true)
				{
					String catName = listC.getSelectedValue().toString();
					bdd.createReponses(catName, nrd.getRep1(), nrd.getRep2());
					reSelectReponses(nrd.getRep1(), nrd.getRep2());
				}
			}
			else if(e.getSource() == delR)
			{
				Reponses r = (Reponses) listR.getSelectedValue();

				if(r == null)
				{
					statusText.setText("Veuiller d'abord selectionner un jeu de réponses.");
					return;
				}

				String reponse1 = r.getReponse1();
				String reponse2 = r.getReponse2();

				if(JOptionPane.showConfirmDialog(null,"Voulez vous vraiment supprimer le jeu de réponses " + reponse1 + ", " + reponse2 + " ?\nCela supprimera aussi toutes les questions associé à cette catégorie.", "Supression de réponses", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
				{
					bdd.deleteReponses(reponse1, reponse2);
					listR.setListData(bdd.getListeReponses(listC.getSelectedValue().toString()).toArray());
					listQ.setListData(new Vector(0));
				}
			}
			else if(e.getSource() == editR)
			{
				Reponses r = (Reponses) listR.getSelectedValue();

				if(r == null)
				{
					statusText.setText("Veuiller d'abord selectionner un jeu de réponses.");
					return;
				}

				String reponse1 = r.getReponse1();
				String reponse2 = r.getReponse2();
				String catName = listC.getSelectedValue().toString();

				NouvelleReponseDialog nrd = new NouvelleReponseDialog("Modification jeu de réponses", reponse1, reponse2, catName, getCategorieList(), null);
				if(nrd.afficher() == true)
				{
					bdd.modifyReponses(nrd.getCat(), reponse1, reponse2, nrd.getRep1(), nrd.getRep2());
					reSelectCategorie(nrd.getCat());
					reSelectReponses(nrd.getRep1(), nrd.getRep2());
				}
			}
		}

		public void valueChanged(ListSelectionEvent listSelectionEvent)
		{
			if(!listR.isSelectionEmpty())
			{
				Reponses r = (Reponses) listR.getSelectedValue();
				listQ.setListData(bdd.getListeQuestions(r.getReponse1(), r.getReponse2()).toArray());
			}
		}
	}

	private class PanQuestionsListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(listR.isSelectionEmpty())
			{
				statusText.setText("Veuillez selectioner une sous-catégorie.");
				return;
			}

			if(e.getSource() == addQ)
			{
				Reponses r = (Reponses) listR.getSelectedValue();
				NouvelleQuestionDialog nqd = new NouvelleQuestionDialog("Nouvelle question", "",
						0, r.getReponse1(),r.getReponse2(), null);

				if(nqd.afficher() == true)
				{
					bdd.createQuestion(nqd.getIntitule(), r.getReponse1(), r.getReponse2(), nqd.getReponse());
					reSelectQuestion(nqd.getIntitule());
				}
			}
			else if(e.getSource() == delQ)
			{
				Question q = (Question) listQ.getSelectedValue();

				if(q == null)
				{
					statusText.setText("Veuiller d'abord selectionner une question.");
					return;
				}

				if(JOptionPane.showConfirmDialog(null, "Voulez vous vraiment supprimer la question " + q.getIntitule() + " ?", "Supression de question", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
				{
					bdd.deleteQuestion(q.getIntitule(), q.getReponse1(), q.getReponse2());
					listQ.setListData(bdd.getListeQuestions(q.getReponse1(), q.getReponse2()).toArray());
				}
			}
			else if(e.getSource() == editQ)
			{
				Question q = (Question) listQ.getSelectedValue();
				if(q == null)
				{
					statusText.setText("Veuiller d'abord selectionner une question.");
					return;
				}

				NouvelleQuestionDialog nqd = new NouvelleQuestionDialog("Modification question", q.getIntitule(),
						q.getReponse(), q.getReponse1(), q.getReponse2(), null);

				if(nqd.afficher() == true)
				{
					bdd.modifyQuestion(q.getIntitule(), nqd.getIntitule(),
							q.getReponse1(), q.getReponse2(), nqd.getReponse());
					reSelectQuestion(nqd.getIntitule());
				}
			}
		}
	}
}