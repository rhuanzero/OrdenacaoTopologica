package br.unirio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GeradorGrafos {

	public static void gerarGrafo(int nVertices) throws IOException {
        int maxArestas = (nVertices * (nVertices - 1)) / 2;

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter("src/entradas/" + nVertices + ".txt"))) {
            
            // Gera uma ordem aleatória dos vértices
            List<Integer> vertices = new ArrayList<>();
            for (int i = 1; i <= nVertices; i++) {
                vertices.add(i);
            }
            Collections.shuffle(vertices); // Embaralha os vértices

            // Gera arestas possíveis com base na ordem aleatória
            List<String> arestasPossiveis = new ArrayList<>();
            for (int i = 0; i < nVertices; i++) {
                for (int j = i + 1; j < nVertices; j++) {
                    int x = vertices.get(i);
                    int y = vertices.get(j);
                    arestasPossiveis.add(x + " < " + y);
                }
            }

            // Embaralha as arestas possíveis (opcional)
            Collections.shuffle(arestasPossiveis);

            // Escreve todas as arestas no arquivo (ou limite se quiser menos)
            for (int i = 0; i < arestasPossiveis.size(); i++) {
                escritor.write(arestasPossiveis.get(i) + "\n");
            }
        }
    }
}
	     





