package utn.algo2.validaciones;

public class EsFALSE extends Validador<Boolean>{

	@Override
	public boolean evaluaValidacion(Boolean valor){
		return !valor;
	}

}
