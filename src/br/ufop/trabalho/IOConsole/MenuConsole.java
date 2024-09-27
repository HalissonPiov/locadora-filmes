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
        System.out.println("Digite a opção de Acesso:\n\t[1] - Filme\n\t[2] - Clientes\n\t[3] - Relatórios\n\t[4] - Sair ");
        int op = Util.leInteiroConsole(input);
        switch (op) {
            case 1:
                menuFilmes.exibeMenuFilmes();
                break;
            case 2:
                menuCliente.exibeMenuClientes();
                break;
            case 3:

                break;
            case 4:
                return false;
            default:
                System.out.println("Opção Inválida");
        }
        return true;
    }
}