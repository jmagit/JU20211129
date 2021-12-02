package com.gildedrose.core;

import java.util.List;
import java.util.Optional;

import com.gildedrose.core.InvalidDataException;
import com.gildedrose.core.NotFoundException;

public interface Repository<T, K> {

	List<T> getAll() throws Exception;

	Optional<T> getOne(K id) throws Exception;

	void add(T item) throws InvalidDataException;

	void modify(T item) throws NotFoundException, InvalidDataException;

	void delete(T item) throws NotFoundException;

	void deleteById(K id) throws NotFoundException;

}