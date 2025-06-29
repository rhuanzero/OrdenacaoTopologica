package br.unirio;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main
{
	public static void main(String args[])
	{
		OrdenacaoTopologica ord = new OrdenacaoTopologica();
		File arquivo = new File("src/br/unirio/entrada.txt");
		Scanner in = null;
		
		try {
			 in = new Scanner(arquivo);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while(in.hasNextLine()) {
			ord.realizaLeitura(in.nextLine());
		}
		
		ord.executa();
		
		
		
		
		
		
	
		/*
		
		
		ord.realizaLeitura(nomeEntrada);

		if(!ord.executa())
			System.out.println("O conjunto nao � parcialmente ordenado.");
		else
			System.out.println("O conjunto � parcialmente ordenado.");*/
	}
}
