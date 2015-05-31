package br.com.nex.controle.mb;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.nex.bean.Authority;
import br.com.nex.controle.AuthorityBCI;

@Component(value = "AuthorityMB")
@Scope(value = "request")
public class AuthorityMB {
	private Authority bean;
	private Authority selecionado;
	private List<Authority> list;
	@Autowired
	private AuthorityBCI controle;

	
	private Map<String, String> mapAuthoritys;
	private Map<String, Authority> mapAuthoritysObj;
	private String selectComativel;
	private String selectPrincipal;
	private TreeNode root;
	private String acao;

	@PostConstruct
	private void init() {
		this.bean = new Authority();
		this.list = controle.selecionar();
	}


	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public String getSelectComativel() {
		return selectComativel;
	}

	public void setSelectComativel(String selectComativel) {
		this.selectComativel = selectComativel;
	}

	public String getSelectPrincipal() {
		return selectPrincipal;
	}

	public void setSelectPrincipal(String selectPrincipal) {
		this.selectPrincipal = selectPrincipal;
	}

	public Map<String, String> getMapAuthoritys() {
		return mapAuthoritys;
	}

	public void setMapAuthoritys(Map<String, String> mapAuthoritys) {
		this.mapAuthoritys = mapAuthoritys;
	}

	public Map<String, Authority> getMapAuthoritysObj() {
		return mapAuthoritysObj;
	}

	public void setMapAuthoritysObj(Map<String, Authority> mapAuthoritysObj) {
		this.mapAuthoritysObj = mapAuthoritysObj;
	}

	public Authority getBean() {
		return bean;
	}

	public void setBean(Authority bean) {
		this.bean = bean;
	}

	public List<Authority> getList() {
		return list;
	}

	public void setList(List<Authority> list) {
		this.list = list;
	}

	public Authority getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(Authority selecionado) {
		this.selecionado = selecionado;
	}


	
	public void consultar() {
		this.controle.selecionar(this.selecionado);
	}


	public void cadastrar() {
		if(!this.bean.getName().trim().equals("")){
			this.controle.cadastrar(this.bean);
			this.bean = new Authority();
			this.init();
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Sucesso!",
					"Authority cadastrado com sucesso"));
		}else{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("ERRO!",
					"Informe o nome da Authority"));
		}
	}

	public void deletar() {
		this.controle.excluir(this.selecionado);
		this.init();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Sucesso!",
				"Authority excluida com sucesso"));
	}

}
