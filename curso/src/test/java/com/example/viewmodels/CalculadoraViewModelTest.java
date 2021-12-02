package com.example.viewmodels;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.security.InvalidParameterException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class CalculadoraViewModelTest {
	@Test
	void create() {
		var calc = new CalculadoraViewModel();
		assertNotNull(calc);
	}
	@Test
	void test_init() {
		var calc = new CalculadoraViewModel();
		calc.init();
		assertAll(
				() -> assertEquals(0, calc.acumula),
				() -> assertEquals('+', calc.pendiente),
				() -> assertEquals(true, calc.limpia),
				() -> assertEquals("0", calc.pantalla),
				() -> assertEquals("", calc.resumen)	
			);
	}
	@Test
	void test_ponDigito_OK() {
		var calc = new CalculadoraViewModel();
		calc.ponDigito("9");
		calc.ponDigito("8");
		calc.ponDigito("7");
		calc.ponDigito("6");
		calc.ponDigito("5");
		calc.ponDigito("4");
		calc.ponDigito("3");
		calc.ponDigito("2");
		calc.ponDigito("1");
		calc.ponDigito("0");
		assertEquals("9876543210", calc.pantalla);
		assertEquals(false, calc.limpia);
	}
	@ParameterizedTest()
	@ValueSource(strings = {"?", "*", "12" })
	@NullAndEmptySource
	void test_ponDigito_KO(String caso) {
		var calc = new CalculadoraViewModel();
		assertThrows(InvalidParameterException.class, () -> calc.ponDigito(caso));
	}

}
