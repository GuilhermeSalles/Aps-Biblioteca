package model.entities;

import java.io.Serializable;

public class Livros implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String isbnLivro;
	private String titulo;
	private int editoraId;
	private double preco;
	
	
	public Livros() {
		
	}
	
	public Livros(String isbnLivro, String titulo, int editoraId, double preco) {
		this.isbnLivro = isbnLivro;
		this.titulo = titulo;
		this.editoraId = editoraId;
		this.preco = preco;
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

	public int getEditoraId() {
		return editoraId;
	}

	public void setEditoraId(int editoraId) {
		this.editoraId = editoraId;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
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
		return "Livros [isbnLivro=" + isbnLivro + ", titulo=" + titulo + ", editoraId=" + editoraId + ", preco=" + preco
				+ "]";
	}
	
	
}