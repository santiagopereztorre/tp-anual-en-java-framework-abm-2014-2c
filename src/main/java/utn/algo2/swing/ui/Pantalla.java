package utn.algo2.swing.ui;

import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import utn.algo2.core.Atributo;
import utn.algo2.core.Entidad;

@SuppressWarnings("serial")
public abstract class Pantalla<T> extends JDialog implements ActionListener{

	protected Entidad<T> entidad;
	protected ArrayList<Atributo<T>> atributos;
	protected Hashtable<Atributo<T>, JTextField> referenciasACamposDeTexto = new Hashtable<Atributo<T>, JTextField>();
	private JLabel labelError;
	protected Runnable callback;
	
	public Pantalla(ArrayList<Atributo<T>> atributos) {
		this.atributos = atributos;
		configurarPantalla();
		agregarCampoDeMensajeError();
		agregarCamposDeTexto(atributos);
		agregarTabla(atributos);
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

	protected void agregarCamposDeTexto(ArrayList<Atributo<T>> atributos) {
		for (Atributo<T> atributo : atributos) {
			JLabel label = new JLabel(atributo.getName() + " :");

			JTextField campoDeTexto = new JTextField();
			campoDeTexto.setColumns(10);
			campoDeTexto.setEditable(!atributo.esSoloLectura());

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

			referenciasACamposDeTexto.put(atributo, campoDeTexto);
		}
	}
	
	protected void agregarTabla(ArrayList<Atributo<T>> atributos) {};
	
	protected abstract void agregarBotones();

	/* Actions */
	
	public void actionPerformed(ActionEvent e) {
		entidad = new Entidad<T>();
		for (Entry<Atributo<T>, JTextField> entry : referenciasACamposDeTexto.entrySet()) {
			entidad.setValor(entry.getKey(), entry.getValue().getText());
		}
		callback.run();
	}

//	public Validacion2 obtengoValidacion(Field f){
//		ValidacionPersonalizada annotation = f.getAnnotation(ValidacionPersonalizada.class);
//		return annotation.metodo();
//	}
	
	/* Getters and Setters */

	public Entidad<T> getEntidad() {
		return entidad;
	}
}
