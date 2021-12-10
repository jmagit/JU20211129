package com.gildedrose.infraestructure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.gildedrose.core.InvalidDataException;
import com.gildedrose.core.NotFoundException;
import com.gildedrose.domains.contracts.ItemRepository;
import com.gildedrose.domains.entities.Item;

/*
CREATE DATABASE `curso` 

CREATE TABLE `products` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(250) NOT NULL,
	`sellIn` SMALLINT NOT NULL,
	`quality` TINYINT NOT NULL,
	PRIMARY KEY (`id`)
)

*/

public class ItemRepositoryImp implements ItemRepository {
	public static ItemRepository getInstance() {
		return new ItemRepositoryImp();
	}

	@Override
	public List<Item> getAll() throws Exception {
		List<Item> rslt = new ArrayList<Item>();
		try (Connection connection = DBHelper.getConnection()) {
			String sql = "select * from products";
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				rslt.add(new Item(rs.getInt("id"), rs.getString("name"), rs.getInt("sellIn"), rs.getInt("quality")));
			}
		} catch (SQLException ex) {
			throw new InvalidDataException(ex.getMessage(), ex.getCause());
		}
		return rslt;
	}

	@Override
	public Optional<Item> getOne(Integer id) throws Exception {
		try (Connection connection = DBHelper.getConnection()) {
			String sql = "select * from products where id = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return Optional
						.of(new Item(rs.getInt("id"), rs.getString("name"), rs.getInt("sellIn"), rs.getInt("quality")));
			} else {
				return Optional.empty();
			}
		} catch (SQLException ex) {
			throw new InvalidDataException(ex.getMessage(), ex.getCause());
		}
	}

	@Override
	public void add(Item item) throws InvalidDataException {
		try (Connection connection = DBHelper.getConnection()) {
			String sql = "insert into products (name, sellIn, quality) values (?,?,?)";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, item.name);
			stmt.setInt(2, item.sellIn);
			stmt.setInt(3, item.quality);
			if (stmt.executeUpdate() != 1) {
				throw new InvalidDataException("Insert failed!");
			}
		} catch (SQLException ex) {
			throw new InvalidDataException(ex.getMessage(), ex.getCause());
		}
	}

	@Override
	public void modify(Item item) throws NotFoundException, InvalidDataException {
		try (Connection connection = DBHelper.getConnection()) {
			String sql = "update products set name = ?, sellIn = ?, quality = ? where id = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, item.name);
			stmt.setInt(2, item.sellIn);
			stmt.setInt(3, item.quality);
			stmt.setInt(4, item.id);
			if (stmt.executeUpdate() != 1) {
				throw new InvalidDataException("Update failed!");
			}
		} catch (SQLException ex) {
			throw new InvalidDataException(ex.getMessage(), ex.getCause());
		}
	}

	@Override
	public void delete(Item item) throws NotFoundException {
		deleteById(item.id);
	}

	@Override
	public void deleteById(Integer id) throws NotFoundException {
		try (Connection connection = DBHelper.getConnection()) {
			String sql = "delete from products where id = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			if (stmt.executeUpdate() != 1) {
				throw new NotFoundException();
			}
		} catch (SQLException ex) {
			throw new NotFoundException();
		}
	}
}
