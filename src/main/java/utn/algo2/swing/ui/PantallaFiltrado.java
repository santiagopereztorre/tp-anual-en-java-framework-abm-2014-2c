package utn.algo2.swing.ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import utn.algo2.core.Entidad;

@SuppressWarnings("serial")
public class PantallaFiltrado<T> extends JDialog implements ActionListener {

	private ModeloTabla<T> modeloTabla = null;
	private Field[] fields;
	private List<Entidad<T>> entidades;
	private Hashtable<Field, JTextField> referenciasATextField = new Hashtable<Field, JTextField>();
	private Entidad<T> entidadAModificar = new Entidad<T>();
	private JTable table;
	private Runnable modificacion;
	private Runnable callback;

	private enum Actions {
		FILTRAR, MODIFICAR, CREAR,
	}

	public PantallaFiltrado(Field[] fields) {
		this.fields = fields;

		getContentPane().setLayout(new GridLayout(0, 2));

		for (Field field : fields) {
			String fieldName = field.getName();

			JLabel labelName = new JLabel(fieldName + " :");
			this.add(labelName);

			JTextField textField = new JTextField();
			this.add(textField);

			referenciasATextField.put(field, textField);
		}

		agregarTabla(fields);

		JButton botonFiltrar = new JButton("Filtrar");
		botonFiltrar.setActionCommand(Actions.FILTRAR.name());
		botonFiltrar.addActionListener(this);
		this.add(botonFiltrar);

		JButton botonModificar = new JButton("Modificar");
		botonModificar.setActionCommand(Actions.MODIFICAR.name());
		botonModificar.addActionListener(this);
		this.add(botonModificar);
		
		JButton botonCrear = new JButton("Crear");
		botonCrear.setActionCommand(Actions.CREAR.name());
		botonCrear.addActionListener(this);
		this.add(botonCrear);

		setSize(400, 400);
		this.setModalityType(ModalityType.MODELESS);
	}

	private void agregarTabla(Field[] fields) {
		this.modeloTabla = new ModeloTabla<T>();

		this.table = new JTable(modeloTabla);
		modeloTabla.setColumnNames(fields);

		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);

		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);

		// Add the scroll pane to this panel.
		add(scrollPane);
	}

	public Entidad<T> getEntidad() {
		return entidadAModificar;
	}

	public void cargarEntidades(List<Entidad<T>> entidades) {
		this.entidades = entidades;
		modeloTabla.setEntidades(entidades);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == Actions.FILTRAR.name()) {
			filtrar();
		}
		if (e.getActionCommand() == Actions.MODIFICAR.name()) {
			modificar();
		}
		if (e.getActionCommand() == Actions.CREAR.name()) {
			crear();
		}
	}

	private void crear() {
		callback.run();
	}

	private void modificar() {
		int row = table.getSelectedRow();
		if (row == -1) {
			return;
		}
		entidadAModificar = modeloTabla.getValueAt(row);
		modificacion.run();
	}

	private void filtrar() {
		List<Entidad<T>> entidadesFiltradas = new ArrayList<Entidad<T>>();
		entidadesFiltradas.addAll(entidades);
		for (Field field : fields) {
			JTextField textoField = referenciasATextField.get(field);
			String texto = textoField.getText();
			if (texto != null) {
				entidadesFiltradas = entidadesFiltradas
						.stream()
						.filter((Entidad<T> entidad) -> entidad.getValor(field)
								.startsWith(texto))
						.collect(Collectors.toList());
			}
		}
		modeloTabla.setEntidades(entidadesFiltradas);
	}

	public void onModificar(Runnable modificacion) {
		this.modificacion = modificacion;
	}

	public void onCrear(Runnable creacionFiltrado) {
		this.callback = creacionFiltrado;
	}

}
