package br.ufop.trabalho.controle;

import java.time.LocalDate;

public class Saida {
	private String nome;
	private String descricao;
	private double valor;
	private LocalDate dataSaida;

	public Saida(String nome, String descricao, double valor, LocalDate dataSaida) {
		this.nome = nome;
		this.descricao = descricao;
		this.valor = valor;
		this.dataSaida = dataSaida;
	}

	// Getters e Setters
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public LocalDate getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(LocalDate dataSaida) {
		this.dataSaida = dataSaida;
	}

	@Override
	public String toString() {
		return "Saída registrada:\n" + "Nome: " + nome + "\n" + "Descrição - " + descricao + "\n" + "Valor: R$ "
				+ String.format("%.2f", valor) + "\n" + "Data: " + dataSaida;
	}

}
