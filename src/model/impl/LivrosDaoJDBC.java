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
import gui.util.Utils;
import model.dao.LivrosDao;
import model.entities.Autor;
import model.entities.Editora;
import model.entities.Livros;
import model.entities.LivrosAutores;

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
			st = conn.prepareStatement("insert into Books values(?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getIsbnLivro());
			st.setString(2, obj.getTitulo());
			st.setInt(3, obj.getEditora().getIdEditora());
			st.setDouble(4, obj.getPreco());

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
	public void insert2(Livros obj) {
		
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("insert into BooksAuthors values(?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getIsbnLivro());
			st.setInt(2, obj.getAutor().getAutorId());
			st.setInt(3, Utils.tryParseToInt(obj.getLivrosAutores().getSequenciaLivroA()));
			
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

			st.setString(1, obj.getTitulo());
			st.setInt(2, obj.getEditora().getIdEditora());
			st.setDouble(3, obj.getPreco());
			st.setString(4, obj.getIsbnLivro());
			st.executeUpdate();
			

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}
	
	@Override
	public void update2(Livros obj,String AuxSeq) {
		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("update BooksAuthors set author_id = ?, seq_no = ? where isbn = ? and seq_no = ?");

			st.setInt(1, obj.getAutor().getAutorId());
			st.setInt(2, Utils.tryParseToInt(obj.getLivrosAutores().getSequenciaLivroA()));
			st.setString(3, obj.getIsbnLivro());
			st.setInt(4, Utils.tryParseToInt(AuxSeq));
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteLivro(Livros obj) {
		PreparedStatement st = null;
		try {

			st = conn.prepareStatement("delete from BooksAuthors where isbn = ?");

			st.setString(1, obj.getIsbnLivro());
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
			st = conn.prepareStatement(
					"select Books.isbn,Books.title, Books.publisher_id, Books.price,BooksAuthors.seq_no,BooksAuthors.isbn,BooksAuthors.author_id,Publishers.publisher_id, Publishers.name, Publishers.url, Authors.Author_id, Authors.Name,Authors.SecondName "
							+ "from BooksAuthors inner join Books on BooksAuthors.isbn = Books.isbn "
							+ "inner join Authors on Authors.Author_id = BooksAuthors.author_id "
							+ "inner join Publishers on Publishers.publisher_id = Books.publisher_id order by Authors.Name");
			rs = st.executeQuery();

			List<Livros> list = new ArrayList<>();

			while (rs.next()) {
				Autor autor = new Autor();
				autor.setAutorId(rs.getInt("Authors.Author_id"));
				autor.setNomeAutor(rs.getString("Authors.Name"));
				autor.setSegundoNome(rs.getString("Authors.SecondName"));

				Editora edit = new Editora();
				edit.setIdEditora(rs.getInt("Publishers.publisher_id"));
				edit.setNomeEditora(rs.getString("Publishers.name"));
				edit.setUrl(rs.getString("Publishers.url"));

				LivrosAutores lAutors = new LivrosAutores();
				lAutors.setIsbnLivrosAutor(rs.getString("BooksAuthors.isbn"));
				lAutors.setAutorLivroAutor(rs.getInt("BooksAuthors.author_id"));
				lAutors.setSequenciaLivroA(rs.getString("BooksAuthors.seq_no"));

				Livros obj = new Livros();
				obj.setIsbnLivro(rs.getString("Books.isbn"));
				obj.setTitulo(rs.getString("Books.title"));
				obj.setLivrosAutores(lAutors);
				obj.setEditora(edit);
				obj.setAutor(autor);
				obj.setPreco(rs.getDouble("Books.price"));

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
			st = conn.prepareStatement(
					"select Books.isbn,Books.title, Books.publisher_id, Books.price,Publishers.publisher_id, Publishers.name, Publishers.url, Authors.Author_id, Authors.Name,Authors.SecondName "
							+ "from BooksAuthors inner join Books on BooksAuthors.isbn = Books.isbn "
							+ "inner join Authors on Authors.Author_id = BooksAuthors.author_id "
							+ "inner join Publishers on Publishers.publisher_id = Books.publisher_id "
							+ "where Books.title = ?");

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
				obj.setEditora(edit);
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
