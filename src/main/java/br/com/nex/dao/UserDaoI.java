package br.com.nex.dao;

import java.util.List;

import br.com.nex.bean.User;

public interface UserDaoI {
	public void insert(User a);
	public void delete(User a);
	public void update(User a);
	public User select(User a);
	public List<User> select();
}
