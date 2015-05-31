package br.com.nex.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.nex.bean.Pessoa;
import br.com.nex.dao.PessoaDaoI;

@Repository
public class PessoaDao implements PessoaDaoI , Serializable{

	@Autowired
	private SessionFactory session;
	
	@Override
	public void insert(Pessoa bean) {
		this.getSession().persist(bean);
	}

	@Override
	public void delete(Pessoa bean) {
		this.getSession().delete(bean);
		
	}

	@Override
	public void update(Pessoa bean) {
		this.getSession().update(bean);
	}

	@Override
	public Pessoa select(Pessoa bean) {
		List<Pessoa> l = this.getSession().createCriteria(Pessoa.class)
				.add(Restrictions.eq("name", bean.getNome())).list();
		return l.get(0);
	}

	@Override
	public List<Pessoa> select() {
		return this.getSession().createCriteria(Pessoa.class).list();

	}
	
	private final Session getSession() {
		return this.session.getCurrentSession();
	}

}
