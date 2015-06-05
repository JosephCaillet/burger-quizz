package InterfaceGraphique;

import javax.swing.*;

public class Bouton extends JButton
{
	public Bouton(String text, Icon icon)
	{
		super(text, icon);
		setFocusPainted(false);
		setIconTextGap(10);
	}
}
