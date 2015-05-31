package br.com.nex.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.nex.bean.Authority;
import br.com.nex.dao.AuthorityDaoI;

@Repository
public class AuthorityDao implements AuthorityDaoI , Serializable{

	@Autowired
	private SessionFactory session;
	
	@Override
	public void insert(Authority a) {
		this.getSession().persist(a);
	}

	@Override
	public void delete(Authority a) {
		this.getSession().delete(a);
		
	}

	@Override
	public void update(Authority a) {
		this.getSession().update(a);
	}

	@Override
	public Authority select(Authority a) {
		List<Authority> l = this.getSession().createCriteria(Authority.class)
				.add(Restrictions.eq("name", a.getName())).list();
		return l.get(0);
	}

	@Override
	public List<Authority> select() {
		return this.getSession().createCriteria(Authority.class).list();

	}
	
	private final Session getSession() {
		return this.session.getCurrentSession();
	}

}
