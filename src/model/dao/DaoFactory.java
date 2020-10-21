
package model.dao;

import db.DB;
import model.impl.AutoresDaoJDBC;
import model.impl.EditorasDaoJDBC;
import model.impl.LivrosDaoJDBC;

public class DaoFactory {

	public static LivrosDao createLivrosDao() {
		return new LivrosDaoJDBC(DB.getConnection());
	}
	
	public static EditorasDao createEditorasDao() {
		return new EditorasDaoJDBC(DB.getConnection());
	}
	
	public static AutoresDao createAutoresDao() {
		return new AutoresDaoJDBC(DB.getConnection());
	}

}