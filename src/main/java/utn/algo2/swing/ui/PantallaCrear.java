package utn.algo2.swing.ui;

import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PantallaCrear<T> extends Pantalla<T> implements ActionListener {

	public PantallaCrear(Field[] fields) {
		super(fields);

		JButton botonCrear = new JButton("Crear");
		botonCrear.addActionListener(this);
		this.add(botonCrear);

		this.configurar();
	}

	public void borrarCampos() {
		for(Entry<Field, JTextField> entry : referenciasATextField.entrySet()) {
		    JTextField value = entry.getValue();
		    value.setText("");
		}
	}
}
