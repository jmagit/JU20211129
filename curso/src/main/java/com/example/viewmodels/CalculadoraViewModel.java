package com.example.viewmodels;

import java.security.InvalidParameterException;

public class CalculadoraViewModel {
	double acumula;
	char pendiente;
	boolean limpia;
	String pantalla, resumen;	

	public CalculadoraViewModel() {
		init();
	}
	public void init() {
		acumula=0;
		pendiente='+';
		limpia = true;
		pantalla = "0";
		resumen = "";		
	}
	
	public void ponDigito(String digito) {
		if(digito == null)
			throw new InvalidParameterException();
		if(!(digito.length() == 1 && '0' <= digito.charAt(0) && digito.charAt(0) <= '9') )
			throw new InvalidParameterException();
		if(limpia && "0".equals(pantalla)) {
			pantalla = digito;
			limpia = false;
		} else 
			pantalla += digito;		
	}

}
