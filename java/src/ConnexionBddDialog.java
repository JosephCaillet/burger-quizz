import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import static javax.swing.BoxLayout.Y_AXIS;

public class ConnexionBddDialog extends JDialog implements ActionListener
{
	private JTextField nomBdd;
	private JFormattedTextField port;
	private JTextField ip;
	private JTextField login;
	private JPasswordField password;
	private JButton ok;
	private JButton annuler;
	private JButton defParams;
	private boolean modifEffectuees;

	public ConnexionBddDialog(String nomBdd, int port, String ip, String login, String password, Dialog owner)
	{
		super(owner, "Paramétres BDD", true);
		createInterface(nomBdd, port, ip, login, password);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void createInterface(String defNomBdd, int defPort, String defIP, String defLogin, String defPassword)
	{
		nomBdd = new JTextField(defNomBdd, 40);
		port = new JFormattedTextField(NumberFormat.getInstance());
		port.setValue(defPort);
		ip = new JTextField(defIP, 40);
		login = new JTextField(defLogin, 40);
		password = new JPasswordField(defPassword, 40);

		ok = new JButton("OK");
		annuler = new JButton("Annuler");
		defParams = new JButton("Paramétres de connexion par défault");

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

		boutons.add(annuler);
		boutons.add(defParams);
		boutons.add(ok);

		getContentPane().add(fieldPanel, BorderLayout.CENTER);
		getContentPane().add(boutons, BorderLayout.SOUTH);
	}

	public boolean afficher()
	{
		setVisible(true);
		return modifEffectuees;
	}

	public void actionPerformed(ActionEvent actionEvent)
	{
		modifEffectuees = false;
		setVisible(false);
	}
}