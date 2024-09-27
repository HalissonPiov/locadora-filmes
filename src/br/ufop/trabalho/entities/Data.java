package br.ufop.trabalho.entities;

// Fig. 8.7: Date.java -- Adaptado
// Declara��o da classe Date.
public class Data {
	private int mes; // 1-12
	private int dia; // 1-31 conforme o m�s
	private int ano; // qualquer ano

	// construtor: chama checarMes para confirmar o valor adequado para mes;
	// chama checarDia para confirmar o valor adequado para dia
	public Data(int d, int m, int a) {
		setMes(m);
		setDia(d);
		setAno(a);
	}

	/**
	 * M�todo para validar o m�s
	 * 
	 * @param mesTeste
	 * @return
	 */
	private int checarMes(int mesTeste) {
		if (mesTeste > 0 && mesTeste <= 12)
			return mesTeste;
		else {
			System.out.printf("Mes invalido (%d) alterado para 1.", mesTeste);
			return 1;
		}
	}

	/**
	 * M�todo para validar o dia
	 * 
	 * @param diaTeste
	 * @return
	 */
	private int checarDia(int diaTeste) {
		int diasNoMes[] = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		// verifica se dia est� no intervalo para o mes
		if (diaTeste > 0 && diaTeste <= diasNoMes[mes])
			return diaTeste;
		// verifica ano bissexto
		if (mes == 2 && diaTeste == 29 && (ano % 400 == 0 || (ano % 4 == 0 && ano % 100 != 0)))
			return diaTeste;
		System.out.printf("Dia invalido (%d) alterado para 1.", diaTeste);
		return 1;
	}

	// retorna uma String no formato m�s/dia/ano
	public String toString() {
		return String.format("%d/%d/%d", mes, dia, ano);
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = checarMes(mes);
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = checarDia(dia);
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}
}
