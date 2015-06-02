import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NouvelleReponseDialog extends JDialog implements ActionListener
{
	private JTextField rep1;
	private JTextField rep2;
	private JButton ok;
	private JButton annuler;
	private boolean mofidEffectues;
	private JComboBox comboCat;

	public NouvelleReponseDialog(String title, String defRep1, String defRep2, String defCategorie, String[] tabCategorie,JFrame parent)
	{
		super(parent,title,true);
		createInterface(defRep1, defRep2, defCategorie, tabCategorie);
		pack();
		setLocationRelativeTo(null);
	}

	private void createInterface(String defRep1, String defRep2, String defCategorie, String[] tabCatrgorie)
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

		if(defCategorie == null)
		{
			comboCat = new JComboBox();
			comboCat.setOpaque(true);
			for(int i=0; i<tabCatrgorie.length; i++)
			{
				comboCat.addItem(tabCatrgorie[i]);
				if(tabCatrgorie[i].equals(defCategorie))
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
			if(rep1.getText().isEmpty() || rep2.getText().isEmpty())
			{
				return;
			}
			mofidEffectues = true;
			setVisible(false);
		}
	}

	public String getRep2() {
		return rep2.getText();
	}

	public String getRep1() {
		return rep1.getText();
	}

	public String getCat() {
		return comboCat.getSelectedItem().toString();
	}
}