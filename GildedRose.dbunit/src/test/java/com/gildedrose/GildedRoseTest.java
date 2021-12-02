package com.gildedrose;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.dbunit.dataset.ITable;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

import com.gildedrose.core.InvalidDataException;
import com.gildedrose.core.SpaceCamelCase;

@DisplayNameGeneration(SpaceCamelCase.class)
@ExtendWith(MockitoExtension.class)
class GildedRoseTest {

	@Test
	void minQualityInvalidTest() {
		Item product = new Item("Elixir of the Mongoose", 1, -1);
		assertThrows(Exception.class, () -> new GildedRose(new Item[] { product }));
	}

	@Test
	void maxQualityInvalidTest() {
		Item product = new Item("+5 Dexterity Vest", 1, 80);
		assertThrows(Exception.class, () -> new GildedRose(new Item[] { product }));
	}

	@Test
	void sulfurasQualityInvalidTest() {
		Item product = new Item("Sulfuras, Hand of Ragnaros", 1, 66);
		assertThrows(Exception.class, () -> new GildedRose(new Item[] { product }));
	}

	@Test
	@Disabled
	void privateRefInvalidTest() {
		Item product = new Item("Elixir of the Mongoose", 1, 10);
		GildedRose app = new GildedRose(new Item[] { product });
		product.sellIn = 0;
		assertEquals(1, app.items[0].sellIn);
	}

	@ParameterizedTest
	@CsvSource({ "1, 80, 1, 80", "-1, 80, -1, 80", })
	void productSulfurasValidTest(int sellIn, int quality, int sellInResult, int qualityResult) {
		String name = "Sulfuras, Hand of Ragnaros";
		Item product = new Item(name, sellIn, quality);
		GildedRose app = new GildedRose(new Item[] { product });
		app.updateQuality();
		assertAll(name, () -> assertEquals(name, product.name, "name"),
				() -> assertEquals(sellInResult, product.sellIn, "sellIn"),
				() -> assertEquals(80, product.quality, "quality"));
	}

	@ParameterizedTest
	@CsvSource({ "-1, 1, -2, 0", "-10, 3, -11, 1", "0, 0, -1, 0", " 7, 6, 6, 5", })
	void otherProductTest(int sellIn, int quality, int sellInResult, int qualityResult) {
		String name = "Normal Product";
		Item product = new Item(name, sellIn, quality);
		GildedRose app = new GildedRose(new Item[] { product });
		app.updateQuality();
		assertAll(name, () -> assertEquals(name, product.name, "name"),
				() -> assertEquals(sellInResult, product.sellIn, "sellIn"),
				() -> assertEquals(qualityResult, product.quality, "quality"));
	}

	@ParameterizedTest
	@CsvSource({ "-2, 49, -3, 50", "-1, 48, -2, 50", "2, 50, 1, 50", "2, 0, 1, 1", })
	void productAgedBrieTest(int sellIn, int quality, int sellInResult, int qualityResult) {
		String name = "Aged Brie";
		Item product = new Item(name, sellIn, quality);
		GildedRose app = new GildedRose(new Item[] { product });
		app.updateQuality();
		assertAll(name, () -> assertEquals(name, product.name, "name"),
				() -> assertEquals(sellInResult, product.sellIn, "sellIn"),
				() -> assertEquals(qualityResult, product.quality, "quality"));
	}

	@ParameterizedTest
	@CsvSource({ "11, 0, 10, 1", "7, 1, 6, 3", "3, 49, 2, 50", "5, 3, 4, 6", "0, 3, -1, 0", "-1, 3, -2, 0", })
	void productBackstagePassesTest(int sellIn, int quality, int sellInResult, int qualityResult) {
		String name = "Backstage passes to a TAFKAL80ETC concert";
		Item product = new Item(name, sellIn, quality);
		GildedRose app = new GildedRose(new Item[] { product });
		app.updateQuality();
		assertAll(name, () -> assertEquals(name, product.name, "name"),
				() -> assertEquals(sellInResult, product.sellIn, "sellIn"),
				() -> assertEquals(qualityResult, product.quality, "quality"));
	}

	@Disabled
	@ParameterizedTest
	@CsvSource({ "11, 10, 10, 8", "7, 1, 6, -1", "-5, 10, -6, 6", "0, 3, -1, 0", })
	void productConjuredTest(int sellIn, int quality, int sellInResult, int qualityResult) {
		String name = "Conjured Mana Cake";
		Item product = new Item(name, sellIn, quality);
		GildedRose app = new GildedRose(new Item[] { product });
		app.updateQuality();
		assertAll(name, () -> assertEquals(name, product.name, "name"),
				() -> assertEquals(sellInResult, product.sellIn, "sellIn"),
				() -> assertEquals(qualityResult, product.quality, "quality"));
	}

	@Test
	@Tag("mock")
	void demoMockTest() throws Exception {
		ItemRepository dao = mock(ItemRepository.class);
		when(dao.getOne(anyInt())).thenReturn(Optional.of(new Item("Aged Brie", 1, 7)));
		// when(dao.getOne(2)).thenReturn(Optional.of(new Item("Backstage passes to a
		// TAFKAL80ETC concert", 3, 10)));

		Item product = dao.getOne(2).get();

		assertAll("Aged Brie", () -> assertEquals("Aged Brie", product.name, "name"),
				() -> assertEquals(1, product.sellIn, "sellIn"), () -> assertEquals(7, product.quality, "quality"));
	}


	Item[] items = new Item[] { new Item(1, "Normal Product", 0, 1), //
			new Item(2, "+5 Dexterity Vest", 10, 20), //
			new Item(3, "Aged Brie", 2, 0), //
			new Item(4, "Elixir of the Mongoose", 5, 7), //
			new Item(5, "Sulfuras, Hand of Ragnaros", 0, 80), //
			new Item(6, "Sulfuras, Hand of Ragnaros", -1, 80),
			new Item(7, "Backstage passes to a TAFKAL80ETC concert", 15, 20),
			new Item(8, "Backstage passes to a TAFKAL80ETC concert", 10, 49),
			new Item(9, "Backstage passes to a TAFKAL80ETC concert", 5, 49), new Item(10, "Conjured Mana Cake", 3, 6) };

	@Test
	@Tag("mock")
	void demoMockMuchosTest() throws Exception {
		ItemRepository dao = mock(ItemRepository.class);
		when(dao.getAll()).thenReturn(Arrays.asList(items));

		List<Item> lst = dao.getAll();
		Item product = lst.get(2);

		assertEquals(10, lst.size());
		assertAll("Aged Brie", () -> assertEquals("Aged Brie", product.name, "name"),
				() -> assertEquals(2, product.sellIn, "sellIn"), () -> assertEquals(0, product.quality, "quality"));
	}

	@Test
	@Tag("mock")
	void constructorGildedRoseTest() throws Exception {
		ItemRepository dao = mock(ItemRepository.class);
		when(dao.getAll()).thenReturn(Arrays.asList(items));
		when(dao.getOne(anyInt())).thenAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				if ((int) args[0] > items.length)
					return Optional.empty();
					//throw new IndexOutOfBoundsException();
				return Optional.of(items[(int) args[0]]);
			}
		});

		GildedRose app = new GildedRose(dao);
		app.updateQuality();
		Item product = dao.getOne(2).get();

		assertEquals(10, app.items.length);
		assertAll("Aged Brie", () -> assertEquals("Aged Brie", product.name, "name"),
				() -> assertEquals(1, product.sellIn, "sellIn"), () -> assertEquals(1, product.quality, "quality"));
		assertFalse(dao.getOne(22).isPresent());
		// assertThrows(IndexOutOfBoundsException.class, () -> dao.getOne(22));
	}

	@Test
	@Tag("mock")
	void saveGildedRoseTest() throws Exception {
		ItemRepository dao = mock(ItemRepository.class);
		when(dao.getAll()).thenReturn(Arrays.asList(items));
		doNothing().when(dao).modify(any());

		GildedRose app = new GildedRose(dao);
		app.updateQuality();
		Item product = app.items[2];
		app.save();

		assertEquals(10, app.items.length);
		assertAll("Aged Brie", () -> assertEquals("Aged Brie", product.name, "name"),
				() -> assertEquals(1, product.sellIn, "sellIn"), () -> assertEquals(1, product.quality, "quality"));

		verify(dao, times(10)).modify(any());
	}

	@Test
	@Tag("mock")
	void saveMalGildedRoseTest() throws Exception {
		ItemRepository dao = mock(ItemRepository.class);
		when(dao.getAll()).thenReturn(Arrays.asList(items));
		doNothing().when(dao).modify(any());
		doThrow(InvalidDataException.class).when(dao).modify(items[7]);

		GildedRose app = new GildedRose(dao);
		app.updateQuality();
		assertThrows(InvalidDataException.class, () -> app.save());

		verify(dao, times(8)).modify(any());
	}
	
	@Test
	@Tag("mock")
	@Disabled
	void saveRealGildedRoseTest() throws Exception {
		ItemRepository dao = spy(new ItemRepositoryImp());
		GildedRose app = new GildedRose(dao);

		app.updateQuality();
		app.save();
		Item product = dao.getOne(3).get();

		assertEquals(10, app.items.length);
//		assertAll("Aged Brie", () -> assertEquals("Aged Brie", product.name, "name"),
//				() -> assertEquals(-4, product.sellIn, "sellIn"), () -> assertEquals(10, product.quality, "quality"));

		verify(dao, times(10)).modify(any());
	}

}
