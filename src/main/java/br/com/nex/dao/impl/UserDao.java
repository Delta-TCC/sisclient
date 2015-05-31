package br.com.nex.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.nex.bean.Authority;
import br.com.nex.bean.User;
import br.com.nex.dao.UserDaoI;
@Repository
public class UserDao implements UserDaoI, Serializable {

	@Autowired
	private SessionFactory session;
	
	@Override
	public void insert(User a) {
		this.getSession().persist(a);
	}

	@Override
	public void delete(User a) {
		this.getSession().delete(a);
		
	}

	@Override
	public void update(User a) {
		this.getSession().update(a);
	}

	@Override
	public User select(User a) {
		List<User> l = this.getSession().createCriteria(User.class)
				.add(Restrictions.eq("username", a.getUsername())).list();
		return l.get(0);
	}

	@Override
	public List<User> select() {
		return this.getSession().createCriteria(User.class).list();

	}
	
	private final Session getSession() {
		return this.session.getCurrentSession();
	}

}
