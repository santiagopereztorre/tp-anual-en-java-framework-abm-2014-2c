package utn.algo2.validaciones;

public enum Validacion2 {
	MENOR_A_100,
	MAYOR_A_0,
	TRUE,
	FALSE;
	
	public Validador getValidador(){
		if(this==MENOR_A_100){
			return new EnteroMenorA100();
		}
//		if(this==MAYOR_A_0){
//			return new EnteroMayorA0();
//		}
		if(this==TRUE){
			return new EsTRUE();
		}
		if(this==FALSE){
			return new EsFALSE();
		}
		return null;
	}
}
