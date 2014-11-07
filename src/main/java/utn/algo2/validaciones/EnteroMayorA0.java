package utn.algo2.validaciones;

public class EnteroMayorA0 extends Validador<Integer>{

	@Override
	boolean evaluaValidacion(Integer valor){
		return valor > 0;
	}

}
