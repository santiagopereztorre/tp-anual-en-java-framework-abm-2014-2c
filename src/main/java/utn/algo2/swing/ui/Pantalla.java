package utn.algo2.swing.ui;

import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.Panel;
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
public abstract class Pantalla<T> extends JDialog implements ActionListener{

	protected Entidad<T> entidad;
	protected Field[] fields;
	protected Hashtable<Field, JTextField> referenciaACamposDeTexto = new Hashtable<Field, JTextField>();
	protected Runnable callback;
	
	public Pantalla(Field[] fields) {
		this.fields = fields;
		configurarPantalla();
		agregarCamposDeTexto(fields);
		agregarBotones();
	}

	/* Visual */

	private void configurarPantalla() {
		this.getContentPane().setFont(new Font("Verdana", Font.PLAIN, 14));
		this.getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		this.setBounds(100, 100, 426, 300);
		this.setModalityType(ModalityType.MODELESS);
		this.setSize(300, 200);

	}

	private void agregarCamposDeTexto(Field[] fields) {
		for (Field field : fields) {
			JLabel label = new JLabel(field.getName() + " :");

			JTextField campoDeTexto = new JTextField();
			campoDeTexto.setColumns(10);

			Panel panel = new Panel();
			panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			panel.add(label);
			panel.add(campoDeTexto);

			getContentPane().add(panel);

			referenciaACamposDeTexto.put(field, campoDeTexto);
		}
	}
	
	protected abstract void agregarBotones();

	/* Actions */
	
	public void actionPerformed(ActionEvent e) {
		entidad = new Entidad<T>();
		for (Field field : fields) {
			JTextField campoDeTexto = referenciaACamposDeTexto.get(field);
			entidad.setValor(field, campoDeTexto.getText());
		}
		callback.run();
	}

	/* Getters and Setters */

	public Entidad<T> getEntidad() {
		return entidad;
	}
}
