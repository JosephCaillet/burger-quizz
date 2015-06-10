package GestionBddDAO;

import java.io.*;
import java.util.Properties;

/**
 * Classe utilitaires stockant les paramétres de connexion à la bdd
 * @author joseph
 */
public class ConfigBDD
{
	/**
	 * Objet permettant le stockage des infos et leur lecture/écritures.
	 */
	private Properties conf;

	/**
	 * Constructeur par défault
	 */
	public ConfigBDD() {
		conf = new Properties();
	}

	/**
	 * Initie la cofiguration depuis un fichier conf.xml lu dans le repertoire courant.
	 * Si le fichier n'existe pas, une configuration par defaut est chargée, et sauvegardée sur le disque.
	 * @return true si le chargement à réussi, false sinon.
	 */
	public boolean loadConf()
	{
		try
		{
			conf.loadFromXML(new FileInputStream("conf.xml"));
			return true;
		}
		catch (IOException e)
		{
			loadDefaultConf();
			saveConf();
			System.out.println(e.getMessage());
			return false;
		}
	}

	/**
	 * Sauve la configuration actuelle dans un fichier conf.xml.
	 * @return true si la sauvegarde est réussie, false sinon.
	 */
	public boolean saveConf()
	{
		try
		{
			conf.storeToXML(new FileOutputStream("conf.xml"), "Configuration de l'aplication de gestion du jeu BurgerQuizz");
			return true;
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
	}

	/**
	 * Charge une configuration par defaut.
	 */
	public void loadDefaultConf()
	{
		conf.setProperty("nomBdd", "burgerquizz");
		conf.setProperty("port", "3306");
		conf.setProperty("ip", "localhost");
		conf.setProperty("login", "alain");
		conf.setProperty("password", "chabat");
	}

	/**
	 * @return Le nom de la base de données.
	 */
	public String getNomBdd() {
		return conf.getProperty("nomBdd");
	}

	/**
	 * Change le nom de la base de données.
	 * @param nomBdd Nom pour la base de données.
	 */
	public void setNomBdd(String nomBdd) {
		conf.setProperty("nomBdd", nomBdd);
	}

	/**
	 * @return Le port de connexion à la base de données.
	 */
	public int getPort() {
		return Integer.parseInt(conf.getProperty("port"));
	}

	/**
	 * Change le port de connexion à la base de données.
	 * @param port Port de connexion à la base de données.
	 */
	public void setPort(long port) {
		conf.setProperty("port", String.valueOf(port));
	}

	/**
	 * @return L'adresse IP du serveur de la base de données.
	 */
	public String getIp() {
		return conf.getProperty("ip");
	}

	/**
	 * Change l'adresse IP de connexion à la base de données.
	 * @param ip Adresse IP du serveur hébergant la base de données.
	 */
	public void setIp(String ip) {
		conf.setProperty("ip", ip);
	}

	/**
	 * @return Login de l'utilisateur pour la base de données.
	 */
	public String getLogin() {
		return conf.getProperty("login");
	}

	/**
	 * Change le login de l'utilisateur pour la base de données.
	 * @param login Login de l'utilisateur.
	 */
	public void setLogin(String login) {
		conf.setProperty("login", login);
	}

	/**
	 * @return Mot de passe de l'utilisateur pour la base de données.
	 */
	public String getPassword() {
		return conf.getProperty("password");
	}

	/**
	 * Change le mot de passe de l'utilisateur pour la base de données.
	 * @param password Nouveau mot de passe
	 */
	public void setPassword(String password) {
		conf.setProperty("password", password);
	}
}