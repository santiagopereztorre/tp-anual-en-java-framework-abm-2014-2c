package utn.algo2.swing.ui;

import java.lang.reflect.Field;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PantallaInicio extends JDialog {

	public PantallaInicio(Field[] fields) {
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		for (Field field : fields){
			String fieldName = field.getName();
			
			JLabel labelCampo = new JLabel(fieldName + " :");
			this.add(labelCampo);
			
			JTextField textCampo = new JTextField();
			this.add(textCampo);
		}
		
		

		this.configurar();
	}

	private void configurar() {
		this.setSize(400, 400);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setVisible(true);
	}
}
