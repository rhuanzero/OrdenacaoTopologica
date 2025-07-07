package br.unirio;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Main
{
	public static void main(String args[])
	{
		OrdenacaoTopologica ord = new OrdenacaoTopologica();
	
		Scanner in = new Scanner(System.in);
		
		String escolha = null;
		boolean ok = true;
        do { 
            try {
                escolha = "";
                System.out.println("Ordenação Topologica\n1 - Realizar Testes  \n2 - Gerar grafos aciclicos artificiais\n3 - Sair ");
                escolha = in.nextLine();

                switch (VerificadorEscolha.VerificarEscolha(escolha, "4")) {
                    case "1":
                    	System.out.println("Insira o tamanho do grafo:");
                		String entrada = in.nextLine();
                	
                    	ord.realizaLeitura(entrada);
                    	System.out.println("Leitura realizada!");
                    	if(!ord.executa())
                			System.out.println("O conjunto nao é parcialmente ordenado.");
                		else
                			System.out.println("O conjunto é parcialmente ordenado.");
                    	ok = false;
                    	break;
                       
                    case "2":
                    	System.out.println("Insira o n de vertices: ");
                    	int i = in.nextInt();
                    	
						try {
							GeradorGrafos.gerarGrafo(i);
						} catch (IOException e) {
							e.printStackTrace();
						}  	
                        System.out.println("Grafos gerados!");
                        break;
                    case "3":
                    	ok = false;
                }
            } catch (EscolhaInvalException e) {
                System.out.println(e.getMessage());
            }
        } while (ok);
        
        in.close();
		
		
		
		
		

		
	}
}
