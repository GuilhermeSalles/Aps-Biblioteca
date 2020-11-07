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
	
	public void saveOrUpdate(Livros obj) {
		if (obj.getIsbnLivro() == null) {
			dao.insert(obj);
		} else {
			dao.update(obj);
		}
	}

	public void remove(Livros obj) {
		dao.deleteById(obj.getIsbnLivro());
	}
	
	public List<Livros> busca(String title) {
		return dao.findTitle(title);
	}
}
