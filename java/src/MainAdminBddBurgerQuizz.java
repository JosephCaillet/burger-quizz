import InterfaceGraphique.InterfacePrincipale;

import javax.swing.*;

/**
 * Classe Main lancant l'application.
 * @author joseph
 */
public class MainAdminBddBurgerQuizz
{
	/**
	 * Méthode main lancant l'IHM principale.
	 * @param args Argument de la ligne de commande.
	 */
	public static void main(String[] args)
	{
		//https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		catch (UnsupportedLookAndFeelException e) {}

		InterfacePrincipale alain_chabat = new InterfacePrincipale();

		if(args.length >=1 && args[0].equals("-56k"))
		{
			alain_chabat.modem56k();
		}
		else if(args.length >=1 && args[0].equals("-lolinternet"))
		{
			alain_chabat.nyan();
		}
	}
}
