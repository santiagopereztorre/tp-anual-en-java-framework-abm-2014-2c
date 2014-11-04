package utn.algo2.baseDeDatos;

import java.util.List;

public interface Persistidor<T> {

	public T obtener();
	public void guardar(T objecto);
	public void modificar(T viejo, T nuevo);
	public void remover(T objeto);
	public List<T> obtenerTodo();
}
