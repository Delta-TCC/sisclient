package br.com.nex.controle.mb;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.nex.bean.Authority;
import br.com.nex.bean.User;
import br.com.nex.controle.AuthorityBCI;
import br.com.nex.controle.UserBCI;
import br.com.nex.util.Hash;

@Component(value = "UserMB")
@Scope(value = "session")
public class UserMB {

	private User bean;
	private User selecionado;
	private List<User> list;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
	@Autowired
	private UserBCI controle;
	@Autowired
	private AuthorityBCI controleAuthority;
	private DualListModel<Authority> auth;
	private Boolean consultaAtivo;

	@PostConstruct
	private void init() {
		this.bean = new User();
		this.list = controle.selecionar();

		List<Authority> authSource = new ArrayList<Authority>();
		List<Authority> authTarget = new ArrayList<Authority>();

		authSource = controleAuthority.selecionar();

		auth = new DualListModel<Authority>(authSource, authTarget);
		this.consultaAtivo = false;
	}

	public Boolean getConsultaAtivo() {
		return consultaAtivo;
	}

	public void setConsultaAtivo(Boolean consultaAtivo) {
		this.consultaAtivo = consultaAtivo;
	}

	public DualListModel<Authority> getAuth() {
		return auth;
	}

	public void setAuth(DualListModel<Authority> auth) {
		this.auth = auth;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public User getBean() {
		return bean;
	}

	public void setBean(User bean) {
		this.bean = bean;
	}

	public List<User> getList() {
		return list;
	}

	public void setList(List<User> list) {
		this.list = list;
	}

	public User getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(User selecionar) {
		this.selecionado = selecionar;
	}

	public void cadastrar() {
		if (!this.bean.getUsername().trim().equals("")) {
			this.controle.cadastrar(this.bean);
			this.init();
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Sucesso!",
					"Usuario cadastrado com sucesso"));
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("ERRO!",
					"Informe o cpf do Usuario"));
		}
	}

	public void deletar() {
		this.controle.excluir(this.selecionado);
		this.init();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Sucesso!",
				"Autorizações atualizadas com sucesso"));
	}

	public void consultar() {
		this.bean = this.controle.selecionar(this.bean);
		List<Authority> authSource = new ArrayList<Authority>();
		List<Authority> authTarget = new ArrayList<Authority>();

		authSource = controleAuthority.selecionar();
		authTarget = this.bean.getAuthorities();
		for (Authority authority : authTarget) {
			authSource.remove(authority);
		}
		this.consultaAtivo = true;
		auth = new DualListModel<Authority>(authSource, authTarget);
	}
	
	public void updateUserAuth(){
		List l =   this.auth.getTarget();
		this.bean.setAuthorities(new ArrayList<Authority>());
		for (Object authority :  l) {
			Authority a = new Authority();
			a.setName((String) authority);
			this.bean.getAuthorities().add(a);
		}
		this.controle.atualizar(this.bean);
		
		this.init();
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Sucesso!",
				"Usuario excluida com sucesso"));

	}

	public void updatePassword() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String login = external.getRemoteUser();
		User u = new User();
		u.setUsername(login);
		u = controle.selecionar(u);

		try {
			if (newPassword.equals(confirmPassword)
					&& u.getPassword()
							.equals(Hash.md5Convert(this.oldPassword))) {
				u.setPassword(Hash.md5Convert(newPassword));
				controle.atualizar(u);

				this.oldPassword = "";
				this.newPassword = "";
				this.confirmPassword = "";

				FacesMessage msg = new FacesMessage("Success! ",
						"senha alterada com sucesso.");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else {
				FacesMessage msg = new FacesMessage("Erro ",
						"Erro ao alterar senha, verifique os dados.");
				FacesContext.getCurrentInstance().addMessage(null, msg);

				this.oldPassword = "";
				this.newPassword = "";
				this.confirmPassword = "";
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}

}
