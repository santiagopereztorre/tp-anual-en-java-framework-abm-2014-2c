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
	protected Field[] fields;
	private List<Entidad<T>> entidades;
	protected Hashtable<Field, JTextField> referenciasATextField = new Hashtable<Field, JTextField>();

	public PantallaFiltrado(Field[] fields) {
		this.fields = fields;
		
		getContentPane().setLayout(new GridLayout(2, 0));

		for (Field field : fields) {
			String fieldName = field.getName();

			JLabel labelName = new JLabel(fieldName + " :");
			this.add(labelName);

			JTextField textField = new JTextField();
			this.add(textField);

			referenciasATextField.put(field, textField);
		}

		agregarTabla(fields);
		
		JButton botonCrear = new JButton("Buscar");
		botonCrear.addActionListener(this);
		this.add(botonCrear);

		setSize(400, 400);
	}

	private void agregarTabla(Field[] fields) {
		this.modeloTabla = new ModeloTabla<T>();

		JTable table = new JTable(modeloTabla);
		modeloTabla.setColumnNames(fields);

		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);

		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);

		// Add the scroll pane to this panel.
		add(scrollPane);
	}

	public Entidad<T> getEntidad() {
		return null;
	}

	public void cargarEntidades(List<Entidad<T>> entidades) {
		this.entidades = entidades;
		modeloTabla.setEntidades(entidades);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		List<Entidad<T>> entidadesFiltradas = new ArrayList<Entidad<T>>();
		entidadesFiltradas.addAll(entidades);
		for (Field field : fields) {
			JTextField textoField = referenciasATextField.get(field);
			String texto = textoField.getText();
			if (texto != null) {
				entidadesFiltradas = entidadesFiltradas.stream().filter((Entidad<T> entidad) -> entidad.getValor(field).startsWith(texto)).collect(Collectors.toList());
			}
		}
		modeloTabla.setEntidades(entidadesFiltradas);
		
	}

}
