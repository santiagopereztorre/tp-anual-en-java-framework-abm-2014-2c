package utn.algo2.swing.ui;

import java.awt.ComponentOrientation;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import utn.algo2.annotations.NotNull;

@SuppressWarnings("serial")
public class PantallaCrear<T> extends Pantalla<T> implements ActionListener {

	public PantallaCrear(Field[] fields) {
		super(fields);
	}
	
	/* Visual */

	protected void agregarBotones() {
		JButton botonCrear = new JButton("Crear");
		botonCrear.addActionListener(this);
		this.add(botonCrear);
	}

	/* Callback */

	public void onCrear(Runnable callback) {
		this.callback = callback;
	}
	
	public void borrarCampos() {
		for(Entry<Field, JTextField> entry : referenciaACamposDeTexto.entrySet()) {
		    JTextField value = entry.getValue();
		    value.setText("");
		}
	}
}
