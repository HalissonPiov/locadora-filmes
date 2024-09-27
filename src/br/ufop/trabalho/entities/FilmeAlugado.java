package br.ufop.trabalho.entities;

public class FilmeAlugado {
    private Filme filme;
    private Cliente cliente;

    public FilmeAlugado(Filme filme, Cliente cliente) {
        this.filme = filme;
        this.cliente = cliente;
    }

    public Filme getFilme() {
        return filme;
    }

   public Cliente getCliente() {
	   return cliente;
   }
}