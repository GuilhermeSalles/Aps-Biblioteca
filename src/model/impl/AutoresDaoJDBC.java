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
import model.dao.AutoresDao;
import model.entities.Autor;

public class AutoresDaoJDBC implements AutoresDao {

	private Connection conn;

	public AutoresDaoJDBC(Connection conn) {
		this.conn = conn;
	}


	@Override
	public void insert(Autor obj) {

		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("insert into Authors (Name,SecondName) values(?,?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNome());
			st.setString(2, obj.getSegundoNome());
			
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
	public void update(Autor obj) {
		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("update Authors set Name = ?,SecondName = ? where Author_id = ?");
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getSegundoNome());
			st.setInt(3, obj.getAutorId());
			
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

			st = conn.prepareStatement("delete from Authors where Author_id = ?");

			st.setInt(1, id);
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public List<Autor> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("select * from Authors order by Name");
			rs = st.executeQuery();

			List<Autor> list = new ArrayList<>();

			while (rs.next()) {
				Autor obj = new Autor();
				obj.setAutorId(rs.getInt("Author_id"));
				obj.setNome(rs.getString("Name"));
				obj.setSegundoNome(rs.getString("SecondName"));
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
	
	
	@Override
	public List<Autor> findByFull(String primeiroNome, String segundoNome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM Authors WHERE Name = ? and SecondName = ?");
			st.setString(1, primeiroNome);
			st.setString(2, segundoNome);
			rs = st.executeQuery();
			
			List<Autor> list = new ArrayList<>();
			
			while (rs.next()) {
				Autor obj = new Autor();
				obj.setAutorId(rs.getInt("Author_id"));
				obj.setNome(rs.getString("Name"));
				obj.setSegundoNome(rs.getString("SecondName"));
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
