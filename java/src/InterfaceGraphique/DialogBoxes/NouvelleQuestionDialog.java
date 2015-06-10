package InterfaceGraphique.DialogBoxes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Boite de dialogue modale pour renseigner une nouvelle question.
 * @author joseph
 */
public class NouvelleQuestionDialog extends JDialog implements ActionListener
{
	/**
	 * Intitulé de la question.
	 */
	private JTextField intitule;
	/**
	 * Validation
	 */
	private JButton ok;
	/**
	 * Annulation
	 */
	private JButton annuler;
	/**
	 * Modification des valeurs déja présentes ou non
	 */
	private boolean mofidEffectues;
	/**
	 * Liste des réponses possible.
	 */
	private JComboBox reponse;

	/**
	 * Constructeur préremplissant les champs de la boite de dialogue.
	 * @param title Titre de la boite de dialogue.
	 * @param defIntitule Intitulé question.
	 * @param defReponse Numéro bonne réponse.
	 * @param reponse1 Réponse une.
	 * @param reponse2 Réponse deux.
	 * @param parent Parent.
	 */
	public NouvelleQuestionDialog(String title, String defIntitule, int defReponse, String reponse1, String reponse2, JFrame parent)
	{
		super(parent, title, true);
		mofidEffectues = false;
		createInterface(defIntitule, defReponse, reponse1, reponse2);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
	}

	/**
	 * Construit l'interface de la boite de dialogue.
	 * @param defIntitule Intitulé question.
	 * @param defReponse Numéro bonne réponse.
	 * @param reponse1 Réponse une.
	 * @param reponse2 Réponse deux.
	 */
	private void createInterface(String defIntitule, int defReponse, String reponse1, String reponse2)
	{
		JPanel champs = new JPanel();
		JPanel boutons = new JPanel();

		intitule = new JTextField(defIntitule, 20);
		intitule.setBackground(Color.WHITE);

		ok = new JButton("OK");
		annuler = new JButton("Annuler");
		ok.addActionListener(this);
		annuler.addActionListener(this);

		reponse = new JComboBox();
		reponse.addItem("Les deux");
		reponse.addItem(reponse1);
		reponse.addItem(reponse2);
		reponse.setSelectedIndex(defReponse);

		GridLayout gridLayout = new GridLayout(2, 2);
		champs.setLayout(gridLayout);
		champs.add(new JLabel("Intitulé:"));
		champs.add(intitule);
		champs.add(new JLabel("Bonne réponse:"));
		champs.add(reponse);

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
			if(intitule.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(this, "L'intitulé ne peut être vide.", "Champs non remplis", JOptionPane.WARNING_MESSAGE);
				return;
			}
			mofidEffectues = true;
			setVisible(false);
		}
	}

	/**
	 * Renvoie l'intitulé de la question.
	 * @return l'intitulé de la question.
	 */
	public String getIntitule() {
		return intitule.getText();
	}

	/**
	 * Renvoie le numéro de la bonne réponse à la question.
	 * @return le numéro de la bonne réponse à la question.
	 */
	public int getReponse() {
		return reponse.getSelectedIndex();
	}
}
