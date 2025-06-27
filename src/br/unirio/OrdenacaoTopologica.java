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
		
		public Elo(int chave, int contador, Elo prox, EloSuc listaSuc)
		{
			this.chave = chave;
			this.contador = contador;
			this.prox = prox;
			this.listaSuc = listaSuc;
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
		if(elox == null) {
			if(prim == null){ // Primeira inserção
				prim = new Elo();
				prim.chave = x;
				prim.contador++;
				elox = prim;
			}
			else { // Demais inserções
				Elo ult;
				
				for (ult = prim;ult.prox!=null;ult = ult.prox); // Encontra o ultimo elo antes de NULL
				elox = new Elo();
				elox.chave = x;
				elox.contador++;
				ult.prox = elox;
			}
		}
		
		// Se y não nao existir
		if(eloy == null ) { // Primeira inserção
			if(prim == null) {
				prim = new Elo();
				prim.chave = y;
				prim.contador++;
				eloy = prim;
			}
			
			else { // Demais inserções
				Elo ult;
				
				for (ult = prim;ult.prox!=null;ult = ult.prox); // Encontra o ultimo elo antes de NULL
				eloy = new Elo();
				eloy.chave = y;
				eloy.contador++;
				ult.prox = eloy;
			
		}
		
		}
				
				
				
				
				
	}
	
	private void atualizarDados(Elo x, Elo y) {
		
		if(x.listaSuc == null) { // Primeira inserção
			x.listaSuc = new EloSuc();
			x.listaSuc.id = y;
		}
		else { // Demais inserções
			EloSuc novo = new EloSuc(y);
			novo.prox = x.listaSuc;
			x.listaSuc.id = novo.id;
			
	
			
		}
	
		y.contador++; // Atualização de dados do y
		
		
		
		
		
		
		
	}
	
	private Elo buscarElo(Elo p,int x){
		
		if(p == null) 
			return null;
		
		if(p.chave == x) 
			return p;
		
		return buscarElo(p.prox,x);
		
	}
	
	
	
	/* M�todo para impress�o do estado atual da estrutura de dados. */
	private void debug()
	{
		/* Preencher. */
	}
	
	/* M�todo respons�vel por executar o algoritmo. */
	public boolean executa()
	{
		/* Preencher. */
		
		return false;
	}
}