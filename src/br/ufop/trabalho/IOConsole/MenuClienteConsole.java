package br.ufop.trabalho.IOConsole;

import java.util.ArrayList;
import java.util.Scanner;

import br.ufop.trabalho.Util;
import br.ufop.trabalho.controle.Constantes;
import br.ufop.trabalho.controle.Controle;
import br.ufop.trabalho.entities.Cliente;
import br.ufop.trabalho.entities.Data;
import br.ufop.trabalho.entities.Filme;
import br.ufop.trabalho.entities.Dependentes;

public class MenuClienteConsole {
	private Controle controle;
	private Scanner input;
	private Cliente cliente;
	private MenuFilmesConsole menufilmesconsole;

	public MenuClienteConsole(Controle controle, Scanner input) {
		this.controle = controle;
		this.input = input;
	}

	public void exibeMenuClientes() {
		boolean continua = true;
		int op = 0;
		do {
			// A opção 3 não é necessária. Foi inserida apenas para teste.
			System.out.println(
					"Digite a opção:\n\t[1] - Cadastrar cliente\n\t[2] - Buscar clientes\n\t[3] - Imprime lista de clientes\n\t[4] - Voltar\n");
			op = Util.leInteiroConsole(input);
			switch (op) {
			case 1:
				leDadosCliente();
				break;
			case 2:
				buscarCliente();
				break;
			case 3:
				// Esta opção não foi solicitada no enunciado. É apenas para testes
				imprimeListaClientes();
				break;
			case 4:
				return;
			default:
				System.out.println("Opção Inválida!");
			}
		} while (continua == true);
	}

	/**
	 * Este método permitirá a entrada dos dados de um cliente. MÉTODO INCOMPLETO.
	 * NÃO CADASTRA TODOS OS DADOS.
	 */

	private void leDadosCliente() {

		Cliente cliente = new Cliente();

		// Limpa o buffer já que leu um inteiro
		input.nextLine();
		String nome, end, cpf = null, msg = "";
		int dia, mes, ano, codigo;

		System.out.println("O cliente é dependente? ");
		System.out.println("\t[1] Sim\n\t[2] Não");
		int escolha = Util.leInteiroConsole(input);
		input.nextLine();

		System.out.println("Digite o nome do cliente: ");
		nome = input.nextLine();
		System.out.println("Digite o endereco do cliente: ");
		end = input.nextLine();
		System.out.println("Digite o codigo do cliente: ");
		codigo = Util.leInteiroConsole(input);
		input.nextLine();
		System.out.println("Digite o CPF do cliente: ");
		cpf = input.nextLine();
		System.out.println("Digite a data de nascimento do cliente");
		System.out.println("Dia: ");
		dia = input.nextInt();
		System.out.println("Mes: ");
		mes = Util.leInteiroConsole(input);
		System.out.println("Ano: ");
		ano = input.nextInt();
		input.nextLine();

		Data dataNascimento = new Data(dia, mes, ano);

		int retorno = controle.addCliente(nome, end, codigo, cpf, dataNascimento);
		// Ao imprimir dataNascimento, vai chamar e imprimir o toString?
		switch (retorno) {
		// Verificação do retorno do método de adição de cliente
		case Constantes.ERRO_CAMPO_VAZIO:
			msg = "Todos os campos devem ser preenchidos!";
			break;
		case Constantes.RESULT_OK:
			msg = "\nCliente cadastrado com sucesso!";
			break;
		}
		System.out.println(msg);

		if (escolha == 1) {
			cadastrarDependente(cpf);
		}
	}

	private void cadastrarDependente(String cpfCliente) {

		if (!controle.verificarQuantDepend(cpfCliente)) {
			System.out.println(
					"O cliente já possui a quantidade máxima de 3 dependentes. Remova um dependente para adicionar outro");
			return;
		}

		String msgD = "", nomeD, endD, cpfD;
		int diaD, mesD, anoD;

		System.out.println("Agora, insira dos dados do dependente para concluir o cadastro");
		System.out.println("Digite o nome do dependente: ");
		nomeD = input.nextLine();
		System.out.println("Digite o CPF do dependente: ");
		cpfD = input.nextLine();
		System.out.println("Digite o nome do endereco: ");
		endD = input.nextLine();
		System.out.println("Digite a data de nascimento");
		System.out.println("Dia: ");
		diaD = input.nextInt();
		System.out.println("Mes: ");
		mesD = input.nextInt();
		System.out.println("Ano: ");
		anoD = input.nextInt();
		input.nextLine();

		Data dataNascimentoD = new Data(diaD, mesD, anoD);

		int resultado = controle.buscarClientePorCpf(cpfCliente).addDependentes(nomeD, endD, cpfD, dataNascimentoD,
				cpfCliente, controle.buscarClientePorCpf(cpfCliente));

		switch (resultado) {
		case Constantes.ERRO_CAMPO_VAZIO:
			msgD = "Todos os campos devem ser preenchidos!";
			break;
		case Constantes.RESULT_OK:
			msgD = "\nDependente cadastrado com sucesso!";
			break;
		default:
			break;
		}
		System.out.println(msgD);
	}

	private void imprimeListaClientes() {
		System.out.println("******** LISTA DE CLIENTES CADASTRADOS *********");
		for (int i = 0; i < controle.getQtdClientes(); i++) {
			// É preciso implementar o toString corretamente.
			System.out.println(controle.getClienteNaPosicao(i).toString());
		}
		System.out.println("******** FIM DA LISTA DE CLIENTES  *********");
	}

	public void buscarCliente() {

		String nomeCliente = "", nomeDependente = "";
		int codigo = 0;
		ArrayList<Cliente> retorno = new ArrayList<>();

		System.out.println("Buscar cliente por: ");
		System.out.println("[1] Nome\n[2] Código\n[3] Dependente");
		int opcao = Util.leInteiroConsole(input);
		input.nextLine();

		switch (opcao) {
		case 1:
			System.out.println("Digite o nome do cliente: ");
			nomeCliente = input.nextLine();
			break;
		case 2:
			System.out.println("Digite o código do cliente: ");
			codigo = Util.leInteiroConsole(input);
			break;
		case 3:
			System.out.println("Digite o nome do dependente associado ao cliente: ");
			nomeDependente = input.nextLine();
			break;
		default:
			System.out.println("Opção inválida");
			break;
		}

		retorno = (ArrayList<Cliente>) controle.encontrarCliente(opcao, nomeCliente, codigo, nomeDependente);

		if (retorno.isEmpty()) {
			System.out.println("Nenhum cliente encontrado.");
		} else {
			for (int i = 0; i < retorno.size(); i++) {
				System.out.println((i + 1) + " - " + retorno.get(i) + "\t");
			}
			System.out.println("Digite o número correspondente ao cliente ou digite 0 para voltar");
			int escolha = Util.leInteiroConsole(input);
			if (escolha > 0 && escolha <= retorno.size()) {
				Cliente clienteSelecionado = retorno.get(escolha - 1);
				System.out.println("Detalhes do cliente: " + clienteSelecionado);
				exibirOpcoesCliente(clienteSelecionado);
			}
		}

	}

	public void exibirOpcoesCliente(Cliente cliente) {

		System.out.println("Selecione uma opção: ");
		System.out.println("[1] Editar\n[2] Locar filme\n[3] Devolver filme\n[4] Pagar multa\n[5] Voltar");
		int opcao = Util.leInteiroConsole(input);
		switch (opcao) {
		case 1:
			editarCliente(cliente);
			break;
		case 2:
			buscarFilme(cliente);
			break;
		case 3:
			System.out.println("Houve atrasado para devolução do filme e há multa a ser paga: \n[1] Sim\n[2] Não");
			int op = Util.leInteiroConsole(input);
			if (op == 1) {
				System.out.println("Digite a quantidade de dias de atraso: ");
				int dias = Util.leInteiroConsole(input);
				double valorMulta = controle.calcularMulta(dias);
				cliente.setMulta(valorMulta);
				System.out.println("Volte e selecione a opção de pagar multa antes de devolver");
				return;
			} else if (op == 2 && cliente.getMulta() == 0) {
				ArrayList<Filme> listaDeFilmes = cliente.getFilmes();
				if (listaDeFilmes.isEmpty()) {
					System.out.println("Nenhum filme encontrado.");
				} else {
					for (int i = 0; i < listaDeFilmes.size(); i++) {
						System.out.println((i + 1) + " - " + listaDeFilmes.get(i) + "\t");
					}
					System.out.println("Insira o número correspondente ao filme que deseja devolver: ");
					int escolha = Util.leInteiroConsole(input);
					if (escolha > 0 && escolha <= listaDeFilmes.size()) {
						Filme filmeEscolhido = listaDeFilmes.get(escolha - 1);
						System.out.println("Detalhes do filme: " + filmeEscolhido);
						System.out.println("[1] Confirmar devolução\n[2] Voltar");
						int opc = Util.leInteiroConsole(input);
						if (opc == 1) {
							listaDeFilmes.remove(escolha - 1);
							System.out.println("Filme devolvido com sucesso");
						} else {
							return;
						}
					}
				}
			} else {
				return;
			}
			break;
		case 4:
			System.out.println("Valor em multa a ser pago: " + cliente.getMulta());
			System.out.println("[1] Pagar\n[2] Voltar");
			int escolha = Util.leInteiroConsole(input);
			if (escolha == 1) {
				System.out.println("Transação sendo efetuada...");
				cliente.setMulta(0);
				System.out.println("Multa paga com sucesso");
			} else {
				return;
			}
			break;
		case 5:
			return;
		default:
			break;
		}

	}

	public void editarCliente(Cliente cliente) {
		int op;

		System.out.println(
				"[1] Adicionar um novo dependente\n[2] Remover um dependente\n[3] Editar dados cadastrais do cliente\n[4] Voltar");
		op = Util.leInteiroConsole(input);
		input.nextLine();

		switch (op) {
		case 1:
			cadastrarDependente(cliente.getCpf());
			break;
		case 2:
			if (cliente.getQuantDependentes() >= 1) {
				System.out.println("Digite o número associado ao dependente que deseja remover: ");

				ArrayList<Dependentes> listaDepend = cliente.getDependentes();

				for (int i = 0; i < listaDepend.size(); i++) {
					System.out.println((i + 1) + " - " + listaDepend.get(i));
				}

				int escolha = Util.leInteiroConsole(input);
				if (escolha > 0 && escolha <= cliente.getDependentes().size()) {
					Dependentes dependenteSelecionado = cliente.getDependentes().get(escolha - 1);
					System.out.println("Detalhes do dependente:\n" + dependenteSelecionado);
					System.out.println("[1] Confirmar remoção\n[2] Voltar");
					int opc = Util.leInteiroConsole(input);
					if (opc == 1) {
						listaDepend.remove(escolha - 1);
						System.out.println("Dependente removido");
					} else {
						return;
					}
				} else {
					System.out.println("O cliente não possui dependentes");
				}
			}
			break;
		case 3:
			System.out.println("Informe os novos dados do cliente para editá-los:");
			leDadosCliente();
			int posicao = controle.getPosicaoCliente(cliente);
			controle.getClientes().remove(posicao);
			break;
		case 4:
			return;
		default:
			break;
		}
	}

	private void locarFilme(Filme filme, Cliente cliente) {

		if (cliente == null) {
			System.out.println("Cliente não encontrado ou nome/CPF incorretos.");
			return;
		}

		// Informações sobre o filme que será locado
		System.out.println("Filme escolhido: " + filme.getNome());
		System.out.println("Escolha o formato para locação:");
		System.out.println("1 - DVD (Disponíveis: " + filme.getQuantidadeDVDs() + ")");
		System.out.println("2 - Blu-ray (Disponíveis: " + filme.getQuantidadeBluRays() + ")");

		int formatoEscolhido = Util.leInteiroConsole(input);

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

		if (locacaoBemSucedida) {
			System.out.println("Filme locado com sucesso!");
		} else {
			System.out.println("Erro ao locar o filme.");
		}
	}

	private void buscarFilme(Cliente cliente) {
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
			System.out.print("Insira o número do filme para ver detalhes ou 0 para voltar: ");
			int escolha = Util.leInteiroConsole(input);
			if (escolha > 0 && escolha <= resultados.size()) {
				Filme filmeSelecionado = resultados.get(escolha - 1);
				System.out.println("Detalhes do Filme:\n" + filmeSelecionado);
				System.out.println("[1] Locar esse filme\n[2] Voltar");
				int op = Util.leInteiroConsole(input);
				if (op == 1) {
					locarFilme(filmeSelecionado, cliente);
				} else {
					return;
				}
			}
		}
	}

}
