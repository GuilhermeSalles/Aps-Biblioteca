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
import model.dao.LivrosDao;
import model.entities.Livros;

public class LivrosDaoJDBC implements LivrosDao {

	private Connection conn;

	public LivrosDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Livros findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM produtos WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Livros obj = new Livros();
				
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
	public void insert(Livros obj) {

		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("insert into Produtos (Nome,Valor) values(?,?)",
					Statement.RETURN_GENERATED_KEYS);


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
	public void update(Livros obj) {
		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("update Produtos set Nome = ?,Valor = ? where Id = ?");
			

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

			st = conn.prepareStatement("delete from Produtos where Id = ?");

			st.setInt(1, id);
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public List<Livros> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("select * from Books order by title");
			rs = st.executeQuery();

			List<Livros> list = new ArrayList<>();

			while (rs.next()) {
				Livros obj = new Livros();
				obj.setIsbnLivro(rs.getString("isbn"));
				obj.setTitulo(rs.getString("title"));
				obj.setEditoraId(rs.getInt("publisher_id"));
				obj.setPreco(rs.getDouble("price"));
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
