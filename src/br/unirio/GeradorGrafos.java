package br.unirio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GeradorGrafos {

	public static void gerarGrafo(int nVertices) throws IOException {
	    int maxArestas = nVertices * (nVertices - 1) / 2;
	    Random rand = new Random();
	    
	    try (BufferedWriter escritor = new BufferedWriter(new FileWriter("src/entradas/"+nVertices + ".txt"))) {
	        // 1. Embaralha vértices com Fisher-Yates
	        int[] vertices = new int[nVertices];
	        for (int i = 0; i < nVertices; i++) vertices[i] = i + 1;
	        
	        for (int i = nVertices - 1; i > 0; i--) {
	            int j = rand.nextInt(i + 1);
	            int temp = vertices[i];
	            vertices[i] = vertices[j];
	            vertices[j] = temp;
	        }
	        
	        // 2. Gera arestas diretamente em vetores
	        int[] x = new int[maxArestas];
	        int[] y = new int[maxArestas];
	        int idx = 0;
	        
	        for (int i = 0; i < nVertices; i++) {
	            for (int j = i + 1; j < nVertices; j++) {
	                x[idx] = vertices[i];
	                y[idx] = vertices[j];
	                idx++;
	            }
	        }
	        
	        // 3. Embaralha arestas com Fisher-Yates
	        for (int i = maxArestas - 1; i > 0; i--) {
	            int j = rand.nextInt(i + 1);
	            // Troca x
	            int tempX = x[i];
	            x[i] = x[j];
	            x[j] = tempX;
	            // Troca y
	            int tempY = y[i];
	            y[i] = y[j];
	            y[j] = tempY;
	        }
	        
	        // 4. Escreve no arquivo
	        for (int i = 0; i < maxArestas; i++) {
	            escritor.write(x[i] + " < " + y[i] + "\n");
	        }
	    }
	}
}
	     





