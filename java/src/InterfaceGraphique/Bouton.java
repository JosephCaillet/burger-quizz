package InterfaceGraphique;

import javax.swing.*;

/**
 * Bouton personalisé
 */
public class Bouton extends JButton
{
	/**
	 * Constructeur modifiant certaines propriétés graphique du bouton.
	 * @param text Text affiché par le bouton.
	 * @param icon Icone affiché par le bouton.
	 */
	public Bouton(String text, Icon icon)
	{
		super(text, icon);
		setFocusPainted(false);
		setIconTextGap(10);
	}
}
