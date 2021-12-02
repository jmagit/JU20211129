package com.example;

import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import com.example.util.Humo;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;

@TestMethodOrder(OrderAnnotation.class)
@Tag("Personas")
class PersonaTest {

	@Test
	@Order(1)
	@Humo
	void testPersona() {
		var p = new Persona(1, "Pepito", "Grillo", 55);
		assertAll("Persona", 
				() -> assertEquals(1, p.getId(), "Id"),
				() -> assertEquals("Pepito", p.getNombre(), "Nombre"),
				() -> assertEquals("Grillo", p.getApellidos(), "Apellidos"),
				() -> assertEquals(55, p.getEdad(), "Edad")
				);
	}

	@Test
	void testGetId() {
		fail("Not yet implemented");
	}

	@Test
	void testSetId() {
		fail("Not yet implemented");
	}

	@Test
	void testGetNombre() {
		fail("Not yet implemented");
	}

	@Test
	void testSetNombre() {
		fail("Not yet implemented");
	}

	@Test
	void testGetApellidos() {
		fail("Not yet implemented");
	}

	@Test
	void testSetApellidos() {
		fail("Not yet implemented");
	}

	@Test
	void testGetEdad() {
		fail("Not yet implemented");
	}

	@Test
	@Order(2)
	@Timeout(value = 10, unit = TimeUnit.SECONDS)
	void testCumpleAños() {
		var p = new Persona(1, "Pepito", "Grillo", 66);
//		assumeTrue(p.getEdad() == 65);
		p.cumpleAños();
//		assertEquals(56, p.getEdad(), "Edad");
//		assertEquals(56, p.edad, "Edad");
		assertTrue(p.isJubilado());
		assumeTrue(false);
	}

}
