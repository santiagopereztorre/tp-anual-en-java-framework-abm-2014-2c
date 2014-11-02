package utn.algo2.swing.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.Hashtable;

import javax.swing.JDialog;
import javax.swing.JTextField;

import utn.algo2.core.Entidad;

@SuppressWarnings("serial")
public class Pantalla<T> extends JDialog implements ActionListener{

	protected Field[] fields;
	protected Hashtable<Field, JTextField> referenciasATextField = new Hashtable<Field, JTextField>();
	protected Entidad<T> entidad = new Entidad<T>();
	
	public Pantalla(Field[] fields) {
		this.fields = fields;
	}
	
	protected void configurar() {
		this.setSize(400, 400);
		this.setModalityType(ModalityType.MODELESS);
	}
	
	public void actionPerformed(ActionEvent e) {
		synchronized (entidad) {
			for (Field field : fields) {
				JTextField textoField = referenciasATextField.get(field);
				entidad.setValor(field.getName(), textoField.getText());
			}
			entidad.notify();
		}
	}

	public Entidad<T> getEntidad() {
		synchronized (entidad) {
			try {
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
