package com.example.ejercicios;

public class Ejercicios {
	public String controlDeVelocidad(int velocidad, boolean zonaEscolar) {
		return velocidad > 50 ? ( zonaEscolar ? "Carcel" : "Multa") : "Continua";
	}
}
