package utn.algo2.interfaces;

import java.util.List;

public interface Persistidor<T> {
	
	/**
	 * Persiste unElemento
	 * @param unElemento
	 */
	void darDeAlta(T unElemento);
	
	/**
	 * Despersiste unElemento
	 * @param unElemento
	 */
	void darDeBaja(T unElemento);
	
	/**
	 * Persiste los cambios que sufrio unElemento
	 * @param unElemento
	 */
	void modificar(T unElemento);
	
	/**
	 * Devuelve una List con todos los elementos persistidos
	 * @return
	 */
	List<T> obtenerTodos();

}
