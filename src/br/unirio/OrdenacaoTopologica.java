package br.unirio;

public class OrdenacaoTopologica
{
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
	public void realizaLeitura(String nomeEntrada)
	{   
		// x < y
		String[] entrada = nomeEntrada.split("<");
		
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
		
		while(p!=null) {
			Elo q = p;
			p = q.prox;
			if(q.contador==0) {
				// Insere q na nova Cadeia
				q.prox = prim;
				prim = q;
			}
		}
	}
	
	
	/* M�todo respons�vel por executar o algoritmo. */
	public boolean executa()
	{
		debug();
		buscaEloSemPred();
		return false;
	}
}