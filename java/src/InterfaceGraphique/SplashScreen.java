package InterfaceGraphique;

import javax.swing.*;
import java.awt.*;

/**
 * Ecran affiché au démarage de l'application.
 * @author joseph
 */
public class SplashScreen extends JWindow
{
	/**
	 * Barre de progression.
	 */
	private JProgressBar progressBar;

	/**
	 * Chargement configuration bdd.
	 */
	public static final int LOADING_BDD_CONF = 0;
	/**
	 * Connection à la bdd.
	 */
	public static final int LOADING_BDD_CONNECT = 1;
	/**
	 * Chargement des ressources graphiques.
	 */
	public static final int LOADING_RSC = 2;
	/**
	 * Création barre de statut.
	 */
	public static final int LOADING_CREATE_STATUS_BAR = 3;
	/**
	 * Création panneau de gestion des catégorie.
	 */
	public static final int LOADING_CREATE_PANEL_CATEGORIES = 4;
	/**
	 * Création panneau de gestion des réponses.
	 */
	public static final int LOADING_CREATE_PANEL_REPONSES = 5;
	/**
	 * Création panneau de gestion des questions.
	 */
	public static final int LOADING_CREATE_PANEL_QUESTIONS = 6;
	/**
	 * Chargement de l'IHM.
	 */
	public static final int LOADING_SETUP_UI = 7;
	/**
	 * Chargement terminé.
	 */
	public static final int LOADING_READY = 8;

	/**
	 * Constructeur initiant les différents membres et construisant l'interface du splashscreen.
	 * @param owner Parent.
	 */
	public SplashScreen(JFrame owner) {
		super(owner);

		JLabel img = new JLabel(new ImageIcon("rsc/splashscreen.gif"));
		img.setBorder(BorderFactory.createMatteBorder(10,10,5,10,new Color(248,179,52)));
		progressBar = new JProgressBar(0,9);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setString("");
		progressBar.setBorder(BorderFactory.createMatteBorder(5,10,10,10,new Color(248,179,52)));

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(img, BorderLayout.CENTER);
		this.getContentPane().add(progressBar, BorderLayout.SOUTH);

		this.pack();
		setLocationRelativeTo(null);

		this.setVisible(true);
	}

	/**
	 * Change l'état du splashscreen: avance l'état de la barre de chargement et change son texte. Une pause de 250ms est imposée entre chaque état du chargement pour faciliter la lecture des message.
	 * @param status Une des constantes de classes représentant un état du chargement.
	 */
	public void setLoadingProgress(int status)
	{
		switch (status)
		{
			case LOADING_BDD_CONF:
				progressBar.setString("Chargement de la configuration à la base de données...");
				break;
			case LOADING_BDD_CONNECT:
				progressBar.setString("Connexion à la base de données...");
				break;
			case LOADING_RSC:
				progressBar.setString("Chargement des ressources graphiques...");
				break;
			case LOADING_CREATE_STATUS_BAR:
				progressBar.setString("Initialisation de la barre de statut...");
				break;
			case LOADING_CREATE_PANEL_CATEGORIES:
				progressBar.setString("Initialisation du panel des catégories...");
				break;
			case LOADING_CREATE_PANEL_REPONSES:
				progressBar.setString("Initialisation du panel des réponses...");
				break;
			case LOADING_CREATE_PANEL_QUESTIONS:
				progressBar.setString("Initialisation du panel des questions...");
				break;
			case LOADING_SETUP_UI:
				progressBar.setString("Initialisation de l'IHM...");
				break;
			case LOADING_READY:
				progressBar.setString("Chargement terminé. Démarrage Application...");
				break;
		}

		progressBar.setValue(status+1);

		try
		{
			Thread.sleep(250);
		}
		catch (InterruptedException e)
		{}

		if(status == LOADING_READY)
		{
			setVisible(false);
		}
	}
}