package utn.algo2.validaciones;

public class EnteroMayorA0 implements Validacion {

	public boolean evaluar(Object objeto){
		Integer valor = (Integer) objeto;
		return valor > 0;
	}

}
