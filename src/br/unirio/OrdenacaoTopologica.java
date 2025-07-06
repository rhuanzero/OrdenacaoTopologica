package br.unirio;
import java.util.Random;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class OrdenacaoTopologica
{
	long tempoInicial; // Para marcar o tempo de cada teste
	String numVertices;
	private class Elo
	{
		/* Identifica��o do elemento. */
		public int chave;
		
		/* N�mero de predecessores. */
		public int contador;
		
		/* Aponta para o pr�ximo elo da lista. */
		public Elo prox;
		
		/* Aponta para o primeiro elemento da lista de sucessores. */
		public EloSuc listaSuc;
		
		public Elo()
		{
			prox = null;
			contador = 0;
			listaSuc = null;
		}
		
		public Elo(int chave) { // Inicializa o Elo com a chave sendo x e o contador == 0
			prox = null;
			this.chave = chave;
			contador = 0;
			listaSuc = null;
		}
		
		public Elo(int chave, int contador, Elo prox, EloSuc listaSuc)
		{
			this.chave = chave;
			this.contador = contador;
			this.prox = prox;
			this.listaSuc = listaSuc;
		}
		
		public String toString() {
			return ""+chave;
		}
	}
	
	private class EloSuc
	{
		/* Aponta para o elo que � sucessor. */
		public Elo id; 
		
		/* Aponta para o pr�ximo elemento. */
		public EloSuc prox;
		
		public EloSuc()
		{
			id = null;
			prox = null;
		}
		
		public EloSuc(Elo id) {// Construtor adicionado
			this.id = id; 
			this.prox = null;
		}
		
		public EloSuc(Elo id, EloSuc prox)
		{
			this.id = id;
			this.prox = prox;
		}
		
		public String toString() {
			return ""+id.chave;
		}
	}


	/* Ponteiro (refer�ncia) para primeiro elemento da lista. */
	private Elo prim;
	
	/* N�mero de elementos na lista. */
	private int n;
		
	public OrdenacaoTopologica()
	{
		prim = null;
		n = 0;
	}
	
	/* M�todo respons�vel pela leitura do arquivo de entrada. */
	
	public void realizaLeitura(String nomeEntrada) { // Complexidade O(n)
		tempoInicial = System.currentTimeMillis(); // Guarda o inicio do teste
		numVertices = nomeEntrada;
		File arquivo = new File("src/entradas/"+nomeEntrada+".txt");
		Scanner in = null;
		
		try {
			 in = new Scanner(arquivo);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while(in.hasNextLine()) {
			realizaLeituraAux(in.nextLine());
		}
	}
	
	
	
	private void realizaLeituraAux(String parChave)
	{   
		// x < y
		String[] entrada = parChave.split("<");
		
		int x = Integer.valueOf(entrada[0].trim());
		int y = Integer.valueOf(entrada[1].trim());
		
		Elo elox = buscarElo(prim,x);
		Elo eloy = buscarElo(prim,y);
		
		// Se x não existir
		if(elox == null)
			elox = insereFinal(prim,x);
		
		// Se y não nao existir
		if(eloy == null )
			eloy = insereFinal(prim,y);
		
		atualizarDados(elox,eloy);
	}
	
	private void atualizarDados(Elo x, Elo y) { // Atualiza os dados de x e y  / Complexidade - O(1)
		// Atualizando dados de X
		if(x.listaSuc == null) { // Primeira inserção
			x.listaSuc = new EloSuc(y);
		}
		else { // Demais inserções
			EloSuc aux = x.listaSuc; // Primeiro sucessor da lista
			x.listaSuc = new EloSuc(y); // Insere o novo elemento no inicio
			x.listaSuc.prox = aux; // Faz o novo elemento apontar para o antigo primeiro da lista
		}
		y.contador++; // Atualização de dados do y
	}
	
	private Elo buscarElo(Elo p,int x){ // Busca e retorna um elo / Complexidade - O(n)
		
		if(p == null) 
			return null;
		
		if(p.chave == x) 
			return p;
		
		return buscarElo(p.prox,x);
		
	}
	
	
	private Elo insereFinal(Elo ult,int x) { // Insere no final e incrementa o contador n da classe ordemTopologica (Complexidade O(N))
		
		if(ult == null) { // Primeira inserção
			prim = new Elo(x); // Inicializa o Elo com a chave sendo x
			n++;
			return prim;
		}
		
		if(ult.prox == null) {
			Elo p = new Elo(x);
			ult.prox = p;
			n++;
			return p;
		}
	
		return insereFinal(ult.prox, x);
	}

	/* M�todo para impress�o do estado atual da estrutura de dados. */
	private void debug()
	{
		System.out.println("Debug");
		debug(prim);
	}
	
	private void debug(Elo p) {
		if(p == null) {
			return;
		}
		
		System.out.print(p+" predecessores: "+p.contador+", sucessores: ");
		imprimeEloSuc(p.listaSuc);

		debug(p.prox);
	}
	
	private void imprimeEloSuc(EloSuc p) {
		if(p == null) {
			System.out.println("NULL");
			return;
		}
		
		System.out.print(p.id+"->");
		
		imprimeEloSuc(p.prox);
	}
	
	private void buscaEloSemPred() { // Complexidade O(n) busca elementos sem predecessores
		Elo p = prim; // Armazena a lista antiga
		prim = null; // Utiliza a lista corrente para armazenar a lista ordenada topologicamente
		
		while(p != null) {
			Elo q = p;
			p = q.prox;
			if(q.contador == 0) {
				// Insere q na nova Cadeia no começo
				q.prox = prim;
				
				//Atualiza o q como primeiro da lista pois coloca o novo elemento no início.
				prim = q;
			}
		}
	}
	
	//Metódo responsável por gerar saída parcialmente ordenada.
	private void gerarSaida() { //Complexidade: O(n+m)
		
		// Utilizando um elo auxiliar para guardar referência ao começo da lista
		Elo q = prim;
		
		//Utilizando um elo auxiliar para guardar o ultimo da lista
		Elo ult = null;
		
		//Verificando se a lista está vazia
		if(prim != null) {
			
			// Usando a referência do primeiro para chegar no final da lista.
			ult = prim;
			
			// Guardando referência ao último elemento da lista.
			while(ult.prox != null) { 
					ult = ult.prox;
			}
		}
		
		//Imprimindo de acordo com a saída esperada no enunciado.
		System.out.println("Ordenação Topológica:");
		
		//Estrutura repetitiva responsável por percorrer a lista com zero predecessores.
		while(q != null) {
			
			//Imprimindo chave corrente
			System.out.print(q.chave + " ");
			
			//Decrementando o número de elementos na lista.
			n--;
			
			//Referência do começo da lista sendo passada para o próximo da lista, descartando o elemento q corrente.
			prim = q.prox;
			
			//Estrutura repetitiva utilizada para percorrer a lista sucessiva.
			for(EloSuc t = q.listaSuc; t != null; t = t.prox) {
				
				//Decrementando o número de predecessores dos elementos da lista sucessiva.
				t.id.contador--;
				
				//Verificando se o número de predecessores é 0.
				if(t.id.contador == 0) {
					
					//Verificando se a lista está vazia.
					if (prim == null) {
						
						//Se a lista estiver vazia, o próximo elemento da lista sucessiva vai ser o primeiro da lista com zero predecessores.
						prim = t.id;
						ult = t.id;
						//Garante que o nó não traga outra lista com ele, causando um encadeamento indesejado.
						t.id.prox = null;
					} 
					
					// Se a lista não estiver vazia.
					else {
						
						// O(1) para colocar no fina da lista.
						ult.prox = t.id;
						
						// Atualizando o elo auxiliar para apontar para o último da lista.
						ult = t.id;
						
						//Garante que o nó não traga outra lista com ele, causando um encadeamento indesejado.
						t.id.prox = null;
						}
					
					//Eliminando elemento com 0 predecessores da lista de sucessores
					q.listaSuc = q.listaSuc.prox;
				}
			}
			// Indo para o próximo elemento da list, descartando o anterior.
			q = prim;
		}
	}

	//Gerador de numeros aleatorios
	Random nAleatorio  = new Random();

	//Vertice ainda nao visitado
	private static final int NAO_VISITADO = 0;
	//Vertice esta sendo visitado
	private static final int VISITANDO = 1;
	//Vertice ja foi visitado
	private static final int VISITADO = 2;

	//Metodo para gerar um grafo direcionado aciclico de N vertices
	private void gerarGrafo(int nVertices){
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
				/*if(y == x) {
					tentativasDeAdicionar++;
					continue;
				}*/
				
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
	private boolean ehAciclico(int x, int y, boolean[][] aresta, int nVertices) {

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
	private boolean contemCiclo(boolean[][] aresta, int nVertices) {
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
	private boolean buscaNoGrafo(boolean[][] aresta, int nVertices, int u,  int[] estados){ // Complexidade O(n^2)
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
	}

	public void imprimirElo() {
		if(prim == null)
			return;
		
		imprimirElo(prim);
	}
	
	public void imprimirElo(Elo p) {
		if(p == null) {
			System.out.println("NULL");
			return;
		}
		System.out.print(p+"->");
		imprimirElo(p.prox);
	}
	
	private boolean isParcialmenteOrdenado() {
		return this.n ==0;
	}
	
	private void gerarRelatorio() { // Gera relatorio do tempo
		
		long duracao = System.currentTimeMillis()-tempoInicial;
		
		String linhaLog = "Duração: "+duracao+"ms / N de vértices do grafo: "+numVertices+"\n"; // Calcula a duração do teste realizado
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/br/unirio/resultados.txt", true))) {
		    writer.write(linhaLog); // Escreve uma nova Linha no arquivo resultados.txt
		    writer.newLine(); // Quebra a linha
		} catch (IOException e) {
		    e.printStackTrace();
		}
		System.out.println(linhaLog); // Imprime a linha atual
	}
	
	/* M�todo respons�vel por executar o algoritmo. */
	public boolean executa()
	{
	
		debug();
		buscaEloSemPred();
		gerarSaida();
		System.out.println();
		gerarRelatorio();
	
		
		return isParcialmenteOrdenado();
		
		
	}
	
	public void gerarEntradas() {
		gerarGrafo(5);
		gerarGrafo(10);
		gerarGrafo(20);
		gerarGrafo(30);
		gerarGrafo(40);
		gerarGrafo(50);
		gerarGrafo(100);
		gerarGrafo(200);
		gerarGrafo(500);
		gerarGrafo(1000);
		gerarGrafo(5000);
		gerarGrafo(10000);
		gerarGrafo(20000);
		gerarGrafo(30000);
		gerarGrafo(50000);
		gerarGrafo(100000);
	}
}
