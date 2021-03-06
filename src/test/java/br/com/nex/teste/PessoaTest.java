package br.com.nex.teste;

import org.junit.Assert;
import org.junit.Test;

import br.com.nex.bean.GrupoPessoas;
import br.com.nex.bean.MenorEMaiorSalario;
import br.com.nex.bean.Pessoa;

public class PessoaTest {

	@Test
	public void menorEMaiorSalario() {
		
		GrupoPessoas grupoPessoas = new GrupoPessoas();
		grupoPessoas.add(new Pessoa(1, "Vanessa", "123.123.123-12", 12.0));
		grupoPessoas.add(new Pessoa(2, "Vanessa2", "123.123.123-12", 13.0));
		grupoPessoas.add(new Pessoa(3, "Vanessa3", "123.123.123-12", 14.0));
		
		
		MenorEMaiorSalario comparar = new MenorEMaiorSalario();
		comparar.encontra(grupoPessoas);
		
		Assert.assertEquals("Vanessa", comparar.getSalarioMenor().getNome());
		Assert.assertEquals("Vanessa3", comparar.getSalarioMaior().getNome());
		
			
		
		
		
	}
}
