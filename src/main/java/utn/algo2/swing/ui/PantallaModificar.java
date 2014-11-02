package utn.algo2.swing.ui;

import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import utn.algo2.core.Entidad;

@SuppressWarnings("serial")
public class PantallaModificar<T> extends Pantalla<T> implements ActionListener {

	public PantallaModificar(Field[] fields) {
		super(fields);

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

		JButton botonCrear = new JButton("Modificar");
		botonCrear.addActionListener(this);
		this.add(botonCrear);

		this.configurar();
	}
	
	public PantallaModificar(Field[] fields, Entidad<T> entidadAModificar) {
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
	
	public void cargarCampos(Entidad<T> entidadAModificar) {
		for(Entry<Field, JTextField> entry : referenciasATextField.entrySet()) {
		    Field key = entry.getKey();
		    String fieldName = key.getName();
		    
		    String valor = entidadAModificar.getValor(fieldName);
			
			JTextField value = entry.getValue();
		    value.setText(valor);
		}
	}
}
