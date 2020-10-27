package model.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.EditorasDao;
import model.entities.Editora;

public class EditorasDaoJDBC implements EditorasDao {

	private Connection conn;

	public EditorasDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Editora findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM produtos WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Editora obj = new Editora();
				
				return obj;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public void insert(Editora obj) {

		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("insert into Publishers (name,url) values(?,?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNomeEditora());
			st.setString(2, obj.getUrl());
			
			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					
				}
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(Editora obj) {
		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("update Publishers set name = ?,url = ? where publisher_id = ?");
			
			st.setString(1, obj.getNomeEditora());
			st.setString(2, obj.getUrl());
			st.setInt(3, obj.getIdEditora());
			
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {

			st = conn.prepareStatement("delete from Publishers where publisher_id = ?");

			st.setInt(1, id);
			
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public List<Editora> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("select * from Publishers order by name");
			rs = st.executeQuery();

			List<Editora> list = new ArrayList<>();

			while (rs.next()) {
				Editora obj = new Editora();
				obj.setIdEditora(rs.getInt("publisher_id"));
				obj.setNomeEditora(rs.getString("name"));
				obj.setUrl(rs.getString("url"));
				list.add(obj);    
			}
			
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

}
