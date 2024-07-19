import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collections;
import java.util.Locale;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class Main {
    private static final String FILE_PATH = "produtos.txt";

    public static final String RESET = "\u001b[0m";
    public static final String RED = "\u001b[31m";
    public static final String GREEN = "\u001b[32m";
    public static final String BLUE = "\u001b[34m";
    public static final String YELLOW = "\u001b[33m";
    public static final String BOLD = "\u001b[1m";

    public static void main(String[] args) {
        System.out.println("Cadastro e Listagem de produtos");

        Scanner scanner = new Scanner(System.in);
        ArrayList<Produto> produtos = new ArrayList<>();
        carregarProdutos(produtos);


        //ADICIONAR PRODUTO

        while(true) {
            System.out.println( BOLD + "\nO que deseja fazer? " + RESET);
            System.out.println(YELLOW+  "\n[1]" + RESET + BOLD +"Cadastrar Item " + YELLOW + "\n[2]" + RESET + BOLD +"Remover Item " + YELLOW +"\n[3]" + RESET +BOLD +"Listar Produtos"+ YELLOW +" \n[4]"+ RESET +BOLD + "Encerrar \n");
            System.out.print(BOLD + "Digite aqui: "+ RESET);
            int option = scanner.nextInt();
            scanner.nextLine();
            if (option == 1) {
                //NOME DO PRODUTO
                System.out.println(BOLD + "Digite o nome do produto: "+ RESET);
                String nome = scanner.nextLine();

                //DESCRIÇÃO DO PRODUTO
                System.out.println(BOLD + "Digite a descrição do produto: "+ RESET);
                String descricao = scanner.nextLine();

                //VALOR DO PRODUTO
                System.out.println(BOLD + "Digite o valor do produto: "+ RESET);
                double valor = Double.parseDouble(scanner.nextLine());

                //DISPONIBILIDADE
                String disp;
                while (true) {
                    System.out.println(BOLD + "Disponível para venda? [s]Sim/[n]Não"+ RESET);
                    disp = scanner.nextLine();
                    if (disp.equalsIgnoreCase("s") || disp.equalsIgnoreCase("n")) {
                        break;
                    } else {
                        System.out.println(RED + BOLD + "Resposta inválida!"+ RESET);
                    }
                }
                Produto produto = new Produto(nome, descricao, valor, disp.equalsIgnoreCase("s"));
                produtos.add(produto);
                salvarProdutos(produtos);
                System.out.println(BOLD + "Produto cadastrado: " + RESET + produto);


            } else if (option == 2) {
                if (produtos.isEmpty()) {
                    System.out.println(RED + BOLD +"Nenhum produto cadastrado."+ RESET);
                } else {
                    System.out.println(BOLD +"Produtos cadastrados: "+ RESET);
                    for (int i = 0; i < produtos.size(); i++) {
                        System.out.println((i + 1) + ": " + produtos.get(i).getNome());
                    }

                    System.out.println(BOLD +"Qual item você quer remover? "+ RESET);
                    int remove = scanner.nextInt() - 1;
                    scanner.nextLine();
                    if (remove >= 0 && remove < produtos.size()) {
                        produtos.remove(remove);
                        salvarProdutos(produtos);
                        System.out.println(GREEN + BOLD +"Item removido com sucesso!" + RESET);
                    } else {
                        System.out.println( RED + BOLD + "Número inválido!" + RESET);
                    }
                }
            } else if (option == 3) {
                Collections.sort(produtos);
                System.out.println(BOLD +"\nVocê cadastrou os seguintes itens (ordenados pelo valor): \n"+ RESET);

                int contador = 1;
                for (Produto produto : produtos) {
                    System.out.println(YELLOW + BOLD + contador + RESET + "-" + produto);
                    contador++;
                }


            } else if (option == 4) {
                break;
            } else {
                System.out.println(RED + BOLD +"Resposta inválida!"+ RESET);
            }
        }
    }
    private static void salvarProdutos(ArrayList<Produto> produtos){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))){
            for(Produto produto : produtos){
                writer.write(produto.toFileString());
                writer.newLine();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void carregarProdutos(ArrayList<Produto> produtos){
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))){
            String line;
            while((line = reader.readLine()) != null){
                produtos.add(Produto.fromFileString(line));
            }
        }catch(IOException e){

        }
        System.out.println("Diretório de trabalho atual: " + new File(".").getAbsolutePath());
    }
}