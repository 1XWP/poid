import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
 
public class PopupLighten extends JFrame implements ChangeListener, ActionListener {
 
int tmp = 0;

private JButton saveButton;
	
private static final long serialVersionUID = 1L;

private Obraz obraz;

public PopupLighten(Obraz obraz) {
	
	this.obraz = obraz;

    JFrame frame = new JFrame("Zmiana jasnoœci");
    frame.setLayout(new BorderLayout());
    frame.pack();
    
	saveButton = new JButton("Zapisz zmiany");
	saveButton.setBounds(20, 20, 350, 40);	
	saveButton.addActionListener(this);
	frame.add(saveButton);
         
    JSlider slider = new JSlider(JSlider.HORIZONTAL, -60, 60, 0);      
    slider.addChangeListener(this);

    frame.setLocationRelativeTo(null);
    frame.setSize(400, 200);  
    frame.setResizable(false);
    frame.add(slider);
    frame.setVisible(true);
 
  }
 
	public void stateChanged(ChangeEvent e){
		   	    
	        JSlider source = (JSlider)e.getSource();          
	        int tmp = source.getValue();
	        System.out.println(tmp);
	        
	        obraz.lighten(tmp);
	        obraz.panel.repaint();
	}
	
    public void actionPerformed(ActionEvent e) {		
        Object source = e.getSource();
        if (source == saveButton) {     
     	
        	obraz.result = obraz.convertTo2DUsingGetRGB(obraz.image2);
        }

	}

 
 
}