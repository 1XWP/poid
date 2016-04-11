import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PopupContrast extends JFrame implements ChangeListener, ActionListener {

	double tmp = 0;
	private JButton saveButton;
	private static final long serialVersionUID = 1L;
	private Obraz obraz;

	public PopupContrast(Obraz obraz) {

		this.obraz = obraz;

		JFrame frame = new JFrame("Zmiana kontrastu");
		frame.setLayout(new BorderLayout());
		frame.pack();

		saveButton = new JButton("Zapisz zmiany");
		saveButton.setBounds(20, 20, 350, 40);
		saveButton.addActionListener(this);
		frame.add(saveButton);

		JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);
		slider.addChangeListener(this);

		frame.setLocationRelativeTo(null);
		frame.setSize(400, 200);
		frame.setResizable(false);
		frame.add(slider);
		frame.setVisible(true);
	}

	public void stateChanged(ChangeEvent e) {

		JSlider source = (JSlider) e.getSource();
		double tmp = source.getValue();
		obraz.contrast(tmp / 100);
		obraz.panel.repaint();

	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == saveButton) {

			obraz.result = Obraz.convertTo2DUsingGetRGB(obraz.image2);
		}
	}
}