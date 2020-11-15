package model.dao;

import java.util.List;

import model.entities.Livros;

public interface LivrosDao {

	void insert(Livros obj);
	
	void insert2(Livros obj);

	void update(Livros obj);

	void update2(Livros obj,String AuxSeq);

	void deleteLivro(Livros obj);

	Livros findById(String id);

	List<Livros> findAll();

	List<Livros> findTitle(String title);
}
