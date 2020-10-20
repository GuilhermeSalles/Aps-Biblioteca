package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Livros;

public class LivroService {
	
	public List<Livros> findAll(){
		List<Livros> list = new ArrayList<>();
		list.add(new Livros("0-201-96426-0","A Guide to the SQL Standard", 1, 47.95));
		list.add(new Livros("0-201-96426-0","A Guide to the SQL Standard", 2, 48.95));
		list.add(new Livros("0-201-96426-0","A Guide to the SQL Standard", 3, 57.95));
		list.add(new Livros("0-201-96426-0","A Guide to the SQL Standard", 4, 87.95));
		return list;
	}
	
}
