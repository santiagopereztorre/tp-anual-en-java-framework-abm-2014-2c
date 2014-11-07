package utn.algo2.validaciones;

public abstract class Validador<T> {
	
	Validacion validacion;
	
	boolean evaluaValidacion(T t){
		return true;
	}
}