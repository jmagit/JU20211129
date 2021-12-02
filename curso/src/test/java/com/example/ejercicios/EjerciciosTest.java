package com.example.ejercicios;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class EjerciciosTest {
	@Nested
	class ControlDeVelocidad {
		Ejercicios ejercicio = new Ejercicios();
		
		@Test
		void Test_ControlDeVelocidad_carcel() {
			assertEquals("Carcel", ejercicio.controlDeVelocidad(51, true));
		}

		@Test
		void Test_ControlDeVelocidad_multa() {
			assertEquals("Multa", ejercicio.controlDeVelocidad(51, false));
		}

		@Test
		void Test_ControlDeVelocidad_continua() {
			assertAll("ControlDeVelocidad", 
					() -> assertEquals("Continua", ejercicio.controlDeVelocidad(50, false)),
					() -> assertEquals("Continua", ejercicio.controlDeVelocidad(50, true))
					);
		}
	}
}
