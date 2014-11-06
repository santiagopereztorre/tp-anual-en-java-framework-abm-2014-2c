package utn.algo2.swing.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JTextField;

import utn.algo2.core.Entidad;

@SuppressWarnings("serial")
public class PantallaCrear<T> extends Pantalla<T> implements ActionListener {

	private Runnable callback;

	public PantallaCrear(Field[] fields) {
		super(fields);

		JButton botonCrear = new JButton("Crear");
		botonCrear.addActionListener(this);
		this.add(botonCrear);
	}

	public void borrarCampos() {
		for(Entry<Field, JTextField> entry : referenciasATextField.entrySet()) {
		    JTextField value = entry.getValue();
		    value.setText("");
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		for (Field field : fields) {
			JTextField textoField = referenciasATextField.get(field);
			entidad.putValor(field, textoField.getText());
		}
		callback.run();
	}
	
	public Entidad<T> getEntidad() {
		return entidad;
	}

	public void onCrear(Runnable creacion) {
		this.callback = creacion;
	}
}
