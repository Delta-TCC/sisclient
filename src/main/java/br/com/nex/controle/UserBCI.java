package br.com.nex.controle;

import java.util.List;

import br.com.nex.bean.User;

public interface UserBCI {
	public Boolean cadastrar(User u);
	public Boolean atualizar(User u);
	public Boolean excluir(User u);
	public User selecionar(User u);
	public List<User> selecionar();
}
