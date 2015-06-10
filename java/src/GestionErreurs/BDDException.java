package GestionErreurs;

/**
 * Exeption lancée lors d'une erreur résultant d'une erreur SQL renvoyée par la base de données.
 */
public class BDDException extends Exception
{
	/**
	 * Constructeur de l'exeption.
	 * @param message Message décrivant l'erreur.
	 */
	public BDDException(String message)
	{
		super(message);
	}
}