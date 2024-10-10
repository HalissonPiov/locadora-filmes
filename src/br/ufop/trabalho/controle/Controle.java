package br.ufop.trabalho.controle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor.INPUT_STREAM;

import br.ufop.trabalho.Util;
import br.ufop.trabalho.entities.Cliente;
import br.ufop.trabalho.entities.Data;
import br.ufop.trabalho.entities.Dependentes;
import br.ufop.trabalho.entities.Filme;
import br.ufop.trabalho.entities.FilmeAlugado;

/***
 * Classe de regra de negócios da aplicação. Esta classe deverá controlar todos
 * os dados que serão armazenados no sistema (clientes, filmes e a parte
 * financeira). Ela é a porta de entrada para acesso a todos os dados e deverá
 * também fazer as verificações necessárias no momento dos cadastros. É
 * necessário perceber a importância dessa classe que implementa todas as regras
 * de negócio da aplicação. A classe Controle poderá ser utilizada para qualquer
 * tipo de interface gráfica inclusive com janelas.
 * 
 * IMPORTANTE: ESTA CLASSE NAO DEVE TER ENTRADA E SAÍDA DE DADOS PARA O USUÁRIO
 * 
 * @author Filipe
 *
 */
public class Controle {
	// Array de clientes
	private ArrayList<Cliente> clientes;

	// Array de filmes
	private ArrayList<Filme> filmes;
	private ArrayList<FilmeAlugado> filmesAlugados = new ArrayList<>();

	private ArrayList<Saida> saida;
	private ArrayList<Entrada> entrada;

	
	public double calcularTotalEntradas() {
	    double total = 0.0;
	    for (Entrada entrada : entrada) {
	        total += entrada.getValor(); // Soma o valor de cada entrada
	    }
	    return total; // Retorna o total calculado
	}
	
	private void exibirSaldo(double totalEntradas, double totalSaidas, String periodo) {
	    double saldo = totalEntradas - totalSaidas;
	    System.out.printf("Total de Entradas (%s): R$ %.2f\n", periodo, totalEntradas);
	    System.out.printf("Total de Saídas (%s): R$ %.2f\n", periodo, totalSaidas);
	    System.out.printf("Saldo (%s): R$ %.2f\n", periodo, saldo);
	}
	
	
	
	// ---
	public static int quantTotalDVD = 0; // var estática: pertence à classe e não a uma instância dela
	public static int quantTotalBluRays = 0;
	public static int valorTotalMulta = 0;
	public static double aluguelImovel = 950;
	public static double salarioTotalFuncionarios = 3000;
	public static double valorDVD = 1.50;
	public static double valorBluRay = 2.50;

	// ---

	public Controle() {
		clientes = new ArrayList<Cliente>();
		filmes = new ArrayList<Filme>();
		saida = new ArrayList<Saida>();
		entrada = new ArrayList<Entrada>();
		// Inicialização de outros arrays e dados

	}

	// Métodos para clientes
	public int addCliente(String nome, String end, int codigo, String cpf, Data dataNascimento) {
		if (!Util.verificaListaStringPreenchida(nome, end, cpf)) {
			return Constantes.ERRO_CAMPO_VAZIO;
		}

		Cliente cliente = new Cliente(nome, end, codigo, cpf, dataNascimento);
		this.clientes.add(cliente);

		return Constantes.RESULT_OK;
	}

	public boolean verificarQuantDepend(String cpfCliente) {

		for (Cliente cliente : clientes) {
			if (cliente.getCpf().equals(cpfCliente) && cliente.getQuantDependentes() == 3) {
				return false;
			}
		}
		return true;
	}

	public int getQtdClientes() {
		return clientes.size();
	}

	public ArrayList<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(ArrayList<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Cliente getClienteNaPosicao(int pos) {
		if (pos >= 0 && pos < getQtdClientes()) {
			return clientes.get(pos);
		}
		return null;
	}

	public int getPosicaoCliente(Cliente cliente) {
		for (int i = 0; i < clientes.size(); i++) {
			if (cliente.getCpf() == getClienteNaPosicao(i).getCpf()
					&& cliente.getNome() == getClienteNaPosicao(i).getNome()) {
				return i;
			}
		}
		return 0;
	}

	public void relatorioClientes() {
		System.out.println("|------- Relatório de clientes cadastrados -------|");
		clientes.forEach(elemento -> System.out.println(elemento));
		System.out.println("|-------------------------------------------------|");
	}

	public void relatorioFilmesPorGenero() {

		System.out.println("|------- Relatório de filmes por gênero -------|");
		System.out.println("Comédia: ");
		buscarFilmesPorGenero("Comédia").forEach(elemento -> System.out.println(elemento));
		System.out.println("\n");
		System.out.println("Terror: ");
		buscarFilmesPorGenero("Terror").forEach(elemento -> System.out.println(elemento));
		System.out.println("\n");
		System.out.println("Animação: ");
		buscarFilmesPorGenero("Animação").forEach(elemento -> System.out.println(elemento));
		System.out.println("\n");
		System.out.println("Romance: ");
		buscarFilmesPorGenero("Romance").forEach(elemento -> System.out.println(elemento));
		System.out.println("\n");
		System.out.println("Ação: ");
		buscarFilmesPorGenero("Ação").forEach(elemento -> System.out.println(elemento));
		System.out.println("\n");
		System.out.println("Aventura: ");
		buscarFilmesPorGenero("Aventura").forEach(elemento -> System.out.println(elemento));
		System.out.println("\n");
		System.out.println("Outros: ");
		buscarFilmesPorGenero("Outros").forEach(elemento -> System.out.println(elemento));
		System.out.println("\n");
		System.out.println("|-----------------------------------------------|");
	}

	public void relatorioFilmesPorAnoLanc() {
		Map<Integer, List<Filme>> filmesPorAno = new HashMap<>(); // agrupa filmes por ano de lançamento. Map: estrutura
																	// que armazena pares de chave e valor. Integer: o
																	// ano de lançamento. HashMap: criaum mapa vazio que
																	// será preenchido com os anos e lista de filmes
		for (Filme filme : filmes) {
			int ano = filme.getAno();
			filmesPorAno.putIfAbsent(ano, new ArrayList<>()); // inicializa a lista se ela não existir
			filmesPorAno.get(ano).add(filme); // Adiciona o filme na respectiva lista por ano
		}

		System.out.println("|------- Relatório de filmes por ano de lançamento -------|");
		for (Map.Entry<Integer, List<Filme>> entry : filmesPorAno.entrySet()) { // Map.Entry: interface que representa
																				// um par (chave-valor: o ano e a lista
																				// de filmes) no mapa. EntrySet: retorna
																				// um conjunto de todas as entradas no
																				// mapa
			int ano = entry.getKey(); // obtém a chave de entrada atual (ano de lançamento)
			List<Filme> filmesDoAno = entry.getValue(); // obtém o elemento (filme) do arraylist no loop

			System.out.println("Ano: " + ano);
			for (Filme filme : filmesDoAno) {
				System.out.println("\t" + filme.toString());
			}
		}
	}

	public void relatorioFilmesNomeEmOrdem() {

		filmes.sort((filme1, filme2) -> filme1.getNome().compareToIgnoreCase(filme2.getNome()));
		filmes.forEach(filme -> System.out.println(filme.toString()));
	}

	public List<Cliente> encontrarCliente(int opcao, String nomeCliente, int codigo, String nomeDependente) {
		ArrayList<Cliente> retorno = new ArrayList<>();
		for (Cliente c : clientes) {
			if (opcao == 1) {
				if (c.getNome().equalsIgnoreCase(nomeCliente)) {
					retorno.add(c);
				}
			} else if (opcao == 2) {
				if (c.getCodigo() == codigo) {
					retorno.add(c);
				}
			} else if (opcao == 3) {
				String result = c.pesquisarNomeDependente(nomeDependente);
				if (result != "") {
					retorno.add(c);
				}
			}
		}
		return retorno;
	}

	public ArrayList<Cliente> buscarClienteComMovim(String nome) {

		ArrayList<Cliente> listaRetorno = new ArrayList<>();

		for (Cliente c : clientes) {
			if (c.getNome().equals(nome) && !c.getMovimentacoes().isEmpty()) { // verificar se está funcionando
				listaRetorno.add(c);
			}
		}
		return listaRetorno;
	}

	// Métodos para filmes
	public int addFilme(Filme filme) {
		filmes.add(filme);
		return Constantes.RESULT_OK;
	}

	public List<Filme> buscaFilmes(String nome, String genero) {
		List<Filme> resultados = new ArrayList<>();
		for (Filme filme : filmes) {
			boolean nomeCorreto = nome.isEmpty() || filme.getNome().equalsIgnoreCase(nome);
			boolean generoCorreto = genero.isEmpty() || filme.getGenero().equalsIgnoreCase(genero);
			if (nomeCorreto && generoCorreto && (filme.getQuantidadeDVDs() > 0 || filme.getQuantidadeBluRays() > 0)) {
				resultados.add(filme);
			}
		}
		return resultados;
	}

	public ArrayList<Filme> buscarFilmesPorGenero(String genero) {
		ArrayList<Filme> resultados = new ArrayList<>();
		for (Filme filme : filmes) {
			if (filme.getGenero().equalsIgnoreCase(genero)) {
				resultados.add(filme);
			}
		}
		return resultados;
	}

	public ArrayList<Filme> buscarFilmesPorDisponibilidade() {
		ArrayList<Filme> resultados = new ArrayList<>();
		for (Filme filme : filmes) {
			if (filme.getQuantidadeDVDs() > 0 || filme.getQuantidadeBluRays() > 0) {
				resultados.add(filme);
			}
		}
		return resultados;
	}

	public void excluirFilme(Filme filme) {
		filmes.remove(filme);
	}

	// metodo buscar filmes por nome

	public ArrayList<Filme> buscarFilmesPorNome(String nome) {
		ArrayList<Filme> resultados = new ArrayList<>();
		for (Filme filme : filmes) {
			if (filme.getNome().equalsIgnoreCase(nome)) {
				resultados.add(filme);
			}
		}
		return resultados;
	}

	public Cliente buscarClientePorCpf(String cpfCliente) {

		for (Cliente cliente : clientes) {
			if (cliente.getCpf().equals(cpfCliente)) {
				return cliente;
			}
		}
		return null;
	}

	// Método para locar filme
	public boolean locarFilmeParaCliente(Filme filme, Cliente cliente) {
		// Verifica se há DVDs ou Blu-rays disponíveis
		if (filme.getQuantidadeDVDs() > 0 || filme.getQuantidadeBluRays() > 0) {
			// Lógica para locar o filme ao cliente (diminuir a quantidade disponível)
			if (filme.getQuantidadeDVDs() > 0) {
				filme.setQuantidadeDVDs(filme.getQuantidadeDVDs() - 0); // Diminui a quantidade de DVDs
			} else {
				filme.setQuantidadeBluRays(filme.getQuantidadeBluRays() - 0); // Diminui a quantidade de Blu-rays
			}

			// Adiciona o filme alugado à lista de filmes alugados
			FilmeAlugado filmeAlugado = new FilmeAlugado(filme, cliente);
			filmesAlugados.add(filmeAlugado);

			// Criação da entrada financeira com valor dinâmico
			double valorLocacao = filme.getValorAluguel(); // Obtém o valor do aluguel baseado no tipo do filme
			Entrada entrada = new Entrada("Locação de Filme",
					"Cliente: " + cliente.getNome() + " - Filme: " + filme.getNome(), valorLocacao, LocalDate.now());

			// Adiciona a entrada ao controle
			cliente.adicionarEntrada(entrada);

			// Exibe as informações da entrada
			System.out.println(entrada); // Isso irá usar o método toString() da classe Entrada

			return true; // Indica que a locação foi bem-sucedida
		}
		System.out.println("Não há filmes disponíveis para locação."); // Mensagem caso não haja filmes disponíveis
		return false; // Indica que não havia filmes disponíveis para locação
	}

	public int editarFilme(Filme filme, String novoGenero, String novoTipo, int novaQtdDVDs, int novaQtdBluRays) {
		if (filme == null) {
			return Constantes.ERRO_FILME_NAO_ENCONTRADO;
		}

		if (!novoGenero.isEmpty()) {
			filme.setGenero(novoGenero);
		}
		if (!novoTipo.isEmpty()) {
			filme.setTipo(novoTipo); // Certifique-se de que o tipo é validado
		}
		if (novaQtdDVDs >= 0) {
			filme.setQuantidadeDVDs(novaQtdDVDs);
		}
		if (novaQtdBluRays >= 0) {
			filme.setQuantidadeBluRays(novaQtdBluRays);
		}

		// Outras validações podem ser necessárias

		return Constantes.RESULT_OK;
	}

	public ArrayList<FilmeAlugado> getFilmesAlugados() {
		return filmesAlugados;
	}

	public double calcularMulta(int dias) {

		double valorMulta = 2 * dias;

		return valorMulta;

	}

	public int quantDVDsComprados(int quantDVD) {

		quantTotalDVD += quantDVD;

		return quantTotalDVD;
	}

	public int quantBluRaysComprados(int quantBluRay) {

		quantTotalBluRays += quantBluRay;

		return quantTotalBluRays;
	}

	public double somaMultas(double valor) {

		valorTotalMulta += valor;

		return valorTotalMulta;
	}

	public void adicionarSaida(Saida saida) {
		this.saida.add(saida);
	}

	public void adicionarEntrada(Entrada entrada) {
		this.entrada.add(entrada);
	}

	public static double getAluguelImovel() {
		return aluguelImovel;
	}

	public static void setAluguelImovel(double aluguelImovel) {
		Controle.aluguelImovel = aluguelImovel;
	}

	public static double getSalarioTotalFuncionarios() {
		return salarioTotalFuncionarios;
	}

	public static void setSalarioTotalFuncionarios(double salarioTotalFuncionarios) {
		Controle.salarioTotalFuncionarios = salarioTotalFuncionarios;
	}

	public static double getValorDVD() {
		return valorDVD;
	}

	public static void setValorDVD(double valorDVD) {
		Controle.valorDVD = valorDVD;
	}

	public static double getValorBluRay() {
		return valorBluRay;
	}

	public static void setValorBluRay(double valorBluRay) {
		Controle.valorBluRay = valorBluRay;
	}

	public ArrayList<Entrada> getEntradas() {
	    return entrada; // Retorna o ArrayList de entradas
	}

	public ArrayList<Saida> getSaidas() {
	    return saida; // Retorna o ArrayList de saídas
	}
}