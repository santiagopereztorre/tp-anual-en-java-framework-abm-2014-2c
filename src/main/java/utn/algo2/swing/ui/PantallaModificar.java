package utn.algo2.swing.ui;

import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JTextField;

import utn.algo2.core.Entidad;

@SuppressWarnings("serial")
public class PantallaModificar<T> extends Pantalla<T> implements ActionListener {

	public PantallaModificar(Field[] fields) {
		super(fields);

		JButton botonCrear = new JButton("Modificar");
		botonCrear.addActionListener(this);
		this.add(botonCrear);

		this.configurar();
	}
	
	public void cargarCampos(Entidad<T> entidadAModificar) {
		for(Entry<Field, JTextField> entry : referenciasATextField.entrySet()) {
		    Field field = entry.getKey();
		    JTextField campoTexto = entry.getValue();
		    String valor = entidadAModificar.getValor(field);
		    campoTexto.setText(valor);
		}
	}
}
