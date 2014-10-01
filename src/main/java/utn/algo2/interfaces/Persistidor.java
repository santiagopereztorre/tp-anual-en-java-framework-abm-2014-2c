package utn.algo2.interfaces;

import java.util.List;

public interface Persistidor<T> {
	
	/**
	 * Persiste unElemento
	 * @param unElemento Elemento a persistir
	 */
	public void darDeAlta(T unElemento);
	
	/**
	 * Despersiste unElemento
	 * @param unElemento Elemento a dar de baja
	 */
	public void darDeBaja(T unElemento);
	
	/**
	 * Persiste los cambios que sufrio unElemento
	 * @param unElemento Actualiza (si corresponde) el elemento con los nuevos cambios
	 */
	public void modificar(T unElemento);
	
	/**
	 * Devuelve una List con todos los elementos persistidos
	 * @return List<T> de los elementos persisitidos
	 */
	public List<T> obtenerTodos();

}
