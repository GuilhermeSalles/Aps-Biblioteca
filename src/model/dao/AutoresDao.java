package model.dao;

import java.util.List;

import model.entities.Autor;

public interface AutoresDao {

	void insert(Autor obj);

	void update(Autor obj);

	void deleteById(Integer id);

	Autor findById(Integer id);

	List<Autor> findAll(); 
}
