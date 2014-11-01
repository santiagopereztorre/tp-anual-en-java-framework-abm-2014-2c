package utn.algo2.swing.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import utn.algo2.core.Entidad;

@SuppressWarnings("serial")
public class PantallaCrear extends JDialog implements ActionListener {

	private Field[] fields;
	private Hashtable<Field, JTextField> referenciasATextField = new Hashtable<Field, JTextField>();
	Entidad entidad = new Entidad();

	public PantallaCrear(Field[] fields) {
		this.fields = fields;

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

		JButton botonCrear = new JButton("Crear");
		botonCrear.addActionListener(this);
		this.add(botonCrear);

		this.configurar();
	}

	private void configurar() {
		this.setSize(400, 400);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		synchronized (entidad) {
			for (Field field : fields) {
				JTextField textoField = referenciasATextField.get(field);
				entidad.setValor(field.getName(), textoField.getText());
			}
			entidad.notify();
		}
		this.setVisible(false);
	}

	public Entidad getEntidad() {
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
