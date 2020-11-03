package model.entities;

import java.io.Serializable;

public class Editora  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer idEditora;
	private String nomeEditora;
	private String url;
	
	public Editora() {
		
	}
	
	public Editora(int idEditora, String nomeEditora, String url) {
		this.idEditora = idEditora;
		this.nomeEditora = nomeEditora;
		this.url = url;
	}
	
	public Integer getIdEditora() {
		return idEditora;
	}
	public void setIdEditora(Integer idEditora) {
		this.idEditora = idEditora;
	}
	public String getNomeEditora() {
		return nomeEditora;
	}
	public void setNomeEditora(String nomeEditora) {
		this.nomeEditora = nomeEditora;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idEditora;
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
		Editora other = (Editora) obj;
		if (idEditora != other.idEditora)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Editora [idEditora=" + idEditora + ", nomeEditora=" + nomeEditora + ", url=" + url + "]";
	}		
}
