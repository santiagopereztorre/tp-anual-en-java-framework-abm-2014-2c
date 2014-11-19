package utn.algo2.casteadores;

import java.time.LocalDate;

import utn.algo2.exception.CasteoInvalidoException;

public class LocalDateCasteador implements Casteador {

	@Override
	public Object castear(String valor) {
		Integer dia = null;
		Integer mes = null;
		Integer anio = null;
		try {
			dia = Integer.parseInt(valor.substring(0, 2));
			mes = Integer.parseInt(valor.substring(3, 5));
			anio = Integer.parseInt(valor.substring(6, 10));
		} catch (NumberFormatException e) {
			throw new CasteoInvalidoException("Mal ingresada la fecha");
		}
		return LocalDate.of(anio, mes, dia);
	}

}
