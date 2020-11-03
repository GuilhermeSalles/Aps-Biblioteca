package model.services;

import java.util.List;

import model.dao.AutoresDao;
import model.dao.DaoFactory;
import model.entities.Autor;

public class AutorService {

	private AutoresDao dao = DaoFactory.createAutoresDao();

	public List<Autor> findAll() {
		return dao.findAll();
	}

	public void saveOrUpdate(Autor obj) {
		if (obj.getAutorId() == 0) {
			dao.insert(obj);
		} else {
			dao.update(obj);
		}
	}

	public void remove(Autor obj) {
		dao.deleteById(obj.getAutorId());
	}

	public List<Autor> findByFull(String aux, String aux1) {
		return dao.findByFull(aux, aux1);
	}
}
