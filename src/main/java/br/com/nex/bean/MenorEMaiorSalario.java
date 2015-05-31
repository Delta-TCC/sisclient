package br.com.nex.bean;


public class MenorEMaiorSalario {

	private Pessoa salarioMenor;
	private Pessoa salarioMaior;

	public void encontra(GrupoPessoas gp) {
		for(Pessoa pessoa : gp.pessoas){
		if (salarioMenor == null
				|| pessoa.getValorSalario() < salarioMenor.getValorSalario()) {
			salarioMenor = pessoa;
		} 
		if (salarioMaior == null
				|| pessoa.getValorSalario() > salarioMaior.getValorSalario()) {
			salarioMaior = pessoa;
		} 
	}
	}

	public Pessoa getSalarioMenor() {
		return salarioMenor;
	}

	public Pessoa getSalarioMaior() {
		return salarioMaior;
	}
}
