package GestionBddDAO;

import java.io.*;
import java.util.Properties;

public class ConfigBDD
{
	private Properties conf;

	public ConfigBDD() {
		conf = new Properties();
	}

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

	public void loadDefaultConf()
	{
		conf.setProperty("nomBdd", "burgerquizz");
		conf.setProperty("port", "3306");
		conf.setProperty("ip", "localhost");
		conf.setProperty("login", "alain");
		conf.setProperty("password", "chabat");
	}

	public String getNomBdd() {
		return conf.getProperty("nomBdd");
	}

	public void setNomBdd(String nomBdd) {
		conf.setProperty("nomBdd", nomBdd);
	}

	public int getPort() {
		return Integer.parseInt(conf.getProperty("port"));
	}

	public void setPort(int port) {
		conf.setProperty("port", String.valueOf(port));
	}

	public String getIp() {
		return conf.getProperty("ip");
	}

	public void setIp(String ip) {
		conf.setProperty("ip", ip);
	}

	public String getLogin() {
		return conf.getProperty("login");
	}

	public void setLogin(String login) {
		conf.setProperty("login", login);
	}

	public String getPassword() {
		return conf.getProperty("password");
	}

	public void setPassword(String password) {
		conf.setProperty("password", password);
	}
}