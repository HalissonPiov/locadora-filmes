package br.ufop.trabalho.entities;

public class Dependentes extends Pessoa {

	public Dependentes(String nome, String endereco, String cpf, Data dataNascimento) {
		super(nome, endereco, cpf, dataNascimento);
	}

	@Override
	public String toString() {
		return " [Nome=" + getNome() + ", Endereco=" + getEndereco() + ", CPF=" + getCpf()
				+ ", Data de nascimento=" + getDataNascimento() + "]";
	}
	
	
	
}
