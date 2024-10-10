package br.ufop.trabalho.controle;

import java.time.LocalDate;

public class Entrada {
	private String nome;
	private String descricao;
	private double valor;
	private LocalDate dataEntrada;

	public Entrada(String nome, String descricao, double valor, LocalDate dataEntrada) {
		this.nome = nome;
		this.descricao = descricao;
		this.valor = valor;
		this.dataEntrada = dataEntrada;
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

	public LocalDate getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(LocalDate dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	@Override
	public String toString() {
		return "Entrada registrada:\n" + "Nome: " + nome + "\n" + "Descrição - " + descricao + "\n" + "Valor: R$ "
				+ String.format("%.2f", valor) + "\n" + "Data: " + dataEntrada;
	}

}
