package model.dao;

import java.util.List;

import model.entities.Livros;

public interface LivrosDao {

	void insert(Livros obj);

	void update(Livros obj);

	void deleteById(String string);

	Livros findById(String id);

	List<Livros> findAll(); 
	
	List<Livros> findTitle(String title); 
}
