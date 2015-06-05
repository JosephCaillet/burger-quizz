package InterfaceGraphique.DialogBoxes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

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
	private JButton quitter;
	private boolean modifEffectuees;

	public ConnexionBddDialog(String nomBdd, int port, String ip, String login, String password, JFrame parent, boolean showExitButton)
	{
		super(parent, "Paramètres de connexion à la base de données", true);
		modifEffectuees = false;
		createInterface(nomBdd, port, ip, login, password, showExitButton);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void createInterface(String defNomBdd, int defPort, String defIP, String defLogin, String defPassword, boolean showExitButton)
	{
		nomBdd = new JTextField(defNomBdd, 30);
		port = new JFormattedTextField(NumberFormat.getInstance());
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

		if(!showExitButton)boutons.add(annuler);
		boutons.add(defParams);
		boutons.add(ok);
		if(showExitButton)boutons.add(quitter);

		getContentPane().add(fieldPanel, BorderLayout.CENTER);
		getContentPane().add(boutons, BorderLayout.SOUTH);
	}

	public boolean afficher()
	{
		setVisible(true);
		return modifEffectuees;
	}

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
				JOptionPane.showMessageDialog(this, "Les champs ne doivent pas être vide.", "Champs non remplis", JOptionPane.WARNING_MESSAGE);
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

	public String getNomBdd() {
		return nomBdd.getText();
	}

	public int getPort() {
		return (int) port.getValue();
	}

	public String getIp() {
		return ip.getText();
	}

	public String getLogin() {
		return login.getText();
	}

	public String getPassword() {
		return String.valueOf(password.getPassword());
	}
}