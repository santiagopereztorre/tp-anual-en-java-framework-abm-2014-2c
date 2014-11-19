package utn.algo2.casteadores;

import utn.algo2.exception.CasteoInvalidoException;

public class DoubleCasteador implements Casteador {

	@Override
	public Object castear(String valor) {
		Double valorCasteado = null;
		try {
			valorCasteado = Double.parseDouble(valor);
		} catch (NumberFormatException e) {
			throw new CasteoInvalidoException(valor + " no puede ser casteado a Double");
		}
		return valorCasteado;
	}

}
