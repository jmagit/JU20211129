package com.gildedrose.domains.servicios;

import java.util.List;
import java.util.Optional;

import com.gildedrose.core.InvalidDataException;
import com.gildedrose.core.NotFoundException;
import com.gildedrose.core.Repository;
import com.gildedrose.domains.contracts.ItemRepository;
import com.gildedrose.domains.contracts.ItemService;
import com.gildedrose.domains.entities.Item;

public class ItemsServiceImpl implements ItemService {
	ItemRepository dao;

	public ItemsServiceImpl(ItemRepository dao) {
		this.dao = dao;
	}

	public List<Item> getAll() throws Exception {
		return dao.getAll();
	}

	@Override
	public Optional<Item> getOne(Integer id) throws Exception {
		return dao.getOne(id);
	}

	@Override
	public void add(Item item) throws InvalidDataException {
		// validar
		dao.add(item);
		
	}

	@Override
	public void modify(Item item) throws NotFoundException, InvalidDataException {
		// validar
		dao.modify(item);
	}

	@Override
	public void delete(Item item) throws NotFoundException {
		deleteById(item.id);
		
	}

	@Override
	public void deleteById(Integer id) throws NotFoundException {
		// validar
		dao.deleteById(id);
	}

	@Override
	public void updateQuality() throws Exception {
		List<Item> items = getAll();
		calculaQuality();
		items.forEach(item -> {
			try {
				modify(item);
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	public void calculaQuality() throws Exception {
	}

}
