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

import utn.algo2.annotations.Control;
import utn.algo2.annotations.Label;
import utn.algo2.annotations.NotNull;
import utn.algo2.annotations.ValidacionPersonalizada;
import utn.algo2.core.Entidad;
import utn.algo2.validaciones.Validacion2;

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
		this.setSize(700, 300);

	}

	private void agregarCamposDeTexto(Field[] fields) {
		for (Field field : fields) {
			JLabel label = new JLabel(field.getName() + " :");

			JTextField campoDeTexto = new JTextField();
			campoDeTexto.setColumns(10);

			JLabel labelnull = new JLabel("Nullable: " + !field.isAnnotationPresent(NotNull.class) + "   ");
			
			Panel panel = new Panel();
			panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			panel.add(label);
			panel.add(campoDeTexto);
			panel.add(labelnull);
			
			if(field.isAnnotationPresent(Label.class)){
				JLabel widget = new JLabel("Es label   ");
				panel.add(widget);
			}
			
			if(field.isAnnotationPresent(Control.class)){
				JLabel widget = new JLabel("Es Control    ");
				panel.add(widget);
			}
			
			JLabel tienevalidacion = new JLabel("Tiene validacion: " + field.isAnnotationPresent(ValidacionPersonalizada.class)+ "   ");
			panel.add(tienevalidacion);
			
			if(field.isAnnotationPresent(ValidacionPersonalizada.class)){
				JLabel validacion = new JLabel("Validacion: " + obtengoValidacion(field).toString());
				panel.add(validacion);
			}
			
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

	public Validacion2 obtengoValidacion(Field f){
		ValidacionPersonalizada annotation = f.getAnnotation(ValidacionPersonalizada.class);
		return annotation.metodo();
	}
	
	/* Getters and Setters */

	public Entidad<T> getEntidad() {
		return entidad;
	}
}
