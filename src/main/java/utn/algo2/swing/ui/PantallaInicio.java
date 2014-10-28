package utn.algo2.swing.ui;

import javax.swing.JDialog;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class PantallaInicio extends JDialog {
	
	public PantallaInicio() {
		JLabel jlbHelloWorld = new JLabel("Hello World");
		add(jlbHelloWorld);
		this.setSize(100, 100);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setVisible(true);
	}
}
