package br.unirio;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class OrdenacaoTopologica
{
	long tempoExecução, tempoLeitura; // Para marcar o tempo de cada teste
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

		@Override
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

		@Override
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
		tempoLeitura = System.currentTimeMillis();
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
		
		tempoLeitura = System.currentTimeMillis() - tempoLeitura;
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
		if(elox == null) {
			elox = insereFinal(prim,x);
		}

		// Se y não nao existir
		if(eloy == null ) {
			eloy = insereFinal(prim,y);
		}

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

		
		while(p!=null) {
			if(p.chave == x)
				return p;
			
			p = p.prox;
		}
		return null;

	}


	private Elo insereFinal(Elo ult,int x) { // Insere no final e incrementa o contador n da classe ordemTopologica (Complexidade O(N))

		if(ult == null) { // Primeira inserção
			prim = new Elo(x); // Inicializa o Elo com a chave sendo x
			n++;
			return prim;
		}

		
		while(ult.prox != null) 
			ult = ult.prox;
		
		
		
		Elo p = new Elo(x);
		ult.prox = p;
		n++;
		return p;
		
	}

	/* Método para impressão do estado atual da estrutura de dados. */
	private void debug()
	{
		System.out.println("Debug");
	
		Elo p = prim;
		while (p != null) {
			System.out.print(p + " predecessores: " + p.contador + ", sucessores: ");
			imprimeEloSuc(p.listaSuc);
			p = p.prox;
		}
	}

	
	private void imprimeEloSuc(EloSuc p) {
	
		while (p != null) {
			System.out.print(p.id + "->");
			p = p.prox;
		}
		System.out.println("NULL"); 
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



	public void imprimirElo() {
		if(prim == null) {
			System.out.println("Lista vazia ou nula.");
			return;
		}

		Elo p = prim;
		while (p != null) {
			System.out.print(p + "->");
			p = p.prox;
		}
		System.out.println("NULL");
	}

	private boolean isParcialmenteOrdenado() {
		return this.n ==0;
	}

	private void gerarRelatorio() { // Gera relatorio do tempo

		long duracao = System.currentTimeMillis()-tempoExecução;
	
		String linhaLog = "Duração: "+duracao+"ms / N de vértices do grafo: "+numVertices+" / Tempo de leitura:"+tempoLeitura+"ms \n"; // Calcula a duração do teste realizado

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
		tempoExecução = System.currentTimeMillis(); // Guarda o inicio do teste
		debug();
		buscaEloSemPred();
		gerarSaida();
		System.out.println();
		gerarRelatorio();


		return isParcialmenteOrdenado();


	}


}
