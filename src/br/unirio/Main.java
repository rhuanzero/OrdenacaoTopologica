package br.unirio;
import java.io.IOException;
import java.util.Scanner;

public class Main
{
	public static void main(String args[])
	{
		OrdenacaoTopologica ord = new OrdenacaoTopologica();

		Scanner in = new Scanner(System.in);

		String entrada = in.nextLine();
	
		for(int i = 0;i<9;i++) {
			ord = new OrdenacaoTopologica();

			ord.realizaLeitura(entrada);
			if(!ord.executa()) {
				System.out.println("O conjunto nao é parcialmente ordenado.");
			} else {
				System.out.println("O conjunto é parcialmente ordenado.");
			}}
		



        in.close();







	}
}
