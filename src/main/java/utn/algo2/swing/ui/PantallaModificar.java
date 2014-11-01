package utn.algo2.swing.ui;

import java.awt.event.ActionListener;
import java.lang.reflect.Field;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import utn.algo2.core.Entidad;

@SuppressWarnings("serial")
public class PantallaModificar<T> extends Pantalla<T> implements ActionListener {

	public PantallaModificar(Field[] fields,
			Entidad<T> entidadAModificar) {
		super(fields);

		getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		for (Field field : fields) {
			String fieldName = field.getName();

			JLabel labelName = new JLabel(fieldName + " :");
			this.add(labelName);

			String valor = entidadAModificar.getValor(fieldName);

			JTextField textField = new JTextField(valor);
			this.add(textField);

			referenciasATextField.put(field, textField);
		}

		JButton botonCrear = new JButton("Modificar");
		botonCrear.addActionListener(this);
		this.add(botonCrear);

		this.configurar();
	}
}
