package com.gildedrose.infraestructure;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.dbunit.Assertion;
import org.dbunit.DefaultOperationListener;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import com.gildedrose.domains.contracts.ItemRepository;
import com.gildedrose.domains.entities.Item;
import com.gildedrose.infraestructure.ItemRepositoryImp;

@TestMethodOrder(OrderAnnotation.class)
class ItemRepositoryTest {
	private ItemRepository dao;
	private IDatabaseTester databaseTester;
	private IDatabaseConnection connection;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		// Obtener instancia del DAO que testeamos
		dao = ItemRepositoryImp.getInstance();

		databaseTester = new JdbcDatabaseTester("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/curso?verifyServerCertificate=false&useSSL=true", "root",
				"root");
		// Inicializar la BD
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		IDataSet dataSet = builder.build(this.getClass().getResourceAsStream("/db-init.xml"));
		databaseTester.setDataSet(dataSet);
		databaseTester.setOperationListener(new DefaultOperationListener() {
			@Override
			public void connectionRetrieved(IDatabaseConnection connection) {
				connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
						new MySqlDataTypeFactory());
				super.connectionRetrieved(connection);
			}
		});
		databaseTester.onSetup();
		connection = databaseTester.getConnection();
		connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
	}

	@AfterEach
	void tearDown() throws Exception {
		connection.close();
		databaseTester.onTearDown();
	}
	
	private ITable getTablaActual(String tabla) throws Exception, DataSetException, SQLException {
		return connection.createDataSet().getTable(tabla);
	}

	private ITable getTablaActual(String tabla, String sql) throws Exception, DataSetException, SQLException {
		return connection.createQueryTable(tabla, sql);
	}

	private ITable getTablaEsperada(String resource, String tabla) throws DataSetException {
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		IDataSet expectedDataSet = builder.build(this.getClass().getResourceAsStream(resource));
		return expectedDataSet.getTable(tabla);
	}


	@Test
	void getAllTest() throws Exception {
		List<Item> actual = dao.getAll();
		Item product = actual.get(2);
		assertEquals(10, actual.size());
		assertAll("Aged Brie", 
				() -> assertEquals("Aged Brie", product.name, "name"),
				() -> assertEquals(2, product.sellIn, "sellIn"), 
				() -> assertEquals(0, product.quality, "quality"));
	}
	@Test
	void getOneTest() throws Exception {
		Optional<Item> actual = dao.getOne(3);
		assertTrue(actual.isPresent());
		Item product = actual.get();
		assertAll("Aged Brie", 
				() -> assertEquals("Aged Brie", product.name, "name"),
				() -> assertEquals(2, product.sellIn, "sellIn"), 
				() -> assertEquals(0, product.quality, "quality"));
	}
	@Test
	void getOneNotFoundTest() throws Exception {
		Optional<Item> actual = dao.getOne(33);
		assertFalse(actual.isPresent());
	}
	@Test
	@Order(1)
	void AddTest() throws Exception {
		dao.add(new Item(0,"kk", 1 , 1));
	}
	@Test
	void ModifyTest() throws Exception {
		dao.modify(new Item(10,"Modificado", 10 , 10));
	}
	@Test
	void deleteByIdTest() throws Exception {
		dao.deleteById(3);
		ITable actual = getTablaActual("products");
		ITable esperado = getTablaEsperada("/item-delete-result.xml", "products");
		Assertion.assertEquals(esperado, actual);
//		 List<Item> actual = dao.getAll();
//		 assertEquals(9, actual.size());
	}

}
