package model.services;

import java.util.List;

import model.dao.AutoresDao;
import model.dao.DaoFactory;
import model.entities.Autor;

public class AutorService {
	
	private AutoresDao dao = DaoFactory.createAutoresDao();
	
	public List<Autor> findAll(){
		return dao.findAll();
	}
	
}