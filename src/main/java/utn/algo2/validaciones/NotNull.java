package utn.algo2.validaciones;

public class NotNull implements Validacion {

	@Override
	public boolean evaluar(Object object) {
		return !object.equals(null);
	}

}
