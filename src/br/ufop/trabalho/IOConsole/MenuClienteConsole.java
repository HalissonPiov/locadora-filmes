package br.ufop.trabalho.IOConsole;

import java.util.Scanner;

import br.ufop.trabalho.Util;
import br.ufop.trabalho.controle.Constantes;
import br.ufop.trabalho.controle.Controle;
import br.ufop.trabalho.entities.Cliente;
import br.ufop.trabalho.entities.Data;

public class MenuClienteConsole {
	private Controle controle;
	private Scanner input;
	private Cliente cliente;

	public MenuClienteConsole(Controle controle, Scanner input) {
		this.controle = controle;
		this.input = input;
	}

	public void exibeMenuClientes() {
		boolean continua = true;
		int op = 0;
		do {
			// A opção 5 não é necessária. Foi inserida apenas para teste.
			System.out.println(
					"Digite a opção:\n\t[1] - Cadastrar Cliente\n\t[2] - Buscar clientes\n\t[5] - imprime Lista de Clientes\n\t[10] - Voltar\n");
			op = Util.leInteiroConsole(input);
			switch (op) {
			case 1:
				leDadosCliente();
				break;
			case 2:
				System.out.println("Falta implementar!");
				break;
			case 5:
				// Esta opção não foi solicitada no enunciado. É apenas para testes
				imprimeListaClientes();
				break;
			case 10:
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
		String nome, end, cpf, msg = "", nomeD, endD, cpfD;
		int dia, mes, ano, diaD, mesD, anoD, codigo;

		System.out.println("O cliente é dependente? ");
		System.out.println("\t[1] Sim\n\t[2] Não");
		int escolha = input.nextInt();
		input.nextLine();

		if (escolha == 1 || escolha == 2) {

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
			mes = input.nextInt();
			System.out.println("Ano: ");
			ano = input.nextInt();
			input.nextLine();
//		
			if (escolha == 1) {

				System.out.println("Agora, insira dos dados do dependente");
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

				int resultado = cliente.addDependentes(nomeD, endD, cpfD, dataNascimentoD, cpf);
				switch (resultado) {
				case Constantes.ERRO_CAMPO_VAZIO:
					msg = "Todos os campos devem ser preenchidos!";
					break;
				case Constantes.RESULT_OK:
					msg = "\nDependente cadastrado com sucesso!";
					break;
				case 0:
					msg = "Não foi possível cadastrar o dependente. O cliente (responsável) já possui o limite máximo de 3 dependentes";
					System.out.println(msg);
					return;
				default:
					break;
				}
			}
			System.out.println(msg);

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
		} else {
			return;
		}
	}

	private void imprimeListaClientes() {
		System.out.println("******** LISTA DE CLIENTES CADASTRADOS *********");
		for (int i = 0; i < controle.getQtdClientes(); i++) {
			// É preciso implementar o toString corretamente.
			System.out.println(controle.getClienteNaPosicao(i).toString());
		}
		System.out.println("******** FIM DA LISTA DE CLIENTES  *********");
	}

}
