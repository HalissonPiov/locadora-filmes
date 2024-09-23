package br.ufop.trabalho.entities;

import java.util.ArrayList;

import br.ufop.trabalho.Util;
import br.ufop.trabalho.controle.Constantes;
import br.ufop.trabalho.controle.Controle;

public class Cliente extends Pessoa {

	private int codigo;
	private double multa;
	private int quantDependentes;
	// Um cliente terá um array de filmes que armazenarão os filmes por ele locados
	private ArrayList<Filme> filmes;
	private ArrayList<Dependentes> dependentes;
	private Controle controle;

	// Um cliente também terá um array de dependentes
//	private ArrayList<Dependente> dependentes;

	/**
	 * Construtor
	 * 
	 * @param nome
	 * @param endereco
	 * @param codigo
	 */
	public Cliente(String nome, String endereco, int codigo, String cpf, Data dataNascimento) {
		super(nome, endereco, cpf, dataNascimento);
		this.codigo = codigo;
		// Instanciação do Array List de filmes
		filmes = new ArrayList<Filme>();
		dependentes = new ArrayList<Dependentes>();
	}

	public int addDependentes(String nome, String end, String cpf, Data dataNascimento, String cpfCliente) {
		
		if (!Util.verificaListaStringPreenchida(nome, end, cpf)) {
			return Constantes.ERRO_CAMPO_VAZIO;
		}
		
		// Buscar no arraylist de clientes, um CPF que tenha a quantidade 3 como valor da var quantDependentes
		boolean retorno = controle.verificarQuantDepend(cpfCliente);
		
		if (retorno == false) {
			return 0;
		}
		
		Dependentes dependente = new Dependentes(nome, end, cpf, dataNascimento);
		this.dependentes.add(dependente);
		quantDependentes++;

		return Constantes.RESULT_OK;
	}

	public int getQuantDependentes() {
		return quantDependentes;
	}

	public void setQuantDependentes(int quantDependentes) {
		this.quantDependentes = quantDependentes;
	}

	/**
	 * COnstrutor vazio criado apenas para a geração do código inicial DE EXEMPLO
	 * pelo professor. Pode ser utilizado mas é importante lembrar de preencher os
	 * campos do objeto para que não ocorra o NullPointerException
	 */
	public Cliente() {
		dependentes = new ArrayList<Dependentes>();
		Controle controle = new Controle();
		this.controle = controle;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public ArrayList<Filme> getFilmes() {
		return filmes;
	}

	public void setFilmes(ArrayList<Filme> filmes) {
		this.filmes = filmes;
	}

	@Override
	public String toString() {
		return getNome();
	}

}
