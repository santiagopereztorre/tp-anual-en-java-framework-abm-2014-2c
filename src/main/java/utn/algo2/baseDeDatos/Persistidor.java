package utn.algo2.baseDeDatos;

import java.util.List;

public interface Persistidor<T> {

	public void guardar(T objecto);
	public void remover(T objeto);
	public T obtener();
	public List<T> obtenerTodo();
}
