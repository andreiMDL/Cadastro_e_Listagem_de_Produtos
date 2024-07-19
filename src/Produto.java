import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Comparator;
import java.util.Locale;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Produto implements Comparable<Produto> {

    public static final String RESET = "\u001b[0m";
    public static final String RED = "\u001b[31m";
    public static final String GREEN = "\u001b[32m";
    public static final String BLUE = "\u001b[34m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BOLD = "\u001b[1m";

    private String nome;
    private String descricao;
    private double valor;
    private boolean disp;

    public Produto(String nome, String descricao, double valor, boolean disp) {
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.disp = disp;

    }
    public String getValorFormatado(){
        DecimalFormat df = new DecimalFormat("#,##0.00", DecimalFormatSymbols.getInstance(Locale.US));
        return df.format(valor);
    }

    public String getNome(){
        return nome;
    }

    public String getDescricao(){
        return descricao;
    }

    public double getValor(){
        return (double) valor;
    }

    public boolean getDisp(){
        return disp;
    }


    @Override
    public String toString(){
        String disponibilidade = disp ?  GREEN + BOLD + "Disponível" : RED + BOLD + "Indisponível";
        return BLUE + BOLD +"\n Nome: " + RESET +  nome + BLUE + BOLD +"\n Descrição: " + RESET + descricao + BLUE +  BOLD +
        "\n Valor: R$ " + RESET + getValorFormatado() + BLUE + BOLD +"\n Disponiblidade: "+ RESET + disponibilidade + RESET ;

    }

    //CONVERTE OS OBJETOS PRODUTO EM STRING

    public String toFileString(){
        return nome + ";" + descricao + ";" + valor + ";" + disp;
    }

    public static Produto fromFileString(String fileString){
        String[] parts = fileString.split(";");
        return new Produto(parts[0], parts[1], Double.parseDouble(parts[2]), Boolean.parseBoolean(parts[3]));
    }

    @Override
    public int compareTo(Produto outroProduto){
        return Double.compare(this.valor, outroProduto.valor);
    }
}
