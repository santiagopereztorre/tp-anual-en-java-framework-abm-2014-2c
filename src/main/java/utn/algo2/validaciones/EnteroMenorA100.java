package utn.algo2.validaciones;

public class EnteroMenorA100 extends Validador<Integer>{

	@Override
	boolean evaluaValidacion(Integer valor){
		return valor < 100;
	}
}
