package br.ufop.trabalho.entities;

import java.util.ArrayList;

import br.ufop.trabalho.Util;
import br.ufop.trabalho.IOConsole.MenuClienteConsole;
import br.ufop.trabalho.controle.Constantes;
import br.ufop.trabalho.controle.Controle;
import br.ufop.trabalho.controle.Entrada;

public class Cliente extends Pessoa {

	private int codigo;
	private double multa;
	private int quantDependentes;
	private ArrayList<Entrada> entradas;
	// Um cliente terá um array de filmes que armazenarão os filmes por ele locados
	private ArrayList<Filme> filmes;
	private ArrayList<Dependentes> dependentes;
	private Controle controle;

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
		Controle controle = new Controle();
		this.controle = controle;
		this.entradas = new ArrayList<Entrada>();
	}

	public int addDependentes(String nome, String end, String cpf, Data dataNascimento, Cliente clienteComDepen) {

		if (!Util.verificaListaStringPreenchida(nome, end, cpf)) {
			return Constantes.ERRO_CAMPO_VAZIO;
		}

		Dependentes dependente = new Dependentes(nome, end, cpf, dataNascimento);
		clienteComDepen.getDependentes().add(dependente);
		clienteComDepen.incrementarDependente(clienteComDepen);

		return Constantes.RESULT_OK;
	}

	public String pesquisarNomeDependente(String nome) {

		int i = 0;

		for (Dependentes d : dependentes) {
			if (this.dependentes.get(i).getNome().equalsIgnoreCase(nome)) {
				return this.dependentes.get(i).getNome();
			}
			i++;
		}
		return "";
	}

	public ArrayList<Dependentes> getDependentes() {
		return dependentes;
	}

	public void setDependentes(ArrayList<Dependentes> dependentes) {
		this.dependentes = dependentes;
	}

	public int getQuantDependentes() {
		return quantDependentes;
	}

	public void setQuantDependentes(int quantDependentes) {
		this.quantDependentes = quantDependentes;
	}

	public double getMulta() {
		return multa;
	}

	public void setMulta(double multa) {
		this.multa = multa;
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
		this.entradas = new ArrayList<Entrada>();
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

	public void incrementarDependente(Cliente c) {
		c.setQuantDependentes(c.getQuantDependentes() + 1);
	}

	public ArrayList<Entrada> getMovimentacoes() {
		return entradas;
	}

	public void setMovimentacoes(ArrayList<Entrada> entradas) {
		this.entradas = entradas;
	}

	public void adicionarEntrada(Entrada entrada) {
		this.entradas.add(entrada);
	}
	
	public void excluirEntrada(Entrada entrada, int indice) {
		this.entradas.remove(indice);
	}

//	public ArrayList<Integer> movimentacao(int movimentacao) {
//
////		ArrayList<Integer> movimentacoes = new ArrayList<>();
//
//		this.movimentacoes.add(movimentacao);
//
//		return movimentacoes;
//	}

	public ArrayList<Entrada> getEntradas() {
		return entradas;
	}

	public void setEntradas(ArrayList<Entrada> entradas) {
		this.entradas = entradas;
	}

	public String toString() {
		StringBuilder info = new StringBuilder();
		info.append("Cliente: ").append(super.getNome()).append("\n");
		info.append("CPF: ").append(super.getCpf()).append("\n");
		info.append("Endereço: ").append(super.getEndereco()).append("\n");
		info.append("Código: ").append(this.codigo).append("\n");
		info.append("Data de Nascimento: ").append(this.getDataNascimento()).append("\n");
		info.append("Multa: ").append(this.multa).append("\n");
		info.append("Dependentes: ");

		if (dependentes.isEmpty()) {
			info.append("Nenhum dependente cadastrado.\n");
		} else {
			for (Dependentes dep : dependentes) {
				info.append(dep.toString()).append("\n");
			}
		}

		info.append("Filmes alugados: ");

		if (filmes.isEmpty()) {
			info.append("Nenhum filme alugado.\n");
		} else {
			for (Filme filme : filmes) {
				info.append(filme.toString()).append("\n");
			}
		}

		return info.toString();
	}

}
