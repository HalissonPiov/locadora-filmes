package br.ufop.trabalho.controle;

import java.util.ArrayList;
import java.util.List;

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
		System.out.println("Não foi encontrado");
		return null;
	}

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
			filmesAlugados.add(filmeAlugado); // Supondo que você tenha a lista filmesAlugados

			return true; // Indica que a locação foi bem-sucedida
		}
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
}