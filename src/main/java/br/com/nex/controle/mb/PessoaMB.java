package br.com.nex.controle.mb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import br.com.nex.bean.Authority;
import br.com.nex.bean.Pessoa;
import br.com.nex.bean.User;
import br.com.nex.controle.AuthorityBCI;
import br.com.nex.controle.PessoaBCI;

@Component(value = "PessoaMB")
@Scope(value = "session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class PessoaMB {
	private Pessoa bean;
	private Pessoa selecionado;
	private List<Pessoa> list;
	@Autowired
	private PessoaBCI controle;


	@PostConstruct
	private void init() {
		this.bean = new Pessoa();
		this.list = controle.selecionar();
	}


	
	public Pessoa getBean() {
		return bean;
	}



	public void setBean(Pessoa bean) {
		this.bean = bean;
	}



	public Pessoa getSelecionado() {
		return selecionado;
	}



	public void setSelecionado(Pessoa selecionado) {
		this.selecionado = selecionado;
	}



	public List<Pessoa> getList() {
		return list;
	}



	public void setList(List<Pessoa> list) {
		this.list = list;
	}



	public PessoaBCI getControle() {
		return controle;
	}



	public void setControle(PessoaBCI controle) {
		this.controle = controle;
	}



	public void consultar() {
		this.controle.selecionar(this.selecionado);
	}


	public void salvar() {
		if(!this.bean.getNome().trim().equals("") & !this.bean.getCpf().trim().equals("")){
			this.controle.cadastrar(this.bean);
			this.bean = new Pessoa();
			this.init();
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Sucesso!",
					"Pessoa cadastrada com sucesso"));
		}else{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("ERRO!",
					"Informe o nome e CPF da pessoa"));
		}
	}

	public void excluir() {
		this.controle.excluir(this.selecionado);
		this.init();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Sucesso!",
				"Pessoa excluida com sucesso"));
	}
	
	public void editar(){
			this.controle.atualizar(this.selecionado);
			this.bean = new Pessoa();
			this.init();
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Sucesso!",
					"Pessoa alterada com sucesso"));
	
	}

}
