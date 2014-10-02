package utn.algo2.annotations;

/**
 * Al validar el campo, se corre el metodo "metodo" de la clase del campo.
 * El metodo debe ser de tipo Static con la firma "boolean metodo(T unElemento)"
 *
 */
public @interface ValidacionPersonalizada {
	String metodo();
}
