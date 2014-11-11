package utn.algo2.swing.ui;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import utn.algo2.core.Entidad;

@SuppressWarnings("serial")
public class ModeloTabla<T> extends AbstractTableModel {

	private String[] columnNames = {};
	private Object[][] data = {};
	private List<Entidad<T>> entidadesPorRow = new ArrayList<Entidad<T>>();
	private Class<?> clase;

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return data.length;
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

	/*
	 * JTable uses this method to determine the default renderer/ editor for
	 * each cell. If we didn't implement this method, then the last column would
	 * contain text ("true"/"false"), rather than a check box.
	 */
	public Class<? extends Object> getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	public void setColumnNames(Field[] fields) {
		String[] columnNames = new String[fields.length];
		int i = 0;
		for (Field field : fields) {
			columnNames[i] = field.getName();
			i++;
		}
		this.columnNames = columnNames;
		actualizar();
	}
	
	public void actualizarEntidades(List<Entidad<T>> entidades) {
		if (entidades.isEmpty()) {
			data = new Object[0][0];
			fireTableDataChanged();
			return;
		}
		entidadesPorRow = entidades;
		clase = entidades.get(0).getClase();
		Field[] fields = clase.getDeclaredFields();
		Integer cantidadAtributos = fields.length;
		Integer cantidadEntidades = entidades.size();
		Object[][] data = new Object[cantidadEntidades][cantidadAtributos];
		for (int i = 0; i < cantidadEntidades; i++) {
			Entidad<T> entidad = entidades.get(i);
			Object[] arrayDeAtributos = new Object[cantidadAtributos];
			for (int j = 0; j < cantidadAtributos; j++) {
				arrayDeAtributos[j] = entidad.getValor(fields[j]);
			}
			data[i] = arrayDeAtributos;
		}
		this.data = data;
		actualizar();
	}
	
	

	public Entidad<T> getEntidadAt(int row) {
//		Object[] objeto = data[row];
//		Entidad<T> entidad = new Entidad<T>();
//		int totalColumnas = getColumnCount();
//		for (int col = 0; col < totalColumnas; col ++ ) {
//			String nombreColumna = getColumnName(col);
//			Field field = null;
//			try {
//				field = clase.getDeclaredField(nombreColumna);
//			} catch (NoSuchFieldException e) {
//				e.printStackTrace();
//			} catch (SecurityException e) {
//				e.printStackTrace();
//			}
//			Object valor =  objeto[col];
//			entidad.setValor(field, valor);
//		}
//		return entidad;
		return entidadesPorRow.get(row);
	}

	public void actualizar() {
		fireTableDataChanged();
	}
}
