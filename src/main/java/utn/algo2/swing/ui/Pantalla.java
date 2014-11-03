package utn.algo2.swing.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import utn.algo2.core.Entidad;

@SuppressWarnings("serial")
public class Pantalla<T> extends JDialog implements ActionListener{

	protected Field[] fields;
	protected Hashtable<Field, JTextField> referenciasATextField = new Hashtable<Field, JTextField>();
	protected Entidad<T> entidad = new Entidad<T>();
	
	public Pantalla(Field[] fields) {
		this.fields = fields;
		
		getContentPane().setLayout(
				new GridLayout(0,2));
		
		this.configurar();

		for (Field field : fields) {
			String fieldName = field.getName();

			JLabel labelName = new JLabel(fieldName + " :");
			this.add(labelName);
			
			JTextField textField = new JTextField();
			this.add(textField);

			referenciasATextField.put(field, textField);
		}
	}
	
	protected void configurar() {
		this.setSize(400, 400);
		this.setModalityType(ModalityType.MODELESS);
	}
	
	public void actionPerformed(ActionEvent e) {
		synchronized (entidad) {
//			for (Field field : fields) {
//				JTextField textoField = referenciasATextField.get(field);
//				entidad.setValor(field.getName(), textoField.getText());
//			}
			for (Field field : fields) {
				JTextField textoField = referenciasATextField.get(field);
				entidad.setValor(field, textoField.getText());
			}
			entidad.notify();
		}
	}

	public Entidad<T> getEntidad() {
		synchronized (entidad) {
			try {
//				if (entidad.isEmpty())
//					entidad.wait();
				if (entidad.isEmpty())
					entidad.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return entidad;
	}
}
