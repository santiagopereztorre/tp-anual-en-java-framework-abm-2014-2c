package utn.algo2.core;

import java.lang.reflect.Field;

public class FieldExtendido {
	
	private Field field;

	public FieldExtendido(Field field) {
		this.field = field;
	}
	
	public String getName() {
		return field.getName();
	}
	
	public Field getField() {
		return field;
	}
}
