package br.unirio;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GeradorGrafos {



	    private static Random random = new Random();

	    public static void gerarGrafo(int nVertices) throws IOException {
	    	int nArestas = nVertices * 2;
	        try (BufferedWriter escritor = new BufferedWriter(new FileWriter("src/entradas/"+nVertices+".txt"))) {
	            List<Integer> vertices = new ArrayList<>();
	            for (int i = 1; i <= nVertices; i++) {
	                vertices.add(i);
	            }
	            Collections.shuffle(vertices);

	            int[] posicao = new int[nVertices + 1];
	            for (int i = 0; i < nVertices; i++) {
	                posicao[vertices.get(i)] = i;
	            }

	            int arestasGeradas = 0;
	            int tentativas = 0;
	            int maxTentativas = nArestas * 100;

	            while (arestasGeradas < nArestas && tentativas < maxTentativas) {
	                int u = random.nextInt(nVertices) + 1;
	                int v = random.nextInt(nVertices) + 1;

	                if (u != v && posicao[u] < posicao[v]) {
	                    escritor.write(u + " < " + v + "\n");
	                    arestasGeradas++;
	                }
	                tentativas++;
	            }
	        }
	    }


	    public static void main(String[] args) {
	    	Scanner in = new Scanner(System.in);
	    	
	    	
	    	int i = in.nextInt();
	    	
	    	try {
				GeradorGrafos.gerarGrafo(i);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
	    /*
	  //Gerador de numeros aleatorios
		static Random nAleatorio  = new Random();

		//Vertice ainda nao visitado
		private static final int NAO_VISITADO = 0;
		//Vertice esta sendo visitado
		private static final int VISITANDO = 1;
		//Vertice ja foi visitado
		private static final int VISITADO = 2;

		//Metodo para gerar um grafo direcionado aciclico de N vertices
		private static void  gerarGrafo(int nVertices){
			//Checando se o numero de vertices é igual ou menor que zero
			if(nVertices <= 0)
				return;


			//Definindo o numero de arestas do grafo como nVertices + 2
			final int N_ARESTAS = nVertices * 2;

			//Adicionando no arquivo.txt cada linha de adicao no grafo
			//Utilizando o BufferedWriter pois tera casos com milhares de vertices
			//O FileWriter true evita que algo sobescreva o arquivo
			try(BufferedWriter escritor = new BufferedWriter(new FileWriter("src/entradas/"+nVertices+".txt"))){

				int contadorDeArestasAdicionadas = 0;
				int tentativasDeAdicionar = 0;
				//Definimos um valor maximo de tenativos para evitar loops infinitos
				final int MAX_TENTATIVAS = N_ARESTAS * 100;
				//Matriz que armazena um x que tem aresta para um y
				boolean[][] aresta = new boolean[nVertices+1][nVertices+1];

				while(contadorDeArestasAdicionadas < N_ARESTAS || tentativasDeAdicionar < MAX_TENTATIVAS) {
					// x ira receber um numero aleatorio entre 1 e nVertices
					int x = nAleatorio.nextInt(nVertices) + 1;

					int y ;
					// y ira receber um numero aleatorio entre 1 e (nVertices) até ser diferente de x
					do {
		                y = nAleatorio.nextInt(nVertices) + 1;
		            } while (y == x);


					//Se y foi maior ou igual a x, ira incrementar y, para que todos os numeros dentro do intervalo tenham
					// a mesma probabilidade de ser sorteado
					//if(y == x) {
					//	tentativasDeAdicionar++;
					//	continue;
				//	}

					//Confere se a aresta ja existe, caso ja continua para procurar outra que nao existe
					if(aresta[x][y]){
						tentativasDeAdicionar++;
						continue;
					}

					//Se a aresta faz permanencer o grafo aciclico, adiciona no arquivo
					if(ehAciclico(x,y,aresta, nVertices)) {

						//Adicionando aresta
						aresta[x][y] = true;

						//Adicao no arquivo
						escritor.write(x + " < " + y + "\n");

						contadorDeArestasAdicionadas++;
					}

					tentativasDeAdicionar++;
				}

				} catch (IOException e) {
				System.err.println("Erro na escrita do arquivo: " + e.getMessage());
				}

		}

		//Metodo para conferir se a insercao x < y ira manter o grafo aciclico
		private static boolean ehAciclico(int x, int y, boolean[][] aresta, int nVertices) {

			//Recebe o estado atual de aresta e o salva
			boolean estadoOriginal = aresta[x][y];

			//De forma temporaria iremos ter essa aresta como true
			aresta[x][y] = true;

			//Checa se coma aresta temporiamente adicionada aagora faz um ciclo ter um grafo
			boolean temCiclo = contemCiclo(aresta, nVertices);

			//Restaura o estado original da aresta
			aresta[x][y] = estadoOriginal;

			//Caso nao tiver ciclo, ou seja, !temCiclo, continuaa aciclico
			//Caso temCiclo, logo e ciclico
			return !temCiclo;
		}

		//Metodo para verificar se com a aresta adicionada temporaria ira fazer o grafo ser ciclico
		private static boolean contemCiclo(boolean[][] aresta, int nVertices) {
			//Vetor para mapear os estado de cada vertice
			int[] estados = new int[nVertices+1];

			//Garantindo que todos vertices sejam verificados
			for(int i = 1; i <= nVertices; i++) {
				// Verificar se o vertice ainda nao foi visitado
				if(estados[i] == NAO_VISITADO) {
					if(buscaNoGrafo(aresta,nVertices, i, estados)){
						return true; //Ciclo encontrado
					}
				}
			}
			return false; // Nao foi encontrado nenhum ciclo em todo grafo durante as recursoes
		}

		//Metodo recursivo para buscar caso o vertice ja foi visitado ou nao
		private static boolean buscaNoGrafo(boolean[][] aresta, int nVertices, int u,  int[] estados){ // Complexidade O(n^2)
			//Indice atual como visitando, estando na recursao
			estados[u] = VISITANDO;

			for(int v = 1; v <= nVertices; v++) {
				if(aresta[u][v]) { //Se existe uma aresta x < y
					// y ainda nao foi visitado
					if(estados[v] == NAO_VISITADO) {
						if(buscaNoGrafo(aresta, nVertices, v, estados)){
							return true; // Se um ciclo for encontrado
						}
					}
					else if(estados[v] == VISITANDO) {
						return true;// tem ciclo, x < y, forma um ciclo
					}
				}
			}
			estados[u] = VISITADO;
			return false; // Nao foi encontrado nenhum ciclo
		}    */
	}





