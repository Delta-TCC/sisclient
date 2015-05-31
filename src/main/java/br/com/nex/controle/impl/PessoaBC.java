package br.com.nex.controle.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import br.com.nex.bean.Authority;
import br.com.nex.bean.Pessoa;
import br.com.nex.controle.AuthorityBCI;
import br.com.nex.controle.PessoaBCI;
import br.com.nex.dao.AuthorityDaoI;
import br.com.nex.dao.PessoaDaoI;

@Controller
public class PessoaBC implements PessoaBCI, Serializable {

	@Autowired
	private PessoaDaoI dao;
	
	@Transactional
	public Boolean cadastrar(Pessoa bean) {
		dao.insert(bean);
		return true;
	}

	@Transactional
	public Boolean atualizar(Pessoa bean) {
		dao.update(bean);
		return true;
	}

	@Transactional
	public Boolean excluir(Pessoa bean) {
		dao.delete(bean);
		return true;
	}

	@Transactional
	public Pessoa selecionar(Pessoa bean) {
		return dao.select(bean);
	}

	@Transactional
	public List<Pessoa> selecionar() {
		return dao.select();
	}

}
