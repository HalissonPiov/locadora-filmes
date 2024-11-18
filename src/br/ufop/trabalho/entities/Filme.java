package br.ufop.trabalho.entities;

import java.text.DecimalFormat;

public class Filme {
	private String nome;
	private int ano;
	private String genero;
	private int quantidadeDVDs;
	private int quantidadeBluRays;
	private static double avaliacao = 0;
	private static double quantAvaliacoes = 0;
	private static double somaAvaliacoesCadaCliente = 0;
	private String tipo;

	public Filme(String nome, int ano, String genero, int quantidadeDVDs, int quantidadeBluRays, String tipo) {
		this.nome = nome;
		this.ano = ano;
		this.genero = genero;
		this.quantidadeDVDs = quantidadeDVDs;
		this.quantidadeBluRays = quantidadeBluRays;
		this.tipo = tipo;
		DecimalFormat df = new DecimalFormat("#.##");
	}

	// Getters e Setters
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public int getQuantidadeDVDs() {
		return quantidadeDVDs;
	}

	public void setQuantidadeDVDs(int quantidadeDVDs) {
		this.quantidadeDVDs = quantidadeDVDs;
	}

	public int getQuantidadeBluRays() {
		return quantidadeBluRays;
	}

	public void setQuantidadeBluRays(int quantidadeBluRays) {
		this.quantidadeBluRays = quantidadeBluRays;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public static double getAvaliacao() {
		return Filme.avaliacao;
	}

	public static void setAvaliacao(double avaliacao) {
		quantAvaliacoes++;
		somaAvaliacoesCadaCliente += avaliacao;
		Filme.avaliacao = somaAvaliacoesCadaCliente / quantAvaliacoes;
	}

	public static double getSomaAvaliacoesClientes() {
		return somaAvaliacoesCadaCliente;
	}

	public static void setSomaAvaliacoesClientes(int somaAvaliacoesClientes) {
		Filme.somaAvaliacoesCadaCliente = somaAvaliacoesClientes;
	}

	// Método para obter o valor do aluguel
	public double getValorAluguel() {
		switch (tipo) {
		case "Lançamento":
			return 10.0;
		case "Novo":
			return 7.0;
		case "Antigo":
			return 5.0;
		default:
			return 0.0; // Valor padrão caso o tipo seja desconhecido
		}
	}

	public static double getQuantAvaliacoes() {
		return quantAvaliacoes;
	}

	public static void setQuantAvaliacoes(int quantAvaliacoes) {
		Filme.quantAvaliacoes = quantAvaliacoes;
	}

	@Override
	public String toString() {
		
		return "Nome: " + nome + ", Ano: " + ano + ", Gênero: " + genero + ", Avaliação por clientes da locadora: "
				+ String.format("%.1f", getAvaliacao()) + ", DVDs Disponíveis: " + quantidadeDVDs + ", Blu-rays Disponíveis: "
				+ quantidadeBluRays + ", Tipo: " + tipo + ", Valor do Aluguel: R$ " + getValorAluguel();
	}

}
