package br.ufop.trabalho.entities;

public class Filme {
    private String nome;
    private int ano;
    private String genero;
    private int quantidadeDVDs;
    private int quantidadeBluRays;
    private String tipo;

    public Filme(String nome, int ano, String genero, int quantidadeDVDs, int quantidadeBluRays, String tipo) {
        this.nome = nome;
        this.ano = ano;
        this.genero = genero;
        this.quantidadeDVDs = quantidadeDVDs;
        this.quantidadeBluRays = quantidadeBluRays;
        this.tipo = tipo;
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

    @Override
    public String toString() {
        return "Nome: " + nome + ", Ano: " + ano + ", Gênero: " + genero + 
                ", DVDs Disponíveis: " + quantidadeDVDs + 
                ", Blu-rays Disponíveis: " + quantidadeBluRays + 
                ", Tipo: " + tipo + 
                ", Valor do Aluguel: R$ " + getValorAluguel(); // Incluindo o valor do aluguel
    }

}
