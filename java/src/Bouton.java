import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Bouton extends JButton
{
	public Bouton(String text, Icon icon)
	{
		super(text, icon);
		//setBackground(Color.WHITE);
		setFocusPainted(false);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
		//http://stackoverflow.com/questions/10274750/java-swing-setting-margins-on-textarea-with-line-border
		//setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		setIconTextGap(10);
	}
}
