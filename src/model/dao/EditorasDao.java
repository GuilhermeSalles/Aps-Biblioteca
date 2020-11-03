package model.dao;

import java.util.List;

import model.entities.Editora;

public interface EditorasDao {

	void insert(Editora obj);

	void update(Editora obj);

	void deleteById(Integer id);

	Editora findById(Integer id);

	List<Editora> findAll();

	List<Editora> findByFull(String nomeEditora);
}
