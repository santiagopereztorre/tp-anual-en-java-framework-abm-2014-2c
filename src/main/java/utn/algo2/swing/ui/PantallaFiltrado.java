package utn.algo2.swing.ui;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import utn.algo2.core.Entidad;

@SuppressWarnings("serial")
public class PantallaFiltrado<T> extends JDialog implements ActionListener {

	private Entidad<T> entidadAModificar = new Entidad<T>();
	private Field[] fields;
	private Hashtable<Field, JTextField> referenciasACamposDeTexto = new Hashtable<Field, JTextField>();
	private JTable table;
	private List<Entidad<T>> entidades;
	private ModeloTabla<T> modeloTabla;
	private Runnable callbackModificacion;
	private Runnable callbackCreacion;

	private enum Actions {
		FILTRAR, MODIFICAR, CREAR,
	}

	public PantallaFiltrado(Field[] fields) {
		this.fields = fields;
		configurarPantalla();
		agregarCamposDeTexto(fields);
		agregarTabla(fields);
		agregarBotones();
	}

	/* Visual */

	private void configurarPantalla() {
		this.getContentPane().setFont(new Font("Verdana", Font.PLAIN, 14));
		this.getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		this.setBounds(100, 100, 426, 300);
		this.setModalityType(ModalityType.MODELESS);
		this.setSize(300, 300);
	}

	private void agregarCamposDeTexto(Field[] fields) {
		for (Field field : fields) {
			JLabel label = new JLabel(field.getName() + " :");

			JTextField campoDeTexto = new JTextField();
			campoDeTexto.setColumns(10);

			Panel panel = new Panel();
			panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			panel.add(label);
			panel.add(campoDeTexto);

			getContentPane().add(panel);

			referenciasACamposDeTexto.put(field, campoDeTexto);
		}
	}

	private void agregarTabla(Field[] fields) {
		modeloTabla = new ModeloTabla<T>();
		modeloTabla.setColumnNames(fields);

		table = new JTable(modeloTabla);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);

		JScrollPane scrollPane = new JScrollPane(table);
		this.add(scrollPane);
	}

	private void agregarBotones() {
		Panel panelBotones = new Panel();
		panelBotones
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		this.getContentPane().add(panelBotones);

		JButton botonFiltrar = new JButton("Filtrar");
		botonFiltrar.setActionCommand(Actions.FILTRAR.name());
		botonFiltrar.addActionListener(this);
		panelBotones.add(botonFiltrar);

		JButton botonModificar = new JButton("Modificar");
		botonModificar.setActionCommand(Actions.MODIFICAR.name());
		botonModificar.addActionListener(this);
		panelBotones.add(botonModificar);

		JButton botonCrear = new JButton("Crear");
		botonCrear.setActionCommand(Actions.CREAR.name());
		botonCrear.addActionListener(this);
		panelBotones.add(botonCrear);
	}

	/* Actions */

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == Actions.CREAR.name())
			crear();
		if (e.getActionCommand() == Actions.MODIFICAR.name())
			modificar();
		if (e.getActionCommand() == Actions.FILTRAR.name())
			filtrar();
	}

	private void crear() {
		callbackCreacion.run();
	}

	private void modificar() {
		int row = table.getSelectedRow();
		if (row == -1)
			return;
		entidadAModificar = modeloTabla.getEntidadAt(row);
		callbackModificacion.run();
	}

	private void filtrar() {
		List<Entidad<T>> entidadesFiltradas = new ArrayList<Entidad<T>>(
				entidades);
		for (Field field : fields) {
			String texto = referenciasACamposDeTexto.get(field).getText();
			if (texto != null) {
				entidadesFiltradas = entidadesFiltradas
						.stream()
						.filter((Entidad<T> entidad) -> entidad.getValor(field).toString()
								.startsWith(texto))
						.collect(Collectors.toList());
			}
		}
		modeloTabla.actualizarEntidades(entidadesFiltradas);
	}

	/* Callbacks */

	public void onModificar(Runnable modificacion) {
		this.callbackModificacion = modificacion;
	}

	public void onCrear(Runnable creacionFiltrado) {
		this.callbackCreacion = creacionFiltrado;
	}

	/* Getters and Setters */

	public void cargarEntidades(List<Entidad<T>> entidades) {
		this.entidades = entidades;
		modeloTabla.actualizarEntidades(entidades);
	}

	public Entidad<T> getEntidad() {
		return entidadAModificar;
	}

}
