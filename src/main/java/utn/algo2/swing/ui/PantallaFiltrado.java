package utn.algo2.swing.ui;

import java.lang.reflect.Field;
import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PantallaFiltrado extends JDialog {
	
	protected Hashtable<Field, JTextField> referenciasATextField = new Hashtable<Field, JTextField>();
	
	public PantallaFiltrado(Field[] fields) {
		getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		for (Field field : fields) {
			String fieldName = field.getName();

			JLabel labelName = new JLabel(fieldName + " :");
			this.add(labelName);

			JTextField textField = new JTextField();
			this.add(textField);

			referenciasATextField.put(field, textField);
		}

		this.configurar();
	}
	
	protected void configurar() {
		this.setSize(400, 400);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setVisible(true);
	}

}
