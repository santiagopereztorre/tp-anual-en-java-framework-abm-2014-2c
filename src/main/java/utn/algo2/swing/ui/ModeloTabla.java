package utn.algo2.swing.ui;

import java.lang.reflect.Field;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import utn.algo2.core.Entidad;

@SuppressWarnings("serial")
public class ModeloTabla<T> extends AbstractTableModel {

	private String[] columnNames = {};
	private Object[][] data = {};
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

	/*
	 * Don't need to implement this method unless your table's editable.
	 */
	public boolean isCellEditable(int row, int col) {
		// Note that the data/cell address is constant,
		// no matter where the cell appears onscreen.
		if (col < 2) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * Don't need to implement this method unless your table's data can change.
	 */
	public void setValueAt(Object value, int row, int col) {
		data[row][col] = value;
		fireTableCellUpdated(row, col);
	}

	public void setColumnNames(Field[] fields) {
		String[] columnNames = new String[fields.length];
		int i = 0;
		for (Field field : fields) {
			columnNames[i] = field.getName();
			i++;
		}
		this.columnNames = columnNames;
		fireTableStructureChanged();
	}
	
	public void setEntidades(List<Entidad<T>> entidades) {
		if (entidades.isEmpty()) {
			data = new Object[0][0];
			fireTableDataChanged();
			return;
		}
		clase = entidades.get(0).getClase();
		Field[] fields = clase.getFields();
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
		fireTableDataChanged();
	}
	
	

	public Entidad<T> getValueAt(int row) {
		Object[] objeto = data[row];
		Entidad<T> entidad = new Entidad<T>();
		int totalColumnas = getColumnCount();
		for (int col = 0; col < totalColumnas; col ++ ) {
			String nombreColumna = getColumnName(col);
			Field field = null;
			try {
				field = clase.getField(nombreColumna);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			String valor = (String) objeto[col];
			entidad.putValor(field, valor);
		}
		return entidad;
	}

	public void actualizar() {
		fireTableDataChanged();
	}
}
