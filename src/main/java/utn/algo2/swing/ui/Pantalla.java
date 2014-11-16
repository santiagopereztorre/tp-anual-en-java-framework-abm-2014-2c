package utn.algo2.swing.ui;

import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import utn.algo2.annotations.ValidacionPersonalizada;
import utn.algo2.core.Atributo;
import utn.algo2.core.Entidad;
import utn.algo2.validaciones.Validacion2;

@SuppressWarnings("serial")
public abstract class Pantalla<T> extends JDialog implements ActionListener{

	protected Entidad<T> entidad;
	protected ArrayList<Atributo<T>> fields;
	protected Hashtable<Atributo<T>, JTextField> referenciasACamposDeTexto = new Hashtable<Atributo<T>, JTextField>();
	private JLabel labelError;
	protected Runnable callback;
	
	public Pantalla(ArrayList<Atributo<T>> fields2) {
		this.fields = fields2;
		configurarPantalla();
		agregarCampoDeMensajeError();
		agregarCamposDeTexto(fields2);
		agregarTabla(fields2);
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

	private void agregarCampoDeMensajeError() {
		Panel panel = new Panel();
		labelError = new JLabel();
		panel.add(labelError);
		getContentPane().add(panel);
	}
	
	public void mostrarError(String mensaje) {
		labelError.setText(mensaje);
	}

	private void agregarCamposDeTexto(ArrayList<Atributo<T>> fields2) {
		for (Atributo<T> field : fields2) {
			JLabel label = new JLabel(field.getName() + " :");

			JTextField campoDeTexto = new JTextField();
			campoDeTexto.setColumns(10);
			campoDeTexto.setEditable(!field.esSoloLectura());

//			JLabel labelnull = new JLabel("Nullable: " + !field.isAnnotationPresent(NotNull.class) + "   ");
			
			Panel panel = new Panel();
			panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			panel.add(label);
			panel.add(campoDeTexto);
//			panel.add(labelnull);
			
//			if(field.isAnnotationPresent(Label.class)){
//				JLabel widget = new JLabel("Es label   ");
//				panel.add(widget);
//			}
//			
//			if(field.isAnnotationPresent(Control.class)){
//				JLabel widget = new JLabel("Es Control    ");
//				panel.add(widget);
//			}
//			
//			JLabel tienevalidacion = new JLabel("Tiene validacion: " + field.isAnnotationPresent(ValidacionPersonalizada.class)+ "   ");
//			panel.add(tienevalidacion);
//			
//			if(field.isAnnotationPresent(ValidacionPersonalizada.class)){
//				JLabel validacion = new JLabel("Validacion: " + obtengoValidacion(field).toString());
//				panel.add(validacion);
//			}
			
			getContentPane().add(panel);

			referenciasACamposDeTexto.put(field, campoDeTexto);
		}
	}
	
	protected void agregarTabla(ArrayList<Atributo<T>> fields2) {};
	
	protected abstract void agregarBotones();

	/* Actions */
	
	public void actionPerformed(ActionEvent e) {
		entidad = new Entidad<T>();
		for (Atributo<T> field : fields) {
			JTextField campoDeTexto = referenciasACamposDeTexto.get(field);
			entidad.setValor(field.getField(), campoDeTexto.getText());
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
