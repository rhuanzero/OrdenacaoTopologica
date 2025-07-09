package br.unirio;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main
{
	public static void main(String args[])
	{
		Scanner in = new Scanner(System.in);

		System.out.print("Insira o número de vértices: ");
		
		String entrada = in.nextLine();
		
		long t0 = System.currentTimeMillis();
		for(int i = 0; i < 10; i++) {
			OrdenacaoTopologica ord = new OrdenacaoTopologica();	
			try {
				GeradorGrafos.gerarGrafo(Integer.valueOf(entrada));
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
			
			ord.realizaLeitura(entrada);
			
			if(!ord.executa()) {
				System.out.println("O conjunto nao é parcialmente ordenado.");
			} else {
				System.out.println("O conjunto é parcialmente ordenado.");
			}
		}
	
		long t1 = System.currentTimeMillis();
		
		long total = t1 - t0;

        String linhaLog = "Grafo de " + entrada + " vértices gerado em " + total + " ms";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/br/unirio/resultados.txt", true))) {
            writer.write(linhaLog);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(linhaLog);
		
        in.close();

	}
}
