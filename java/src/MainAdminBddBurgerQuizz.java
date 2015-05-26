public class MainAdminBddBurgerQuizz
{
	public static void main(String args[])
	{
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
