package br.com.nex.bean;

import java.util.ArrayList;
import java.util.List;

public class GrupoPessoas {
	
	List<Pessoa> pessoas = new ArrayList<Pessoa>();
	
	public void add(Pessoa pessoa) {
        pessoas.add(pessoa);
    }

}
