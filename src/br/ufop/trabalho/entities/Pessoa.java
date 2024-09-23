package br.ufop.trabalho.entities;

/**
 * Classe para armazenar os dados de uma Pessoa. Como o sistema deverá controlar
 * clientes e funcionários os dados comuns serão armazenadona superClassePessoa.
 * 
 * @author Filipe
 *
 */
public class Pessoa {

	private String nome, endereco;
	private String cpf;
	private Data dataNascimento;

	public Pessoa(String nome, String endereco, String cpf, Data dataNascimento) {
		this.nome = nome;
		this.endereco = endereco;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
	}

	/**
	 * COnstrutor vazio. Criado apenas para a geração do código inicial DE EXEMPLO
	 * pelo professor. NÃO DEVERÁ SER UTILIZADO
	 */
	public Pessoa() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Data getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Data dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

}
