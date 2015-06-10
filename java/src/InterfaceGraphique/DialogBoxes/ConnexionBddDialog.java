package InterfaceGraphique.DialogBoxes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

/**
 * Boite de dialogue modale permettant de renseigner une configuration de connexion à la base de données.
 * @author joseph
 */
public class ConnexionBddDialog extends JDialog implements ActionListener
{
	/**
	 * Champ pour le nom de la base de données.
	 */
	private JTextField nomBdd;
	/**
	 * Champ pour le numéro de port du serveur hebergeant la base de données.
	 */
	private JFormattedTextField port;
	/**
	 * Champ pour l'IP du serveur hebergeant la base de données.
	 */
	private JTextField ip;
	/**
	 * Champ pour le login utilisateur.
	 */
	private JTextField login;
	/**
	 * Champ pur le mot de passe utilisateur.
	 */
	private JPasswordField password;
	/**
	 * Bouton de validation.
	 */
	private JButton ok;
	/**
	 * Bouton d'annulation.
	 */
	private JButton annuler;
	/**
	 * Bouton pour mettre en place les paramétre de connexion par defaut.
	 */
	private JButton defParams;
	/**
	 * Bouton pour quitter l'aplication.
	 */
	private JButton quitter;
	/**
	 * Booléen determinant si les paramétre ont été modifié ou non.
	 */
	private boolean modifEffectuees;

	/**
	 * Constructeur préremplissant les champs de la boite de dialogue.
	 * @param nomBdd Nom base de données.
	 * @param port Port serveur base de données.
	 * @param ip Adresse ip serveur base de données.
	 * @param login Login utilisateur base de données.
	 * @param password Mot de passe utilisateur base de données.
	 * @param parent Parent
	 * @param showExitButton Afficher on non un bouton pour quitter l'aplication.
	 */
	public ConnexionBddDialog(String nomBdd, int port, String ip, String login, String password, JFrame parent, boolean showExitButton)
	{
		super(parent, "Paramètres de connexion à la base de données", true);
		modifEffectuees = false;
		createInterface(nomBdd, port, ip, login, password, showExitButton);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
	}

	/**
	 * Construit l'interface de la boite de dialogue.
	 * @param defNomBdd Nom base de données.
	 * @param defPort Port serveur base de données.
	 * @param defIP Adresse ip serveur base de données.
	 * @param defLogin Login utilisateur base de données.
	 * @param defPassword Mot de passe utilisateur base de données.
	 * @param showExitButton Afficher on non un bouton pour quitter l'aplication, remplaçant le bouton annuler.
	 */
	private void createInterface(String defNomBdd, int defPort, String defIP, String defLogin, String defPassword, boolean showExitButton)
	{
		nomBdd = new JTextField(defNomBdd, 30);
		port = new JFormattedTextField(NumberFormat.getIntegerInstance());
		port.setValue(defPort);
		ip = new JTextField(defIP, 30);
		login = new JTextField(defLogin, 30);
		password = new JPasswordField(defPassword, 30);

		nomBdd.setBackground(Color.WHITE);
		port.setBackground(Color.WHITE);
		ip.setBackground(Color.WHITE);
		login.setBackground(Color.WHITE);
		password.setBackground(Color.WHITE);

		ok = new JButton("OK");
		annuler = new JButton("Annuler");
		defParams = new JButton("Paramètres de connexion par défaut");
		quitter = new JButton("Quitter l'aplication");

		ok.addActionListener(this);
		annuler.addActionListener(this);
		defParams.addActionListener(this);
		quitter.addActionListener(this);

		JPanel fieldPanel = new JPanel();
		JPanel boutons = new JPanel();
		fieldPanel.setLayout(new GridLayout(5, 2));

		fieldPanel.add(new JLabel("Nom de la base de données:"));
		fieldPanel.add(nomBdd);
		fieldPanel.add(new JLabel("Numéro de port:"));
		fieldPanel.add(port);
		fieldPanel.add(new JLabel("Adresse IP du serveur:"));
		fieldPanel.add(ip);
		fieldPanel.add(new JLabel("Login utilisateur:"));
		fieldPanel.add(login);
		fieldPanel.add(new JLabel("Mot de passe utilisateur:"));
		fieldPanel.add(password);

		if(!showExitButton)
			{boutons.add(annuler);}
		boutons.add(defParams);
		boutons.add(ok);
		if(showExitButton)
			{boutons.add(quitter);}

		getContentPane().add(fieldPanel, BorderLayout.CENTER);
		getContentPane().add(boutons, BorderLayout.SOUTH);
	}

	/**
	 * Affiche la boite de dialogue.
	 * @return Un booléen indiquant si la configuration à été modifié ou non (clic sur le bouton OK ou annuler).
	 */
	public boolean afficher()
	{
		setVisible(true);
		return modifEffectuees;
	}

	/**
	 * Répond au clic sur les bouton: restaure la configuration par défault des champs, ou confirme/infirme que des changements on eu lieu, aprés vérification de la conformité des valeurs rentrées dans les champs.
	 * @param e Un ActionEvent au clic sur un bouton.
	 */
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == defParams)
		{
			nomBdd.setText("burgerquizz");
			port.setValue(3306);
			ip.setText("localhost");
			login.setText("alain");
			password.setText("chabat");
		}
		else if(e.getSource() == annuler)
		{
			modifEffectuees = false;
			setVisible(false);
		}
		else if(e.getSource() == ok)
		{
			if(getNomBdd().isEmpty() || getIp().isEmpty() || getLogin().isEmpty())
			{
				JOptionPane.showMessageDialog(this, "Les champs ne doivent pas être vides.", "Champs non remplis", JOptionPane.WARNING_MESSAGE);
				return;
			}
			modifEffectuees = true;
			setVisible(false);
		}
		else if(e.getSource() == quitter)
		{
			System.exit(0);
		}
	}

	/**
	 * Renvoie le nom de la bdd.
	 * @return nom de la bdd.
	 */
	public String getNomBdd() {
		return nomBdd.getText();
	}

	/**
	 * Renvoie le port du serveur bdd.
	 * @return le port du serveur bdd.
	 */
	public int getPort() {
		return Integer.parseInt(String.valueOf(port.getValue()));
	}

	/**
	 * Renvoie l'ip du serveur bdd.
	 * @return l'ip port du serveur bdd.
	 */
	public String getIp() {
		return ip.getText();
	}

	/**
	 * Renvoie le login de l'utilisateur du serveur bdd.
	 * @return le login de l'utilisateur du serveur bdd.
	 */
	public String getLogin() {
		return login.getText();
	}

	/**
	 * Renvoie le mot de passe de l'utilisateur du serveur bdd.
	 * @return le mot de passe l'utilisateur du serveur bdd.
	 */
	public String getPassword() {
		return String.valueOf(password.getPassword());
	}
}