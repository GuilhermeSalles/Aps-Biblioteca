package model.dao;

import java.util.List;

import model.entities.Livros;

public interface LivrosDao {

	void insert(Livros obj);

	void update(Livros obj);

	void deleteById(Integer id);

	Livros findById(Integer id);

	List<Livros> findAll(); 
}
