package br.ufop.trabalho.IOConsole;

import java.util.Scanner;

import br.ufop.trabalho.Util;
import br.ufop.trabalho.controle.Constantes;
import br.ufop.trabalho.controle.Controle;

public class MenuClienteConsole {
	private Controle controle;
	private Scanner input;
	
	public MenuClienteConsole(Controle controle, Scanner input){
		this.controle = controle;
		this.input = input;
	}
	
	public void exibeMenuClientes(){
		boolean continua = true;
		int op  = 0;
		do{	
			// A opção 5 não é necessária. Foi inserida apenas para teste.
			System.out.println("Digite a opção:\n\t1 - Cadastrar Cliente\n\t2 - Buscar clientes\n\t5 - imprime Lista de Clientes\n\t10 - Voltar\n");
			op = Util.leInteiroConsole(input);
			switch(op){
				case 1:
					leDadosCliente();
					break;
				case 2:
					System.out.println("Falta implementar!");
					break;
				case 5:
					//Esta opção não foi solicitada no enunciado. É apenas para testes
					imprimeListaClientes();
					break;
				case 10:
					return;
				default:
					System.out.println("Op��o Inv�lida!");
			}		
		}while(continua == true);
	}

	/**
	 * Este método permitirá a entrada dos dados de um cliente. 
	 * 		MÉTODO INCOMPLETO. NÃO CADASTRA TODOS OS DADOS.
	 */
	private void leDadosCliente(){
		//Limpa o buffer já que leu um inteiro
		input.nextLine();
		String nome, end;
		int codigo;
		System.out.println("Digite o nome do cliente");
		nome = input.nextLine();
		System.out.println("Digite o endereco do cliente");
		end = input.nextLine();
		System.out.println("Digite o codigo do cliente");		
		codigo = Util.leInteiroConsole(input);
		input.nextLine();
		int retorno = controle.addCliente(nome, end, codigo);
		String msg = "";
		switch(retorno){
		//Verificação do retorno do método de adição de cliente
			case Constantes.ERRO_CAMPO_VAZIO:
					msg = "Todos os campos devem ser preenchidos!";
					break;
			case Constantes.RESULT_OK:
				msg = "Cliente cadastrado com sucesso!";
				break;
		}
		System.out.println(msg);

	}
	

	private void imprimeListaClientes() {
		System.out.println("******** LISTA DE CLIENTES CADASTRADOS *********");
		for(int i = 0; i < controle.getQtdClientes(); i++){
			//É preciso implementar o toString corretamente.
			System.out.println(controle.getClienteNaPosicao(i).toString());
		}	
		System.out.println("******** FIM DA LISTA DE CLIENTES  *********");
	}


}
