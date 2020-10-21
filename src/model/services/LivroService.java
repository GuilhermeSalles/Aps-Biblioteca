package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.LivrosDao;
import model.entities.Livros;

public class LivroService {
	
	private LivrosDao dao = DaoFactory.createLivrosDao();
	
	public List<Livros> findAll(){
		return dao.findAll();
	}
	
}
