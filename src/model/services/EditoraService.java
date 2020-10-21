package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.EditorasDao;
import model.entities.Editora;

public class EditoraService {
	
	private EditorasDao dao = DaoFactory.createEditorasDao();
	
	public List<Editora> findAll(){
		return dao.findAll();
	}
	
}
