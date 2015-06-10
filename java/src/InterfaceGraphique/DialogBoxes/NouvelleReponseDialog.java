package InterfaceGraphique.DialogBoxes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Boite de dialogue modale pour renseigner un nouveau jeu de réponses.
 * @author joseph
 */
public class NouvelleReponseDialog extends JDialog implements ActionListener
{
	/**
	 * Réponses une.
	 */
	private JTextField rep1;
	/**
	 * Réponse deux.
	 */
	private JTextField rep2;
	/**
	 * Validation.
	 */
	private JButton ok;
	/**
	 * Annulation.
	 */
	private JButton annuler;
	/**
	 * Booléen determinant si les paramétre ont été modifié ou non.
	 */
	private boolean mofidEffectues;
	/**
	 * Liste des catégorie
	 */
	private JComboBox comboCat;

	/**
	 * Constructeur préremplissant les champs de la boite de dialogue.
	 * @param title Titre
	 * @param defRep1 Réponse une par défaut.
	 * @param defRep2 Réponse deux par défaut.
	 * @param defCategorie Catégorie par défaut
	 * @param tabCategorie Tableau des catégories existantes.
	 * @param parent Parent.
	 */
	public NouvelleReponseDialog(String title, String defRep1, String defRep2, String defCategorie, String[] tabCategorie,JFrame parent)
	{
		super(parent, title, true);
		mofidEffectues = false;
		createInterface(defRep1, defRep2, defCategorie, tabCategorie);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
	}

	/**
	 * Construit l'interface de la boite de dialogue.
	 * @param defRep1 Réponse une par défaut.
	 * @param defRep2 Réponse deux par défaut.
	 * @param defCategorie Catégorie par défaut
	 * @param tabCategorie Tableau des catégories existantes.
	 */
	private void createInterface(String defRep1, String defRep2, String defCategorie, String[] tabCategorie)
	{
		JPanel champs = new JPanel();
		JPanel boutons = new JPanel();

		rep1 = new JTextField(defRep1,15);
		rep2 = new JTextField(defRep2,15);

		rep1.setBackground(Color.WHITE);
		rep2.setBackground(Color.WHITE);

		ok = new JButton("OK");
		annuler = new JButton("Annuler");
		ok.addActionListener(this);
		annuler.addActionListener(this);

		GridLayout gridLayout = new GridLayout(2, 2);
		champs.setLayout(gridLayout);
		champs.add(new JLabel("Réponse 1:"));
		champs.add(rep1);
		champs.add(new JLabel("Réponse 2:"));
		champs.add(rep2);

		if(defCategorie != null)
		{
			comboCat = new JComboBox();
			comboCat.setOpaque(true);
			for(int i=0; i< tabCategorie.length; i++)
			{
				comboCat.addItem(tabCategorie[i]);
				if(tabCategorie[i].equals(defCategorie))
				{
					comboCat.setSelectedIndex(i);
				}
			}
			gridLayout.setRows(3);
			champs.add(new JLabel("Catégorie:"));
			champs.add(comboCat);
		}

		boutons.add(annuler);
		boutons.add(ok);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(champs, BorderLayout.CENTER);
		getContentPane().add(boutons, BorderLayout.SOUTH);
	}

	/**
	 * Affiche la boite de dialogue.
	 * @return Un booléen indiquant si la configuration à été modifié ou non (clic sur le bouton OK ou annuler).
	 */
	public boolean afficher()
	{
		setVisible(true);
		return mofidEffectues;
	}

	/**
	 * Répond au clic sur les bouton: confirme/infirme que des changements on eu lieu, aprés vérification de la conformité des valeurs rentrées dans les champs.
	 * @param e Un ActionEvent au clic sur un bouton.
	 */
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == annuler)
		{
			mofidEffectues = false;
			setVisible(false);
		}
		else if(e.getSource() == ok) {
			if(rep1.getText().isEmpty() || rep2.getText().isEmpty() || rep1.getText().equals(rep2.getText()))
			{
				JOptionPane.showMessageDialog(this, "Les champs ne doivent être ni vides, ni égaux.", "Champs non remplis", JOptionPane.WARNING_MESSAGE);
				return;
			}
			mofidEffectues = true;
			setVisible(false);
		}
	}

	/**
	 * Renvoie la réponse une.
	 * @return la réponse une.
	 */
	public String getRep1() {
		return rep1.getText();
	}

	/**
	 * Renvoie la réponse deux.
	 * @return la réponse deux.
	 */
	public String getRep2() {
		return rep2.getText();
	}

	/**
	 * Renvoie la catégorie.
	 * @return la catégorie.
	 */
	public String getCat() {
		return comboCat.getSelectedItem().toString();
	}
}