package br.com.nex.controle;

import java.util.List;

import br.com.nex.bean.Pessoa;


public interface PessoaBCI {
	public Boolean cadastrar(Pessoa bean);
	public Boolean atualizar(Pessoa bean);
	public Boolean excluir(Pessoa bean);
	public Pessoa selecionar(Pessoa bean);
	public List<Pessoa> selecionar();
}
