package model.dao;

import java.util.List;

import model.entities.Autor;

public interface AutoresDao {

	void insert(Autor obj);

	void update(Autor obj);

	void deleteById(Integer id);

	List<Autor> findByFull(String primeiroNome, String segundoNome);

	List<Autor> findAll();
}
