package br.ufop.trabalho.IOConsole;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import br.ufop.trabalho.Util;
import br.ufop.trabalho.controle.Constantes;
import br.ufop.trabalho.controle.Controle;
import br.ufop.trabalho.entities.Cliente;
import br.ufop.trabalho.entities.Data;
import br.ufop.trabalho.entities.Filme;
import br.ufop.trabalho.controle.Saida;
import br.ufop.trabalho.controle.Entrada;
import br.ufop.trabalho.entities.Dependentes;

public class MenuClienteConsole {
	private Controle controle;
	private Scanner input;
	private Cliente cliente;
	private MenuFilmesConsole menufilmesconsole;
	private Entrada entrada;
	private Saida saida;

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

		if (controle.buscarClientePorCpf(cpf) != null) {
			System.out.println("Erro: o CPF informado já está cadastrado");
			return;
		}

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
				controle.buscarClientePorCpf(cpfCliente));

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
			System.out.println("Houve atraso para devolução do filme e há multa a ser paga: \n[1] Sim\n[2] Não");
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
							// Adiciona a lógica de entrada ao devolver filme
							double valorDevolucao = 0; // Defina o valor aqui, se aplicável
							Entrada entrada = new Entrada("Devolução de Filme",
									"Cliente: " + cliente.getNome() + " - Filme devolvido: " + filmeEscolhido.getNome(),
									valorDevolucao, LocalDate.now());
							cliente.adicionarEntrada(entrada); // Certifique-se de que esse método existe

							listaDeFilmes.remove(escolha - 1);
							System.out.println("Filme devolvido com sucesso");

							// Exibe as informações da entrada
							exibirEntrada(entrada);
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
				// Criação da entrada financeira para o pagamento da multa
				double valorMulta = cliente.getMulta();
				Entrada entrada = new Entrada("Pagamento de Multa", "Cliente: " + cliente.getNome() + " - Multa paga",
						valorMulta, LocalDate.now());
				// Adiciona a entrada ao controle
				cliente.adicionarEntrada(entrada); // Certifique-se de que esse método existe
				controle.somaMultas(valorMulta);
				System.out.println("Transação sendo efetuada...");
				cliente.setMulta(0); // Zera a multa após o pagamento
				System.out.println("Multa paga com sucesso");

				// Exibe as informações da entrada
				exibirEntrada(entrada);
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

	// Método auxiliar para exibir informações da entrada
	private void exibirEntrada(Entrada entrada) {
		System.out.println("Entrada registrada:");
		System.out.println("Nome: " + entrada.getNome());
		System.out.println("Descrição: " + entrada.getDescricao());
		System.out.println("Valor: " + entrada.getValor());
		System.out.println("Data: " + entrada.getDataEntrada());
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

		if (cliente.getFilmes().size() == 5) {
			System.out.println(
					"O cliente atingiu a quantidade máxima de 5 filmes alugados por cliente. Devolta um para poder locar um outro.");
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

	public void cadastroDeEntrada() {

		int valor, quant;
		double valorEntrada;

		System.out.println("Selecione a opção de cadastro de entrada: ");
		System.out.println(
				"[1] Aluguel de filmes antigos\n[2] Aluguel de filmes novos\n[3] Aluguel de filmes de lançamento\n[4] Valor de multas pagas por clientes");
		int op = Util.leInteiroConsole(input);
		switch (op) {
		case 1:
			System.out.println("Digite a quantidade de filmes antigos que foram alugados: ");
			quant = Util.leInteiroConsole(input);
			valor = 5;
			valorEntrada = valor * quant;
			Entrada entradaFA = new Entrada("Filmes antigos alugados", "Entrada feita pelo usuário no balancete",
					valorEntrada, LocalDate.now());
			controle.adicionarEntrada(entradaFA);
			System.out.println("Entrada de " + valor * quant + " reais registrada");
			break;
		case 2:
			System.out.println("Digite a quantidade de filmes novos que foram alugados: ");
			quant = Util.leInteiroConsole(input);
			valor = 7;
			valorEntrada = valor * quant;
			Entrada entradaFN = new Entrada("Filmes novos alugados", "Entrada feita pelo usuário no balancete",
					valorEntrada, LocalDate.now());
			controle.adicionarEntrada(entradaFN);
			System.out.println("Entrada de " + valor * quant + " reais registrada");
			break;
		case 3:
			System.out.println("Digite a quantidade de filmes de lançamento que foram alugados: ");
			quant = Util.leInteiroConsole(input);
			valor = 10;
			valorEntrada = valor * quant;
			Entrada entradaFL = new Entrada("Filmes de lançamentos alugados", "Entrada feita pelo usuário no balancete",
					valorEntrada, LocalDate.now());
			controle.adicionarEntrada(entradaFL);
			System.out.println("Entrada de " + valor * quant + " reais registrada");
			break;
		case 4:
			System.out.println("Digite o valor: ");
			double valorInserido = input.nextDouble();
			Entrada entradaM = new Entrada("Multa paga", "Entrada feita pelo usuário no balancete", valorInserido,
					LocalDate.now());
			controle.adicionarEntrada(entradaM);
			System.out.println("Entrada de " + valorInserido + " reais registrada");
			break;
		default:
			System.out.println("Opção inválida");
			break;
		}
	}

	public void cadastroDeSaida() {
		int quantDVD, quantBluRay, escolha;
		double novoValor;
		double valorTotalDVD, valorTotalBluRay, salarioTotalFunc;

		System.out.println("Selecione a opção de cadastro de saída");
		System.out.println(
				"[1] Aluguel do imóvel\n[2] Compra de filmes\n[3] Pagamento de funcionários\n[4] Custos operacionais");
		int op = Util.leInteiroConsole(input);

		switch (op) {
		case 1:
			// Aluguel do imóvel
			System.out.println(
					"[1] Inserir novo valor do aluguel e alterar o valor fixo no sistema\n[2] Registrar pagamento do aluguel no mesmo valor de "
							+ Controle.getAluguelImovel() + " reais");
			escolha = Util.leInteiroConsole(input);
			if (escolha == 1) {
				System.out.println("Insira o novo valor: ");
				novoValor = input.nextDouble();
				Controle.setAluguelImovel(novoValor);
			}
			Saida saidaA = new Saida("Aluguel pago", "Saída feita pelo usuário no balancete",
					Controle.getAluguelImovel(), LocalDate.now());
			controle.adicionarSaida(saidaA);
			System.out.println("Saída de " + Controle.getAluguelImovel() + " reais registrada");
			break;

		case 2:
			// Compra de DVDs e Blu-rays
			System.out.println("Digite a quantidade de DVDs adquiridos pela locadora: ");
			quantDVD = Util.leInteiroConsole(input);
			controle.quantDVDsComprados(quantDVD);

			System.out.println(
					"[1] Inserir novo valor do DVD e alterar o valor fixo no sistema\n[2] Registrar pagamento de DVD no valor de "
							+ Controle.getValorDVD() + " reais");
			escolha = Util.leInteiroConsole(input);
			if (escolha == 1) {
				System.out.println("Insira o novo valor");
				novoValor = input.nextDouble();
				Controle.setValorDVD(novoValor);
			}
			valorTotalDVD = quantDVD * Controle.getValorDVD();
			Saida saidaDVD = new Saida("Compra de DVD", "Saída feita pelo usuário no balancete", valorTotalDVD,
					LocalDate.now());
			controle.adicionarSaida(saidaDVD);
			System.out.println("Saída de " + valorTotalDVD + " reais registrada\n");

			System.out.println("Digite a quantidade de Blu-rays adquiridos pela locadora:");
			quantBluRay = Util.leInteiroConsole(input);
			controle.quantBluRaysComprados(quantBluRay);

			System.out.println(
					"[1] Inserir novo valor do Blu-ray e alterar o valor fixo no sistema\n[2] Registrar pagamento de Blu-ray no valor de "
							+ Controle.getValorBluRay() + " reais");
			escolha = Util.leInteiroConsole(input);
			if (escolha == 1) {
				System.out.println("Insira o novo valor");
				novoValor = input.nextDouble();
				Controle.setValorBluRay(novoValor);
			}
			valorTotalBluRay = quantBluRay * Controle.getValorBluRay();
			Saida saidaBluRay = new Saida("Compra de Blu-ray", "Saída feita pelo usuário no balancete",
					valorTotalBluRay, LocalDate.now());
			controle.adicionarSaida(saidaBluRay);
			System.out.println("Saída de " + valorTotalBluRay + " reais registrada\n");
			break;

		case 3:
			// Pagamento de funcionários
			System.out.println("Digite o valor total a ser pago aos funcionários: ");
			salarioTotalFunc = input.nextDouble();
			Saida saidaFunc = new Saida("Pagamento de Funcionários", "Saída feita pelo usuário no balancete",
					salarioTotalFunc, LocalDate.now());
			controle.adicionarSaida(saidaFunc);
			System.out.println("Saída de " + salarioTotalFunc + " reais registrada\n");
			break;

		case 4:
			System.out.println("Digite qual foi o valor de custos operacionais: ");
			double valorOP = Util.leInteiroConsole(input);

			Saida saidaOP = new Saida("Pagamento de custos operacionais", "Saída feita pelo usuário no balancete",
					valorOP, LocalDate.now());
			controle.adicionarSaida(saidaOP);

			System.out.println("Saída de " + valorOP + " reais registrada");
			break;
		default:
			System.out.println("Opção inválida");
			break;
		}
	}

	public void buscarDeMovimentacoes() {

		input.nextLine();

		System.out.println("Digite o nome do cliente: ");
		String nome = input.nextLine();

		ArrayList<Cliente> listaClientes = (ArrayList<Cliente>) controle.buscarClienteComMovim(nome);

		if (listaClientes.isEmpty()) {
			System.out.println("Nenhum cliente com esse nome encontrado que tenha feito uma movimentação");
			return;
		}

		for (int i = 0; i < listaClientes.size(); i++) {
			System.out.println((i + 1) + " - " + listaClientes.get(i));
		}
		System.out.print("Escolha o número do cliente para ver as movimentações ou 0 para voltar: ");
		int escolha = Util.leInteiroConsole(input);
		if (escolha > 0 && escolha <= listaClientes.size()) {
			Cliente clienteSelec = listaClientes.get(escolha - 1);

			ArrayList<Entrada> entradasCliente = clienteSelec.getEntradas();

			System.out.println("Movimentações do cliente: " + clienteSelec);
			for (int i = 0; i < entradasCliente.size(); i++) {
				System.out.println((i + 1) + " - " + entradasCliente.get(i));
				System.out.println("\n");
			}
			System.out.print("Escolha o número da movimentação do cliente para ver as opções ou 0 para voltar: ");
			int opcao = Util.leInteiroConsole(input);
			if (opcao > 0 && opcao <= entradasCliente.size()) {
				Entrada entradaSelec = entradasCliente.get(opcao - 1);
				System.out.println("[1] Editar movimentação\n[2] Excluir movimentação");
				int op = Util.leInteiroConsole(input);
				if (op == 1) {
					editarEntrada(entradaSelec, clienteSelec);
					System.out.println("Entrada editada");
					System.out.println("Entrada excluída");
				} else if (op == 2) {
					cliente.excluirEntrada(entradaSelec, opcao - 1);
					System.out.println("Entrada excluída.");
				} else {
					System.out.println("Opção inválida.");
				}

			}
		}
	}

	public void editarEntrada(Entrada entrada, Cliente cliente) {

		LocalDate dataHoje = null;

		System.out.println("Digite o nome da entrada: ");
		String nome = input.nextLine();
		System.out.println("Digite a descrição: ");
		String desc = input.nextLine();
		System.out.println("Digite o valor: ");
		double valor = input.nextDouble();
		System.out.println("[1] Digitar uma data de entrada (formato XX/XX/XXXX)\n[2] Inserir a data de hoje");
		int op = Util.leInteiroConsole(input);
		if (op == 1) {
			System.out.println("Digite o dia: ");
			int d = Util.leInteiroConsole(input);
			System.out.println("Digite o mês: ");
			int m = Util.leInteiroConsole(input);
			System.out.println("Digite o ano: ");
			int a = Util.leInteiroConsole(input);
			dataHoje = LocalDate.of(a, m, d);
		} else if (op == 2) {
			dataHoje = LocalDate.now();
		} else {
			System.out.println("Opção inválida.");
			return;
		}

		Entrada novaEntrada = new Entrada(nome, desc, valor, dataHoje);
		cliente.adicionarEntrada(novaEntrada);
	}

	public void coletarEntrada() {
		System.out.println();
	}

	private void exibirSaldo(double totalEntradas, double totalSaidas, String periodo) {
		double saldo = totalEntradas - totalSaidas;
		System.out.printf("Total de Entradas (%s): R$ %.2f\n", periodo, totalEntradas);
		System.out.printf("Total de Saídas (%s): R$ %.2f\n", periodo, totalSaidas);
		System.out.printf("Saldo (%s): R$ %.2f\n", periodo, saldo);
	}

	public void balancetePorMes() {
		System.out.println("Digite o mês (1-12): ");
		int mes = Util.leInteiroConsole(input);

		System.out.println("Digite o ano (AAAA): ");
		int ano = Util.leInteiroConsole(input);

		double totalEntradas = 0.0;
		double totalSaidas = 0.0;

		// Exibir entradas do mês
		System.out.println("Entradas de " + mes + "/" + ano + ":");

		for (Cliente c : controle.getClientes()) {
			for (Entrada e : c.getEntradas()) {
				if (e.getDataEntrada().getMonthValue() == mes && e.getDataEntrada().getYear() == ano) {
					totalEntradas += e.getValor();
					System.out.println(e);
					System.out.println("\n");
				}
			}
		}

		for (Entrada entrada : controle.getEntradas()) {
			if (entrada.getDataEntrada().getMonthValue() == mes && entrada.getDataEntrada().getYear() == ano) {
				System.out.println(entrada);
				System.out.println("\n");
				totalEntradas += entrada.getValor();
			}
		}

		// Exibir saídas do mês
		System.out.println("Saídas de " + mes + "/" + ano + ":");
		for (Saida saida : controle.getSaidas()) {
			if (saida.getDataSaida().getMonthValue() == mes && saida.getDataSaida().getYear() == ano) {
				System.out.println(saida);
				System.out.println("\n");
				totalSaidas += saida.getValor();
			}
		}

		// Chamar o método para exibir o saldo
		exibirSaldo(totalEntradas, totalSaidas, mes + "/" + ano);
	}

	public void balancetePorAno() {
		System.out.println("Digite o ano (AAAA): ");
		int ano = Util.leInteiroConsole(input);

		double totalEntradas = 0.0;
		double totalSaidas = 0.0;

		// Exibir entradas do ano
		System.out.println("Entradas de " + ano + ":");

		for (Cliente c : controle.getClientes()) {
			for (Entrada e : c.getEntradas()) {
				if (e.getDataEntrada().getYear() == ano) {
					totalEntradas += e.getValor();
					System.out.println(e);
					System.out.println("\n");
				}
			}
		}

		for (Entrada entrada : controle.getEntradas()) {
			if (entrada.getDataEntrada().getYear() == ano) {
				System.out.println(entrada);
				System.out.println("\n");
				totalEntradas += entrada.getValor();
			}
		}

		// Exibir saídas do ano
		System.out.println("Saídas de " + ano + ":");
		for (Saida saida : controle.getSaidas()) {
			if (saida.getDataSaida().getYear() == ano) {
				System.out.println(saida);
				System.out.println("\n");
				totalSaidas += saida.getValor();
			}
		}

		// Chamar o método para exibir o saldo
		exibirSaldo(totalEntradas, totalSaidas, String.valueOf(ano));
	}
}