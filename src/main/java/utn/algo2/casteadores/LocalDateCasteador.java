package utn.algo2.casteadores;

import java.time.LocalDate;

import utn.algo2.exception.CasteoInvalidoException;

public class LocalDateCasteador implements Casteador {

	@Override
	public Object castear(String valor) {
		if (valor.length() != 10)
			throw new CasteoInvalidoException("Fecha mal ingresada dd-mm-aaaa");
		
		if (!valor.substring(2, 3).equals("-") || !valor.substring(5, 6).equals("-"))
			throw new CasteoInvalidoException("Fecha mal ingresada dd-mm-aaaa");
		
		Integer dia = null;
		Integer mes = null;
		Integer anio = null;
		try {
			dia = Integer.parseInt(valor.substring(0, 2));
			mes = Integer.parseInt(valor.substring(3, 5));
			anio = Integer.parseInt(valor.substring(6, 10));
		} catch (NumberFormatException e) {
			throw new CasteoInvalidoException("Fecha mal ingresada dd-mm-aaaa");
		}
		
		if (dia > 31 || dia < 0)
			throw new CasteoInvalidoException("Fecha mal ingresada dd-mm-aaaa");
		
		if (mes > 12 || mes < 0)
			throw new CasteoInvalidoException("Fecha mal ingresada dd-mm-aaaa");
		
		return LocalDate.of(anio, mes, dia);
	}

}
