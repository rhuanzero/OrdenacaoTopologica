package br.unirio;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main
{
	public static void main(String args[]) throws IOException

	{
		Scanner in = new Scanner(System.in);
		//System.out.print("Insira o número de vértices: ");
		//String[] entradas = {"10", "20", "30", "40", "50", "100", "200", "500", "1000", "5000", "10000", "20000","21000"}; 
		OrdenacaoTopologica ord = new OrdenacaoTopologica();
		String entrada = in.nextLine();
			System.out.println(entrada);
			
			long t0 = System.currentTimeMillis();
			for(int j = 0; j < 10; j++) {
				ord.realizaLeitura(entrada);
				if(!ord.executa()) {
					System.out.println("O conjunto nao é parcialmente ordenado.");
				} else {
					System.out.println("O conjunto é parcialmente ordenado.");
				}
			}
			long t1 = System.currentTimeMillis();
			
			long total = (t1-t0)/10;
	
	        String linhaLog = "Grafo de " + entrada + " vértices executado em " + total + " ms";
	
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/br/unirio/resultados.txt", true))) {
	            writer.write(linhaLog);
	            writer.newLine();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	
	        System.out.println(linhaLog);
		
		
		
	}
}
