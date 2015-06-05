package InterfaceGraphique.DialogBoxes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NouvelleQuestionDialog extends JDialog implements ActionListener
{
	private JTextField intitule;
	private JButton ok;
	private JButton annuler;
	private boolean mofidEffectues;
	private JComboBox reponse;

	public NouvelleQuestionDialog(String title, String defIntitule, int defReponse, String reponse1, String reponse2, JFrame parent)
	{
		super(parent, title, true);
		mofidEffectues = false;
		createInterface(defIntitule, defReponse, reponse1, reponse2);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
	}

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

	public boolean afficher()
	{
		setVisible(true);
		return mofidEffectues;
	}

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
				JOptionPane.showMessageDialog(this, "Les champs ne doivent pas être vide.", "Champs non remplis", JOptionPane.WARNING_MESSAGE);
				return;
			}
			mofidEffectues = true;
			setVisible(false);
		}
	}

	public String getIntitule() {
		return intitule.getText();
	}

	public int getReponse() {
		return reponse.getSelectedIndex();
	}
}
