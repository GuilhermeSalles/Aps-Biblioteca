package model.entities;

import java.io.Serializable;

public class Autor implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer autorId;
	private String nome;
	private String segundoNome;
	
	public Autor() {
		
	}
	
	public Autor(Integer autorId, String nome, String segundoNome) {
		this.autorId = autorId;
		this.nome = nome;
		this.segundoNome = segundoNome;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSegundoNome() {
		return segundoNome;
	}

	public void setSegundoNome(String segundoNome) {
		this.segundoNome = segundoNome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((autorId == null) ? 0 : autorId.hashCode());
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
		Autor other = (Autor) obj;
		if (autorId == null) {
			if (other.autorId != null)
				return false;
		} else if (!autorId.equals(other.autorId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Autor [autorId=" + autorId + ", nome=" + nome + ", segundoNome=" + segundoNome + "]";
	}
	
	
	
}
