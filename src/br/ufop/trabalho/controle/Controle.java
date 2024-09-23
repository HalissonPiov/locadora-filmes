package br.ufop.trabalho.controle;

import java.util.ArrayList;
import java.util.List;

import br.ufop.trabalho.Util;
import br.ufop.trabalho.entities.Cliente;
import br.ufop.trabalho.entities.Data;
import br.ufop.trabalho.entities.Filme;

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

	public Controle() {
		clientes = new ArrayList<Cliente>();
		filmes = new ArrayList<Filme>();
		// Entre as "setas" estava vazio. Deveria ser isso mesmo??
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

		int contagem = 0;

		for (Cliente cliente : clientes) {
			if (cliente.getCpf().equals(cpfCliente) || cliente.getQuantDependentes() == 1) {
				contagem++;
			}
		}
		if (contagem == 3) {
			return false;
		}
		return true;
	}

	public int getQtdClientes() {
		return clientes.size();
	}

	public Cliente getClienteNaPosicao(int pos) {
		if (pos >= 0 && pos < getQtdClientes()) {
			return clientes.get(pos);
		}
		return null;
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

	public boolean locarFilmeParaCliente(Filme filme, Cliente cliente) {
		if (filme.getQuantidadeDVDs() > 0 || filme.getQuantidadeBluRays() > 0) {
			// Lógica para locar o filme ao cliente (por exemplo, diminuir a quantidade
			// disponível)
			if (filme.getQuantidadeDVDs() > 0) {
				filme.setQuantidadeDVDs(filme.getQuantidadeDVDs() - 1);
			} else {
				filme.setQuantidadeBluRays(filme.getQuantidadeBluRays() - 1);
			}
			return true;
		}
		return false;
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
}