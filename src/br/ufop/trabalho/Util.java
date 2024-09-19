package br.ufop.trabalho;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Esta classe tem a função de oferecer métodos úteis que poderão ser utilizados em vários momentos do código. Por isso, os métodos
 * serão estáticos de forma que possam ser acessados sem a necessidade de instanciar um Objeto da classe Util.
 * @author Filipe
 *
 */
public class Util {
	
	/**
	 * Este méodo verifica se uma String recebida como parâmetro está preenchida com algum valor. Caso tenha pelo menos um caracter
	 * retornará true, caso contrário retornará false.
	 * @param texto
	 * @return
	 */
	public static boolean verificaStringPreenchida(String texto){
		if(texto != null && texto.trim().isEmpty()){
			return false;
		}
		return true;
	}
	/**
	 * Este método verifica se uma lista de Strings está preenchida. Veja que o parâmetro recebe um número variável de Strings
	 * que automaticamente é convertido em um array de Strings.
	 * @param strings
	 * @return
	 */
	public static boolean verificaListaStringPreenchida(String ... strings ){
		for(String s: strings){
			if(verificaStringPreenchida(s) == false)
				return false;
		}
		return true;
	}
	
	/**
	 * Método que verifica se a senha tem pelo menos um número
	 * @param senha
	 * @return
	 */
	public static boolean senhaComNumero (String senha){
		//FALTA IMPLEMENTAR
		return true;
	}
	
	public static int leInteiroConsole(Scanner in){
		int r = 0;
		boolean continua = false;
		do{
			try{
				r = in.nextInt();
				continua = false;		
			}
			catch(InputMismatchException e){
				System.out.println("Erro ao ler n�mero! Digite novamente:");
				in.nextLine();
				continua = true;
			}
		}while(continua);
		return r;
	}
			
	
}
