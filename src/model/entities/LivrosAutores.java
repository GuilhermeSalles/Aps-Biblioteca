package model.entities;

import java.io.Serializable;

public class LivrosAutores  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer isbnLivrosAutor;
	private Integer autorLivroAutor;
	private String sequenciaLivroA;
	
	public LivrosAutores() {
		
	}
	
	public LivrosAutores(Integer isbnLivrosAutor, Integer autorLivroAutor, String sequenciaLivroA) {
		this.isbnLivrosAutor = isbnLivrosAutor;
		this.autorLivroAutor = autorLivroAutor;
		this.sequenciaLivroA = sequenciaLivroA;
	}

	public Integer getIsbnLivrosAutor() {
		return isbnLivrosAutor;
	}

	public void setIsbnLivrosAutor(Integer isbnLivrosAutor) {
		this.isbnLivrosAutor = isbnLivrosAutor;
	}

	public Integer getAutorLivroAutor() {
		return autorLivroAutor;
	}

	public void setAutorLivroAutor(Integer autorLivroAutor) {
		this.autorLivroAutor = autorLivroAutor;
	}

	public String getSequenciaLivroA() {
		return sequenciaLivroA;
	}

	public void setSequenciaLivroA(String sequenciaLivroA) {
		this.sequenciaLivroA = sequenciaLivroA;
	}

	@Override
	public String toString() {
		return "LivrosAutores [isbnLivrosAutor=" + isbnLivrosAutor + ", autorLivroAutor=" + autorLivroAutor
				+ ", sequenciaLivroA=" + sequenciaLivroA + "]";
	}
	
	
}
