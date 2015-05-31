package br.com.nex.dao;

import java.util.List;

import br.com.nex.bean.Pessoa;

public interface PessoaDaoI {
	public void insert(Pessoa bean);
	public void delete(Pessoa bean);
	public void update(Pessoa bean);
	public Pessoa select(Pessoa bean);
	public List<Pessoa> select();
}
