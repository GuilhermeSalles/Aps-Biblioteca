package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.LivrosDao;
import model.entities.Livros;

public class LivroService {

	private LivrosDao dao = DaoFactory.createLivrosDao();

	public List<Livros> findAll() {
		return dao.findAll();
	}

	public void save(Livros obj) {
		dao.insert(obj);
		dao.insert2(obj);
	}

	public void update(Livros obj) {
		dao.update(obj);
	}
	
	public void update2(Livros obj,String AuxSeq) {
		dao.update2(obj,AuxSeq);
	}

	public void remove(Livros obj) {
		dao.deleteLivro(obj);
	}

	public List<Livros> busca(String title) {
		return dao.findTitle(title);
	}
}
