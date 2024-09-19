package br.ufop.trabalho.controle;

/**
 * Esta interface será utilizada apenas para armazenar constantes. Cada constante deverá ser estática e final e para seguirmos a convenção
 * elas também deverão ter todas as letras maiúsculas. 
 * 
 * As constantes inteiras servem para representar alguma informação importante de forma mais simples. Por exemplo: 
 * se considerarmos a chamada a um método para cadastro de Cliente passando todos os dados referentes ao cadastro do cliente. 
 * O método deverá fazer várias verificações com o objetivo de garantir que os dados passados estão corretos. Caso haja algum erro o 
 * método de cadastro de cliente deverá "informar" a quem o chamou o erro que ocorreu para que providências possam ser tomadas 
 * (pedir que o usuário digite os dados novamente). Uma alternativa para executar tal procedimento é o retorno de uma String contendo a 
 * descrição do erro. Essa solução ruim pois exige a todo momento a comparação entre Strings que é algo custoso computacionalmente. 
 * Uma alternativa mais simples é criar um inteiro para retorno e atribuir um identificador (nome da variavel) que pode diretamente indicar 
 * qual erro ocorreu. Para retornar o erro de cadastro de cliente com nome vazio deverá ser executado o código
 * 
 * 				Constantes. ERRO_CAMPO_VAZIO
 *  
 * No retorno bastará fazer uma verificação para qual tipo de retorno ocorreu erro ou RESULT_OK.
 *  
 * IMPORTANTE: OS NÚMEROS QUE SERÃO RETORNADOS EM UM MESMO MÉTODO DEVEM OBRIGATORIAMENTE SER DIFERENTES.
 * 
 * @author Filipe
 *
 */
public interface Constantes {
	
	// Códigos de resultado para operações gerais
	public static final int RESULT_OK = 1;
	public static final int ERRO_CAMPO_VAZIO = 2;
	public static final int ERRO_FILME_EXISTENTE = 3;
	public static final int ERRO_FILME_NAO_ENCONTRADO = 4;
	
}
