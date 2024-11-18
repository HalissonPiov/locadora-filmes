package br.ufop.trabalho.IOConsole;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import br.ufop.trabalho.controle.Constantes;
import br.ufop.trabalho.Util;
import br.ufop.trabalho.controle.Controle;
import br.ufop.trabalho.controle.Entrada;
import br.ufop.trabalho.entities.Cliente;
import br.ufop.trabalho.entities.Filme;
import br.ufop.trabalho.entities.FilmeAlugado;

public class MenuFilmesConsole {

	private Controle controle;
	private Scanner input;

	public MenuFilmesConsole(Controle controle, Scanner input) {
		this.controle = controle;
		this.input = input;
	}

	public void exibeMenuFilmes() {
		boolean continua = true;
		do {
			System.out.println(
					"Digite a opção desejada:\n\t[1] - Cadastro de Filme\n\t[2] - Busca de Filme\n\t[3] - Voltar ao Menu Principal");
			int op = Util.leInteiroConsole(input);
			switch (op) {
			case 1:
				cadastrarFilme();
				break;
			case 2:
				buscarFilme();
				break;
			case 3:
				continua = false;
				break;
			default:
				System.out.println("Opção inválida.");
			}
		} while (continua);
	}

	private void cadastrarFilme() {
		System.out.println("Cadastro de Filme");

		System.out.print("Digite o nome do filme: ");
		String nome = input.next();

		ArrayList<Filme> retorno = controle.buscarFilmesPorNome(nome);
		if (!retorno.isEmpty()) {
			System.out.println("Não é possível cadastrar filmes de mesmo nome.");
			return;
		}

		System.out.print("Digite o ano de lançamento: ");
		int ano = Util.leInteiroConsole(input);

		// Validação do ano
		while (ano < 1900 || ano > 2024) {
			System.out.println("Ano inválido. Por favor, digite um ano entre 1900 e 2024:");
			ano = Util.leInteiroConsole(input);
		}

		System.out.println("Escolha o gênero do filme:");
		System.out.println("1 - Comédia");
		System.out.println("2 - Terror");
		System.out.println("3 - Animação");
		System.out.println("4 - Romance");
		System.out.println("5 - Ação");
		System.out.println("6 - Aventura");
		System.out.println("7 - Outros");
		int generoEscolhido = Util.leInteiroConsole(input);

		String genero = "";
		switch (generoEscolhido) {
		case 1:
			genero = "Comédia";
			break;
		case 2:
			genero = "Terror";
			break;
		case 3:
			genero = "Animação";
			break;
		case 4:
			genero = "Romance";
			break;
		case 5:
			genero = "Ação";
			break;
		case 6:
			genero = "Aventura";
			break;
		case 7:
			genero = "Outros";
			break;
		default:
			System.out.println("Gênero inválido. Definindo como 'Outros'.");
			genero = "Outros";
			break;
		}

		String tipo = "";
		boolean tipoValido = false;

		while (!tipoValido) {

			System.out.println("Escolha o tipo do filme:");
			System.out.println("Apenas as seguintes condições são válidas:");
			System.out.println("1 - Lançamento (somente para filmes de 2024)");
			System.out.println("2 - Novo (apenas entre 2013 e 2023)");
			System.out.println("3 - Antigo (filmes de 2012 ou anteriores)");
			int tipoEscolhido = Util.leInteiroConsole(input);

			switch (tipoEscolhido) {
			case 1:
				if (ano == 2024) {
					tipo = "Lançamento";
					tipoValido = true;
				} else {
					System.out.println("Tipo inválido. Filmes de lançamento só podem ser de 2024.");
				}
				break;
			case 2:
				if (ano >= 2013 && ano <= 2023) {
					tipo = "Novo";
					tipoValido = true;
				} else {
					System.out.println("Tipo inválido. Filmes novos devem ser de 2013 a 2023.");
				}
				break;
			case 3:
				if (ano <= 2012) {
					tipo = "Antigo";
					tipoValido = true;
				} else {
					System.out.println("Tipo inválido. Filmes antigos devem ser de 2012 ou anteriores.");
				}
				break;
			default:
				System.out.println("Tipo inválido. Por favor, escolha uma opção válida.");
				break;
			}
		}

		System.out.println("Tipo definido: " + tipo);

		System.out.print("Digite a quantidade de DVDs: ");
		int quantidadeDVDs = Util.leInteiroConsole(input);
		if (quantidadeDVDs > controle.quantDVDsComprados(0)) {
			System.out.println("A locadora não possui essa quantidade de DVDs para cadastrar");
			return;
		}

		System.out.print("Digite a quantidade de Blu-rays: ");
		int quantidadeBluRays = Util.leInteiroConsole(input);
		if (quantidadeBluRays > controle.quantBluRaysComprados(0)) {
			System.out.println("A locadora não possui essa quantidade de blu-rays para cadastrar");
			return;
		}

		Filme filme = new Filme(nome, ano, genero, quantidadeDVDs, quantidadeBluRays, tipo);
		int resultado = controle.addFilme(filme);

		if (resultado == Constantes.RESULT_OK) {
			System.out.println("Filme cadastrado com sucesso.");
		} else {
			System.out.println("Erro ao cadastrar filme.");
		}
	}

	private void buscarFilme() {
		System.out.println("Buscar Filme");

		System.out.println("Escolha a forma de busca:");
		System.out.println("1 - Por Nome");
		System.out.println("2 - Por Gênero");
		System.out.println("3 - Por Disponibilidade");
		int opcaoBusca = Util.leInteiroConsole(input);

		ArrayList<Filme> resultados = new ArrayList<>();

		switch (opcaoBusca) {
		case 1:
			System.out.print("Digite o nome exato do filme: ");
			String nome = input.next();
			resultados = controle.buscarFilmesPorNome(nome);
			break;
		case 2:
			System.out.println("Escolha o gênero:");
			System.out.println("1 - Comédia");
			System.out.println("2 - Terror");
			System.out.println("3 - Animação");
			System.out.println("4 - Romance");
			System.out.println("5 - Ação");
			System.out.println("6 - Aventura");
			System.out.println("7 - Outros");
			int generoEscolhido = Util.leInteiroConsole(input);

			String genero = "";
			switch (generoEscolhido) {
			case 1:
				genero = "Comédia";
				break;
			case 2:
				genero = "Terror";
				break;
			case 3:
				genero = "Animação";
				break;
			case 4:
				genero = "Romance";
				break;
			case 5:
				genero = "Ação";
				break;
			case 6:
				genero = "Aventura";
				break;
			case 7:
				genero = "Outros";
				break;
			default:
				System.out.println("Gênero inválido.");
				return;
			}
			resultados = (ArrayList<Filme>) controle.buscarFilmesPorGenero(genero);
			break;
		case 3:
			resultados = (ArrayList<Filme>) controle.buscarFilmesPorDisponibilidade();
			break;
		default:
			System.out.println("Opção de busca inválida.");
			return;
		}

		if (resultados.isEmpty()) {
			System.out.println("Nenhum filme encontrado.");
		} else {
			for (int i = 0; i < resultados.size(); i++) {
				System.out.println((i + 1) + " - " + resultados.get(i));
			}
			System.out.print("Escolha o número do filme para ver detalhes ou 0 para voltar: ");
			int escolha = Util.leInteiroConsole(input);
			if (escolha > 0 && escolha <= resultados.size()) {
				Filme filmeSelecionado = resultados.get(escolha - 1);
				System.out.println("Detalhes do Filme:\n" + filmeSelecionado);
				exibeOpcoesFilme(filmeSelecionado);
			}
		}
	}

	private void exibeOpcoesFilme(Filme filme) {
		System.out.println("Escolha uma opção:");
		System.out.println("[1] - Editar");
		System.out.println("[2] - Excluir");
		System.out.println("[3] - Locar para cliente");
		System.out.println("[4] - Lista de alocação");
		int opcao = Util.leInteiroConsole(input);
		switch (opcao) {
		case 1:
			editarFilme(filme);
			break;
		case 2:
			excluirFilme(filme);
			break;
		case 3:
			locarFilme(filme);
			break;
		case 4:
			verFilmesAlugados();
			break;
		default:
			System.out.println("Opção inválida.");
			break;
		}
	}

	private void locarFilme(Filme filme) {

		Scanner scanner = new Scanner(System.in);
		System.out.print("Digite o nome do cliente: ");
		String nomeCliente = scanner.nextLine();
		System.out.print("Digite o CPF do cliente: ");
		String cpfCliente = scanner.nextLine();

		// Verifica se o cliente existe
		Cliente cliente = null;
		for (int i = 0; i < controle.getQtdClientes(); i++) {
			Cliente temp = controle.getClienteNaPosicao(i);
			if (temp.getNome().equalsIgnoreCase(nomeCliente) && temp.getCpf().equals(cpfCliente)) {
				cliente = temp;
				break;
			}
		}

		if (cliente == null) {
			System.out.println("Cliente não encontrado ou nome/CPF incorretos.");
			return;
		}

		if (cliente.getFilmes().size() == 5) {
			System.out.println(
					"O cliente atingiu a quantidade máxima de 5 filmes alugados por cliente. Devolta um filme para poder locar um outro.");
			return;
		}

		// Informações sobre o filme que será locado
		System.out.println("Filme escolhido: " + filme.getNome());
		System.out.println("Escolha o formato para locação:");
		System.out.println("1 - DVD (Disponíveis: " + filme.getQuantidadeDVDs() + ")");
		System.out.println("2 - Blu-ray (Disponíveis: " + filme.getQuantidadeBluRays() + ")");

		int formatoEscolhido = Util.leInteiroConsole(scanner);

		// Verifica a escolha do formato e realiza a locação
		boolean locacaoBemSucedida = false;
		if (formatoEscolhido == 1 && filme.getQuantidadeDVDs() > 0) {
			locacaoBemSucedida = controle.locarFilmeParaCliente(filme, cliente);
			filme.setQuantidadeDVDs(filme.getQuantidadeDVDs() - 1); // Diminui a quantidade de DVDs
		} else if (formatoEscolhido == 2 && filme.getQuantidadeBluRays() > 0) {
			locacaoBemSucedida = controle.locarFilmeParaCliente(filme, cliente);
			filme.setQuantidadeBluRays(filme.getQuantidadeBluRays() - 1); // Diminui a quantidade de Blu-rays
		} else {
			System.out.println("Formato inválido ou não disponível para locação.");
			return;
		}

		cliente.getFilmes().add(filme);

		Entrada entradaLoca = new Entrada("Locação de filme",
				"Cliente: " + cliente.getNome() + " - Filme: " + filme.getNome(), filme.getValorAluguel(),
				LocalDate.now());
		cliente.adicionarEntrada(entradaLoca);

		if (locacaoBemSucedida) {
			System.out.println("Filme locado com sucesso!");
		} else {
			System.out.println("Erro ao locar o filme.");
		}
	}

	private void verFilmesAlugados() {

		ArrayList<FilmeAlugado> filmesAlugados = controle.getFilmesAlugados();

		if (filmesAlugados.isEmpty()) {
			System.out.println("Nenhum filme alugado no momento.");
		} else {
			System.out.println("Filmes Alugados:");
			for (FilmeAlugado filmeAlugado : filmesAlugados) {
				System.out.println("Filme: " + filmeAlugado.getFilme().getNome() + ", Alugado por: "
						+ filmeAlugado.getCliente().getNome());
			}
		}
	}

	private void editarFilme(Filme filme) {

		System.out.println("Escolha o novo gênero do filme (ou digite 0 para manter o mesmo):");
		String genero = selecionarGenero();
		if (genero.equals("0")) {
			genero = filme.getGenero();
		}
		System.out.println("Digite o novo tipo do filme (deixe em branco para manter o mesmo):");
		String tipo = selecionarTipoFilme();
		input.nextLine();
		System.out.println("Digite a nova quantidade de DVDs disponíveis (digite -1 para manter o mesmo):");
		int qtdDVDs = Util.leInteiroConsole(input);
		System.out.println("Digite a nova quantidade de BluRays disponíveis (digite -1 para manter o mesmo):");
		int qtdBluRays = Util.leInteiroConsole(input);

		// Atualizar apenas os campos que não foram deixados em branco
		if (genero.isEmpty())
			genero = filme.getGenero();
		if (tipo.isEmpty())
			tipo = filme.getTipo();
		if (qtdDVDs == -1)
			qtdDVDs = filme.getQuantidadeDVDs();
		if (qtdBluRays == -1)
			qtdBluRays = filme.getQuantidadeBluRays();

		int retorno = controle.editarFilme(filme, genero, tipo, qtdDVDs, qtdBluRays);

		if (retorno == Constantes.RESULT_OK) {
			System.out.println("Filme editado com sucesso!");
		} else {
			System.out.println("Erro ao editar o filme.");
		}
	}

	private void excluirFilme(Filme filme) {
		controle.excluirFilme(filme);
		System.out.println("Filme excluído com sucesso!");
	}

	private String selecionarGenero() {
		System.out.println(
				"Escolha o gênero:\n\t1 - Comédia\n\t2 - Terror\n\t3 - Animação\n\t4 - Romance\n\t5 - Ação\n\t6 - Aventura\n\t7 - Outros");
		int opcao = Util.leInteiroConsole(input);
		switch (opcao) {
		case 1:
			return "Comédia";
		case 2:
			return "Terror";
		case 3:
			return "Animação";
		case 4:
			return "Romance";
		case 5:
			return "Ação";
		case 6:
			return "Aventura";
		case 7:
			return "Outros";
		default:
			System.out.println("Gênero inválido. Definindo como 'Outros'.");
			return "Outros";
		}
	}

	private String selecionarTipoFilme() {
		String tipo = "";
		int tipoEscolhido;
		do {
			System.out.println("Escolha o tipo de filme:\n\t1 - Lançamento\n\t2 - Novo\n\t3 - Antigo");
			tipoEscolhido = Util.leInteiroConsole(input);

			switch (tipoEscolhido) {
			case 1:
				tipo = "Lançamento";
				break;
			case 2:
				tipo = "Novo";
				break;
			case 3:
				tipo = "Antigo";
				break;
			default:
				System.out.println("Tipo inválido. Por favor, tente novamente.");
			}
		} while (tipoEscolhido < 1 || tipoEscolhido > 3);

		return tipo;
	}

}