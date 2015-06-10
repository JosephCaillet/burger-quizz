package InterfaceGraphique;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow
{
	private JProgressBar progressBar;

	public static final int LOADING_BDD_CONF = 0;
	public static final int LOADING_BDD_CONNECT = 1;
	public static final int LOADING_RSC = 2;
	public static final int LOADING_CREATE_STATUS_BAR = 3;
	public static final int LOADING_CREATE_PANEL_CATEGORIES = 4;
	public static final int LOADING_CREATE_PANEL_REPONSES = 5;
	public static final int LOADING_CREATE_PANEL_QUESTIONS = 6;
	public static final int LOADING_SETUP_UI = 7;
	public static final int LOADING_READY = 8;

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