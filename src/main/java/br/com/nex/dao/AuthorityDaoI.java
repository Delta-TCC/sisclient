package br.com.nex.dao;

import java.util.List;

import br.com.nex.bean.Authority;

public interface AuthorityDaoI {
	public void insert(Authority a);
	public void delete(Authority a);
	public void update(Authority a);
	public Authority select(Authority a);
	public List<Authority> select();
}
