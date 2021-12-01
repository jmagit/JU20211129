package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@TestInstance(Lifecycle.PER_CLASS)
@DisplayName("Demos de las pruebas")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CalcTest {
	private static Calc calc;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		calc = new Calc();
	}

	@AfterEach
	void tearDown() throws Exception {

	}

	@Nested
	public class Test_Suma {
		double rslt = 0;

		@Nested
		public class Test_OK {

			@ParameterizedTest(name = "{index} => {0} + {1} = {2}")
			@CsvSource({ 
				"1,1,2", 
				"0,0,0", 
				"1, -1, 0", 
				"'0.1', '0.2', 0.3"
				})
			void testInternos(double a, double b, double r) {
				assertEquals(r, calc.suma(a, b));
			}
			
			@ParameterizedTest(name = "{index} => {0} + {1} = {2}")
			@CsvFileSource(resources = "/casos.csv", numLinesToSkip = 1)
			void testExtermos(double a, double b, double r) {
				assertEquals(r, calc.suma(a, b));
			}
			void testSuma_Enteros() {
//		double rslt = 0;
//		assertNotNull(calc);
//		assertEquals(4, rslt);
//		assertEquals(4, calc.suma(1, 3));
				assertTimeout(Duration.ofMillis(100), () -> rslt = calc.suma(1, 3), "Lenta");
				assertEquals(4, rslt);
//		assertEquals(5, calc.suma(1, 3), "Segundo");
			}
		}

		@Nested
		public class Test_KO {

			@Test
			void testSuma_Double() {
//		assertEquals(0.3, calc.suma(0.1, 0.2) );
			}
		}
	}

	@Nested
	public class Test_Resta {
		@Test
		void testResta() {
			assertEquals(-0.1, calc.resta(0.1, 0.2));
			assertEquals(1, calc.resta(2, 1));
//		assertEquals(0.1, calc.resta(1, 0.9) );
			assertEquals(0, calc.resta(1, 1));
			assertEquals(2, calc.resta(1, -1));
		}
	}

	@Nested
	public class Test_Divisiones {
		@Test
		void test_Divide_dos_reales() {
			assertEquals(2, calc.divide(4.0, 2));
			assertEquals(1, calc.divide(4.0, 4));
//		try {
//			calc.divide(4.0, 0);
//			fail("No salta la excepcion");
//		} catch (ArithmeticException e) {
//		} catch (Exception e) {
//			fail("No salta la excepcion esperada");
//		}
//		
			assertThrows(ArithmeticException.class, () -> calc.divide(4.0, 0));
		}

		@Test
		@Disabled
		void test_Divide_dos_enteros() {
			assertEquals(Double.POSITIVE_INFINITY, calc.divide(4, 0));
		}
	}
	@Nested
	public class Test_isPositive {
		@Nested
		public class Test_OK {

			@ParameterizedTest(name = "{index} => con el ''{0}''")
			@ValueSource(doubles = { 0, 100, 1000, 99 })
			void testValidos(double caso) {
				assertTrue(calc.isPositive(caso));
			}
		}

		@Nested
		public class Test_KO {

			@ParameterizedTest(name = "{index} => con el ''{0}''")
			@ValueSource(doubles = { -0.000000000001, -100 })
			void testInvalidos(double caso) {
				assertFalse(calc.isPositive(caso));
			}
		}
	}


}
