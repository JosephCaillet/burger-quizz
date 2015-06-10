package InterfaceGraphique;

import GestionErreurs.BDDException;
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

import static javax.swing.BoxLayout.*;

/**
 * Interface graphique principale
 */
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

	//constantes couleurs
	private static final Color ERROR_COLOR = Color.RED;
	private static final Color INFO_COLOR = new Color(50,50,255);
	private static final Color NORMAL_COLOR = new Color(50,50,50);

	/**
	 * Constructeur lancant le splashcreen, la connexion à la base de donné, et la construction de l'interface principale.
	 */
	public InterfacePrincipale()
	{
		SplashScreen splashScreen = new SplashScreen(this);

		splashScreen.setLoadingProgress(SplashScreen.LOADING_BDD_CONF);
		configBDD = new ConfigBDD();
		configBDD.loadConf();
		bdd = new ConnexionBDD();

		splashScreen.setLoadingProgress(SplashScreen.LOADING_BDD_CONNECT);
		tryToConnect();

		setTitle("Administration base de données de l'aplication BurgerQuizz");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon("rsc/icon.png").getImage());
		setLayout(new BorderLayout());

		splashScreen.setLoadingProgress(SplashScreen.LOADING_RSC);
		loadImgBouton();
		splashScreen.setLoadingProgress(SplashScreen.LOADING_CREATE_STATUS_BAR);
		createStatusBar();
		splashScreen.setLoadingProgress(SplashScreen.LOADING_CREATE_PANEL_CATEGORIES);
		createPanelCategories();
		splashScreen.setLoadingProgress(SplashScreen.LOADING_CREATE_PANEL_REPONSES);
		createPanelReponses();
		splashScreen.setLoadingProgress(SplashScreen.LOADING_CREATE_PANEL_QUESTIONS);
		createPanelQuestion();

		splashScreen.setLoadingProgress(SplashScreen.LOADING_SETUP_UI);
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
		splashScreen.setLoadingProgress(SplashScreen.LOADING_READY);
		setVisible(true);
	}

	/**
	 * Charge les images pour les boutons.
	 */
	private void loadImgBouton()
	{
		plusImg = new ImageIcon("rsc/plus.png");
		delImg = new ImageIcon("rsc/del.png");
		editImg = new ImageIcon("rsc/edit.png");
		setupImg = new ImageIcon("rsc/cle.png");
	}

	/**
	 * Initialise et configure la barre de statut.
	 */
	private void createStatusBar()
	{
		statusBar = new JPanel(new BorderLayout(0,0));

		Border border = BorderFactory.createMatteBorder(3,0,0,0, new Color(220,220,220));
		statusBar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10,70,5,70),border));

		statusText = new JLabel();
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
				if(configureBDD(false))
				{
					tryToConnect();
					try
					{
						listC.setListData(bdd.getListeCategorie().toArray());
						setQuestionsPanelEnabled(false);
						setReponsesPanelEnabled(false);
						setStatusText("Connexion à la base de données établie.");
					}
					catch (BDDException e)
					{
						setStatusText(e.getMessage(), ERROR_COLOR);
					}
				}
			}
		});
	}

	/**
	 * Initialise et configure le panneaux de gestion des catégorie.
	 */
	private void createPanelCategories()
	{
		panCategories = new JPanel();
		addC = new Bouton("Ajouter une catégorie", plusImg);
		delC = new Bouton("Supprimer la catégorie", delImg);
		editC = new Bouton("Modifier la catégorie", editImg);

		try
		{
			listC = new JList(bdd.getListeCategorie().toArray());
			setStatusText("Application demarrée, connexion à la base de données initiée.");
		}
		catch (BDDException e)
		{
			setStatusText(e.getMessage(), ERROR_COLOR);
		}

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

	/**
	 * Initialise et configure le panneaux de gestion des réponses.
	 */
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

		setReponsesPanelEnabled(false);
	}

	/**
	 * Initialise et configure le panneaux de gestion des questions.
	 */
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

		setQuestionsPanelEnabled(false);
	}

	/**
	 * easter-egg
	 */
	public void modem56k()
	{
		int i =0;
		while(i != 500)
		{
			this.setSize(1000, i);
			try
			{
				Thread.sleep(100);
			}
			catch(InterruptedException e)
			{}
			i++;
		}
	}

	/**
	 * easter-egg
	 */
	public void nyan()
	{
		BorderLayout bl = (BorderLayout)getContentPane().getLayout();
		JLabel l = (JLabel)bl.getLayoutComponent(BorderLayout.NORTH);
		l.setIcon(new ImageIcon("rsc/nyan.gif"));
		setLocationRelativeTo(null);
		pack();
	}

	/**
	 * Affiche une boite de dialogue pour modifier et sauvegarder une nouvelle configuration.
	 * @param showExitButton Si true, bouton annuler est remplacé par un bouton pour quitter l'aplication.
	 * @return true si la configuration à été modifiée, false sinon.
	 */
	public boolean configureBDD(boolean showExitButton)
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
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Essaye de se connecter à la base de données, et demande à modifier la configuration de la connexion si echec.
	 */
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

	/**
	 * Change le texte de la barre de statut.
	 * @param message Message.
	 * @param color Couleur;
	 */
	private void setStatusText(String message, Color color)
	{
		statusText.setForeground(color);
		statusText.setText(message);
	}

	/**
	 * Change le texte de la barre de statut et el met en noir.
	 * @param message Message.
	 */
	private void setStatusText(String message)
	{
		statusText.setForeground(NORMAL_COLOR);
		statusText.setText(message);
	}

	/**
	 * Active ou désactive le panneaux de gestion des réponses.
	 * @param active panneaux actif ou non.
	 */
	private void setReponsesPanelEnabled(boolean active)
	{
		addR.setEnabled(active);
		delR.setEnabled(active);
		editR.setEnabled(active);
		listR.setEnabled(active);
		listR.setListData(new Vector(0));
	}

	/**
	 * Active ou désactive le panneaux de gestion des questions.
	 * @param active panneaux actif ou non.
	 */
	private void setQuestionsPanelEnabled(boolean active)
	{
		addQ.setEnabled(active);
		delQ.setEnabled(active);
		editQ.setEnabled(active);
		listQ.setEnabled(active);
		listQ.setListData(new Vector(0));
	}

	/**
	 * Selectionne à nouveau une catégorie.
	 * @param newCatName La catégorie à selectionner.
	 * @throws BDDException Si une erreur SQL survient.
	 */
	private void reSelectCategorie(String newCatName) throws BDDException
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

	/**
	 * Selectionne à nouveau un jeu de reponses.
	 * @param rep1 La réponse 1
	 * @param rep2 La réponse 2
	 * @throws BDDException Si une erreur SQL survient.
	 */
	private void reSelectReponses(String rep1, String rep2) throws BDDException
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

	/**
	 * Selectionne à nouveau une question.
	 * @param intitule L'intitulé.
	 * @throws BDDException Si une erreur SQL survient.
	 */
	private void reSelectQuestion(String intitule) throws BDDException
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

	/**
	 * Selectionne une liste des categories.
	 * @return Un tableau de noms de catégories.
	 */
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

	/**
	 * Classe interne pour l'ecoute d'événements sur le panneaux de gestion des catégories.
	 * @author joseph
	 */
	private class PanCategoriesListener implements ActionListener, ListSelectionListener
	{
		/**
		 * Réagit au clic sur un des boutons du panneux des catégories.
		 * @param e Un evenement au clic sur un bouton.
		 */
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
					setStatusText("Une categorie ne peut porter un nom vide.", INFO_COLOR);
					return ;
				}

				try
				{
					bdd.createCategorie(catName);
					reSelectCategorie(catName);
					setQuestionsPanelEnabled(false);
					setStatusText("La catégorie " + catName + " à bien été créée.");
				}
				catch (BDDException ex)
				{
					setStatusText(ex.getMessage(), ERROR_COLOR);
				}
			}
			else if(e.getSource() == delC)
			{
				Categorie c = (Categorie) listC.getSelectedValue();

				if(c == null)
				{
					setStatusText("Veuiller d'abord selectionner une categorie.", INFO_COLOR);
					return;
				}

				String categorieName = c.getNom();

				if(JOptionPane.showConfirmDialog(null, "Voulez vous vraiment supprimer la catégorie " + categorieName + " ?\nCela supprimera aussi toute les reponses et questions associé à cette catégorie.", "Supression de catégorie", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
				{
					try
					{
						bdd.deleteCategorie(categorieName);
						listC.setListData(bdd.getListeCategorie().toArray());
						setQuestionsPanelEnabled(false);
						setReponsesPanelEnabled(false);
						setStatusText("La catégorie " + categorieName + "à bien été suprimée.");
					}
					catch (BDDException ex)
					{
						setStatusText(ex.getMessage(), ERROR_COLOR);
					}
				}
			}
			else if(e.getSource() == editC)
			{
				Categorie c = (Categorie) listC.getSelectedValue();

				if(c == null)
				{
					setStatusText("Veuiller d'abord selectionner une categorie.", INFO_COLOR);
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
					setStatusText("Une categorie ne peut porter un nom vide.", INFO_COLOR);
					return ;
				}

				try
				{
					bdd.renameCategorie(oldCatName, newCatName);
					reSelectCategorie(newCatName);
					setStatusText("La catégorie " + oldCatName + " à bien été renomée en " + newCatName + ".");
				}
				catch (BDDException ex)
				{
					setStatusText(ex.getMessage(), ERROR_COLOR);
				}
			}
		}

		/**
		 * Réagit à la selection d'une catégorie.
		 * @param e Un evenement a la selection d'une catégorie.
		 */
		public void valueChanged(ListSelectionEvent e)
		{
			if(!listC.isSelectionEmpty())
			{
				try
				{
					Object[] tabRep = bdd.getListeReponses(listC.getSelectedValue().toString()).toArray();
					setReponsesPanelEnabled(true);
					listR.setListData(tabRep);
					setQuestionsPanelEnabled(false);
					setStatusText("Récupération des jeux de questions effectuée");
				}
				catch (BDDException ex)
				{
					setStatusText(ex.getMessage(), ERROR_COLOR);
				}
			}
		}
	}

	/**
	 * Classe interne pour l'ecoute d'événements sur le panneaux de gestion des réponses.
	 * @author joseph
	 */
	private class PanReponsesListener implements ActionListener, ListSelectionListener
	{
		/**
		 * Réagit au clic sur un des boutons du panneux des réponses.
		 * @param e Un evenement au clic sur un bouton.
		 */
		public void actionPerformed(ActionEvent e)
		{
			if(listC.isSelectionEmpty())
			{
				setStatusText("Veuillez selectioner une catégorie.", INFO_COLOR);
				return;
			}

			if(e.getSource() == addR)
			{
				NouvelleReponseDialog nrd = new NouvelleReponseDialog("Nouveau jeu de réponses", "", "", null, null, null);
				if(nrd.afficher() == true)
				{
					String catName = listC.getSelectedValue().toString();
					try
					{
						bdd.createReponses(catName, nrd.getRep1(), nrd.getRep2());
						reSelectReponses(nrd.getRep1(), nrd.getRep2());
						setStatusText("Le jeu de réponse à bien été créé.");
					}
					catch (BDDException ex)
					{
						setStatusText(ex.getMessage(), ERROR_COLOR);
					}
				}
			}
			else if(e.getSource() == delR)
			{
				Reponses r = (Reponses) listR.getSelectedValue();

				if(r == null)
				{
					setStatusText("Veuiller d'abord selectionner un jeu de réponses.", INFO_COLOR);
					return;
				}

				String reponse1 = r.getReponse1();
				String reponse2 = r.getReponse2();

				if(JOptionPane.showConfirmDialog(null,"Voulez vous vraiment supprimer le jeu de réponses " + reponse1 + ", " + reponse2 + " ?\nCela supprimera aussi toutes les questions associé à cette catégorie.", "Supression de réponses", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
				{
					try
					{
						bdd.deleteReponses(reponse1, reponse2);
						Object[] tabRep = bdd.getListeReponses(listC.getSelectedValue().toString()).toArray();
						setReponsesPanelEnabled(true);
						listR.setListData(tabRep);
						setQuestionsPanelEnabled(false);
						setStatusText("Le jeu de réponse à bien été suprimé.");
					}
					catch (BDDException ex)
					{
						setStatusText(ex.getMessage(), ERROR_COLOR);
					}
				}
			}
			else if(e.getSource() == editR)
			{
				Reponses r = (Reponses) listR.getSelectedValue();

				if(r == null)
				{
					setStatusText("Veuiller d'abord selectionner un jeu de réponses.", INFO_COLOR);
					return;
				}

				String reponse1 = r.getReponse1();
				String reponse2 = r.getReponse2();
				String catName = listC.getSelectedValue().toString();

				NouvelleReponseDialog nrd = new NouvelleReponseDialog("Modification jeu de réponses", reponse1, reponse2, catName, getCategorieList(), null);
				if(nrd.afficher() == true)
				{
					try
					{
						bdd.modifyReponses(nrd.getCat(), reponse1, reponse2, nrd.getRep1(), nrd.getRep2());
						reSelectCategorie(nrd.getCat());
						reSelectReponses(nrd.getRep1(), nrd.getRep2());
						setStatusText("Le jeu de réponse à bien été modifié.");
					}
					catch (BDDException ex)
					{
						setStatusText(ex.getMessage(), ERROR_COLOR);
					}
				}
			}
		}

		/**
		 * Réagit à la selection d'un jeu de réponses.
		 * @param listSelectionEvent Un evenement a la selection d'un jeu de réponses.
		 */
		public void valueChanged(ListSelectionEvent listSelectionEvent)
		{
			if(!listR.isSelectionEmpty())
			{
				Reponses r = (Reponses) listR.getSelectedValue();
				try
				{
					Object[] tabQue = bdd.getListeQuestions(r.getReponse1(), r.getReponse2()).toArray();
					setQuestionsPanelEnabled(true);
					listQ.setListData(tabQue);
					setStatusText("Récupération des questions effectuée");
				}
				catch (BDDException e)
				{
					setStatusText(e.getMessage(), ERROR_COLOR);
				}
			}
		}
	}

	/**
	 * Classe interne pour l'ecoute d'événements sur le panneaux de gestion des questions.
	 * @author joseph
	 */
	private class PanQuestionsListener implements ActionListener
	{
		/**
		 * Réagit au clic sur un des boutons du panneux des questions.
		 * @param e Un evenement au clic sur un bouton.
		 */
		public void actionPerformed(ActionEvent e)
		{
			if(listR.isSelectionEmpty())
			{
				setStatusText("Veuillez selectioner une sous-catégorie.", INFO_COLOR);
				return;
			}

			if(e.getSource() == addQ)
			{
				Reponses r = (Reponses) listR.getSelectedValue();
				NouvelleQuestionDialog nqd = new NouvelleQuestionDialog("Nouvelle question", "",
						0, r.getReponse1(),r.getReponse2(), null);

				if(nqd.afficher() == true)
				{
					try
					{
						bdd.createQuestion(nqd.getIntitule(), r.getReponse1(), r.getReponse2(), nqd.getReponse());
						reSelectQuestion(nqd.getIntitule());
						setStatusText("La question à bien été créée.");
					}
					catch (BDDException ex)
					{
						setStatusText(ex.getMessage(), ERROR_COLOR);
					}
				}
			}
			else if(e.getSource() == delQ)
			{
				Question q = (Question) listQ.getSelectedValue();

				if(q == null)
				{
					setStatusText("Veuiller d'abord selectionner une question.", INFO_COLOR);
					return;
				}

				if(JOptionPane.showConfirmDialog(null, "Voulez vous vraiment supprimer la question " + q.getIntitule() + " ?", "Supression de question", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
				{
					try
					{
						bdd.deleteQuestion(q.getIntitule(), q.getReponse1(), q.getReponse2());
						listQ.setListData(bdd.getListeQuestions(q.getReponse1(), q.getReponse2()).toArray());
						setQuestionsPanelEnabled(true);
						setStatusText("La question à bien été suprimée.");
					}
					catch (BDDException ex)
					{
						setStatusText(ex.getMessage(), ERROR_COLOR);
					}
				}
			}
			else if(e.getSource() == editQ)
			{
				Question q = (Question) listQ.getSelectedValue();
				if(q == null)
				{
					setStatusText("Veuiller d'abord selectionner une question.", INFO_COLOR);
					return;
				}

				NouvelleQuestionDialog nqd = new NouvelleQuestionDialog("Modification question", q.getIntitule(),
						q.getReponse(), q.getReponse1(), q.getReponse2(), null);

				if(nqd.afficher() == true)
				{
					try
					{
						bdd.modifyQuestion(q.getIntitule(), nqd.getIntitule(),
								q.getReponse1(), q.getReponse2(), nqd.getReponse());
						reSelectQuestion(nqd.getIntitule());
						setStatusText("La question à bien été modifiée.");
					}
					catch (BDDException ex)
					{
						setStatusText(ex.getMessage(), ERROR_COLOR);
					}
				}
			}
		}
	}
}