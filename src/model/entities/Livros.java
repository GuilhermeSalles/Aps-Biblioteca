package model.entities;

import java.io.Serializable;

public class Livros implements Serializable {

	private static final long serialVersionUID = 1L;

	private String isbnLivro;
	private String titulo;
	private Editora editora;
	private Autor autor;
	private double preco;

	private LivrosAutores livrosAutores;

	public Livros() {

	}

	public Livros(String isbnLivro, String titulo, Editora editoraNome, Autor autor, double preco,
			LivrosAutores livrosAutores) {
		super();
		this.isbnLivro = isbnLivro;
		this.titulo = titulo;
		this.editora = editoraNome;
		this.autor = autor;
		this.preco = preco;
		this.livrosAutores = livrosAutores;
	}

	public String getIsbnLivro() {
		return isbnLivro;
	}

	public void setIsbnLivro(String isbnLivro) {
		this.isbnLivro = isbnLivro;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Editora getEditora() {
		return editora;
	}

	public void setEditora(Editora editoraNome) {
		this.editora = editoraNome;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public LivrosAutores getLivrosAutores() {
		return livrosAutores;
	}

	public void setLivrosAutores(LivrosAutores livrosAutores) {
		this.livrosAutores = livrosAutores;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isbnLivro == null) ? 0 : isbnLivro.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Livros other = (Livros) obj;
		if (isbnLivro == null) {
			if (other.isbnLivro != null)
				return false;
		} else if (!isbnLivro.equals(other.isbnLivro))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Livros [isbnLivro=" + isbnLivro + ", titulo=" + titulo + ", editoraId=" + editora + ", preco="
				+ preco + "]";
	}

}
