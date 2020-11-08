package model.entities;

import java.io.Serializable;

public class LivrosAutores implements Serializable {

	private static final long serialVersionUID = 1L;

	private String isbnLivrosAutor;
	private int autorLivroAutor;
	private String sequenciaLivroA;

	public LivrosAutores() {

	}

	public LivrosAutores(String isbnLivrosAutor, int autorLivroAutor, String sequenciaLivroA) {
		super();
		this.isbnLivrosAutor = isbnLivrosAutor;
		this.autorLivroAutor = autorLivroAutor;
		this.sequenciaLivroA = sequenciaLivroA;
	}

	public String getIsbnLivrosAutor() {
		return isbnLivrosAutor;
	}

	public void setIsbnLivrosAutor(String isbnLivrosAutor) {
		this.isbnLivrosAutor = isbnLivrosAutor;
	}

	public int getAutorLivroAutor() {
		return autorLivroAutor;
	}

	public void setAutorLivroAutor(int string) {
		this.autorLivroAutor = string;
	}

	public String getSequenciaLivroA() {
		return sequenciaLivroA;
	}

	public void setSequenciaLivroA(String sequenciaLivroA) {
		this.sequenciaLivroA = sequenciaLivroA;
	}

	@Override
	public String toString() {
		return  String.valueOf(sequenciaLivroA) ;
	}

}
