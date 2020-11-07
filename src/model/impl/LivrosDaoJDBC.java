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
import model.entities.Autor;
import model.entities.Editora;
import model.entities.Livros;

public class LivrosDaoJDBC implements LivrosDao {

	private Connection conn;

	public LivrosDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Livros findById(String id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM produtos WHERE Id = ?");
			st.setString(1, id);
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

			st = conn.prepareStatement("update Books set title = ?,publisher_id = ?,price = ? where isbn = ?");
			

			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteById(String id) {
		PreparedStatement st = null;
		try {

			st = conn.prepareStatement("delete from Books where isbn = ?");

			st.setString(1, id);
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
			st = conn.prepareStatement("select Books.isbn,Books.title, Books.publisher_id, Books.price,Publishers.publisher_id, Publishers.name, Publishers.url, Authors.Author_id, Authors.Name,Authors.SecondName " + 
					"from BooksAuthors inner join Books on BooksAuthors.isbn = Books.isbn " + 
					"inner join Authors on Authors.Author_id = BooksAuthors.author_id " + 
					"inner join Publishers on Publishers.publisher_id = Books.publisher_id order by Books.title");
			rs = st.executeQuery();

			List<Livros> list = new ArrayList<>();

			while (rs.next()) {
				Autor autor = new Autor();
				autor.setAutorId(rs.getInt("Author_id"));
				autor.setNomeAutor(rs.getString("Authors.Name"));
				autor.setSegundoNome(rs.getString("SecondName"));
				
				Editora edit = new Editora();
				edit.setIdEditora(rs.getInt("publisher_id"));
				edit.setNomeEditora(rs.getString("Publishers.name"));
				edit.setUrl(rs.getString("url"));
				
				Livros obj = new Livros();
				obj.setIsbnLivro(rs.getString("isbn"));
				obj.setTitulo(rs.getString("title"));
				obj.setEditoraNome(edit);
				obj.setAutor(autor);
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
	@Override
	public List<Livros> findTitle(String title) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("select Books.isbn,Books.title, Books.publisher_id, Books.price,Publishers.publisher_id, Publishers.name, Publishers.url, Authors.Author_id, Authors.Name,Authors.SecondName " + 
					"from BooksAuthors inner join Books on BooksAuthors.isbn = Books.isbn " + 
					"inner join Authors on Authors.Author_id = BooksAuthors.author_id " + 
					"inner join Publishers on Publishers.publisher_id = Books.publisher_id " + 
					"where Books.title = ?");
			
			st.setString(1, title);
			rs = st.executeQuery();
			
			List<Livros> list = new ArrayList<>();
			
			while (rs.next()) {
				Autor autor = new Autor();
				autor.setAutorId(rs.getInt("Author_id"));
				autor.setNomeAutor(rs.getString("Authors.Name"));
				autor.setSegundoNome(rs.getString("SecondName"));
				
				Editora edit = new Editora();
				edit.setIdEditora(rs.getInt("publisher_id"));
				edit.setNomeEditora(rs.getString("Publishers.name"));
				edit.setUrl(rs.getString("url"));
				
				Livros obj = new Livros();
				obj.setIsbnLivro(rs.getString("isbn"));
				obj.setTitulo(rs.getString("title"));
				obj.setEditoraNome(edit);
				obj.setAutor(autor);
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
