package br.ufop.trabalho.IOConsole;

import java.util.Scanner;
import br.ufop.trabalho.Util;
import br.ufop.trabalho.controle.Controle;

public class MenuConsole {

	private Controle controle;
	private MenuClienteConsole menuCliente;
	private MenuFilmesConsole menuFilmes;
	private Scanner input;

	public MenuConsole() {
		controle = new Controle();
		input = new Scanner(System.in);
		menuCliente = new MenuClienteConsole(controle, input);
		menuFilmes = new MenuFilmesConsole(controle, input);
	}

	public void inicioExecucao() {
		boolean continua = true;
		do {
			continua = exibeMenuPrincipal();
		} while (continua);
		System.out.println("Obrigado por usar o sistema!");
	}

	private boolean exibeMenuPrincipal() {
		System.out.println(
				"Digite a opção de Acesso:\n\t[1] - Filme\n\t[2] - Clientes\n\t[3] - Relatórios\n\t[4] - Balancete\n\t[5] - Sair ");
		int op = Util.leInteiroConsole(input);
		switch (op) {
		case 1:
			menuFilmes.exibeMenuFilmes();
			break;
		case 2:
			menuCliente.exibeMenuClientes();
			break;
		case 3:
			System.out.println(
					"[1] Relatório de clientes\n[2] Relatório de filmes por gênero\n[3] Relatório de filmes por ano de lançamento\n[4] Relatório de filmes por nome\n");
			int opcao = Util.leInteiroConsole(input);
			switch (opcao) {
			case 1:
				controle.relatorioClientes();
				break;
			case 2:
				controle.relatorioFilmesPorGenero();
				break;
			case 3:
				controle.relatorioFilmesPorAnoLanc();
				break;
			case 4:
				controle.relatorioFilmesNomeEmOrdem();
				break;
			default:
				break;
			}
			break;
		case 4:
			System.out.println(
					"[ Atenção: os cadastros de entrada e de saída abaixo permitem realizar essas ações de forma manual.\n"
					+ "Entradas realizadas por clientes dentro da plataforma são computadas automaticamente quando feitas]\n");
			System.out.println(
					"[1] Cadastro de entrada\n[2] Cadastro de saída\n[3] Busca de movimentações\n[4] Balancete por mês\n[5] Balancete por ano");
			int escolha = Util.leInteiroConsole(input);

			switch (escolha) {
			case 1:
				menuCliente.cadastroDeEntrada();
				return true;
			case 2:
				menuCliente.cadastroDeSaida();
				return true;
			case 3:
				menuCliente.buscarDeMovimentacoes();
				return true;
			case 4:
				menuCliente.balancetePorMes();
				return true;
			case 5:
				menuCliente.balancetePorAno();
				return true;
			default:
				System.out.println("Opção inválida.");
				break;
			}
		case 5:
			return false;
		default:
			System.out.println("Opção Inválida");
		}
		return true;
	}
}