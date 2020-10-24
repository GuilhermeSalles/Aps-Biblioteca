package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.EditorasDao;
import model.entities.Editora;

public class EditoraService {

	private EditorasDao dao = DaoFactory.createEditorasDao();

	public List<Editora> findAll() {
		return dao.findAll();
	}

	public void saveOrUpdate(Editora obj) {
		if (obj.getIdEditora() == 0) {
			dao.insert(obj);
		} else {
			dao.update(obj);
		}
	}
}
