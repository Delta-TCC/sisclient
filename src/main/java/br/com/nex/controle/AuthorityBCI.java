package br.com.nex.controle;

import java.util.List;

import br.com.nex.bean.Authority;

public interface AuthorityBCI {
	public Boolean cadastrar(Authority a);
	public Boolean atualizar(Authority a);
	public Boolean excluir(Authority a);
	public Authority selecionar(Authority a);
	public List<Authority> selecionar();
}
