package RepresentacaoComp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Grafo {

	// Atributos do Grafo
	private ArrayList<Vertice> vertices; //Lista que contêm todos os vértices do Grafo
	private ArrayList<Aresta> arestas; //Lista que contêm todas as arestas do Grafo

	// Atributos usados na funcao encontrarMenorCaminho (Djikstra)
	List<Vertice> menorCaminho = new ArrayList<Vertice>(); // Lista que guarda os vertices pertencentes ao menor caminho encontrado
	
	List<Vertice> naoVisitados = new ArrayList<Vertice>(); // Lista dos vertices que ainda nao foram visitados
	
	Vertice verticeCaminho;	 // Variavel Vertice que recebe os vertices pertencentes ao menor caminho

	Vertice atual; // Variavel Vertice que guarda o vertice que esta sendo visitado

	Vertice vizinho; // Variavel que marca o vizinho do vertice atualmente visitado	(Vertice atual)
	

	// Método Construtor
	public Grafo() {
		// Inicialização dos Arrays
		this.vertices = new ArrayList<Vertice>();
		this.arestas = new ArrayList<Aresta>();
	}

	// Adiciona um Vertice no Grafo
	public void adicionarVertice(int id, String nome) { 
		Vertice novoVertice = new Vertice(id, nome);
		this.vertices.add(novoVertice); //--> Adiciona no ArrayList<Vertice> de Vertice
	}

	// Adiciona Arestas Ponderadas no Grafo
	public void adicionarAresta(int id, int verticeInicioId, int verticeFimId, int peso) { 
		Vertice verticeInicial = this.getVertice(verticeInicioId); // Procura o Vertice(metodo GetVertice) que contém esse Id na Lista
		Vertice verticeFinal = this.getVertice(verticeFimId);
		Aresta aresta = new Aresta(id, verticeInicial, verticeFinal, peso); //Criou(Instanciou) a Aresta
		verticeInicial.adicionarArestaSaida(aresta);
		verticeInicial.adicionarVerticeSaida(verticeFinal);
		verticeFinal.adicionarArestaEntrada(aresta);
		verticeFinal.adicionarVerticeEntrada(verticeInicial);
		this.arestas.add(aresta); //--> Adiciona no ArrayList<Aresta> de Aresta
	}

	// Procura o Vértice no Grafo e retorna - o parâmetro é o id do vertice
	public Vertice getVertice(int id) {
		Vertice verticeProcurado = null; // Inicializa null
		for (int i = 0; i < this.vertices.size(); i = i + 1) {
			if (this.vertices.get(i).getId() == id) {
				verticeProcurado = this.vertices.get(i); // Achou o vértice
				break;// Quebra o loop
			}
		}
		return verticeProcurado;
	}

	
	// MÉTODO: LISTA DE ADJACÊNCIA
	public void imprimeListaAdj() {
		int i;
		System.out.println("--- LISTA DE ADJACÊNCIA ---");
		for (i = 0; i < vertices.size(); i = i + 1) {
			System.out.println("adj " + vertices.get(i) + ": " + vertices.get(i).getVerticeSaida());
		}
	}

	// MÉTODO: BUSCA EM LARGURA
	public void buscaEmLargura(int verticePartida, int verticeChegada) { // id do Vertice de partida , id do Vertice final
		System.out.println("---BUSCA EM LARGURA ---");																	
		ArrayList<Vertice> marcados = new ArrayList<Vertice>(); //Array de vertices marcados
		ArrayList<Vertice> fila = new ArrayList<Vertice>();//Array da Fila de vertices que serão visitados
		Vertice verticeAtual = this.vertices.get(verticePartida - 1); // esse é o Vértice Atual
		marcados.add(verticeAtual); //--> Adiciona o vertice atual na Lista de Vertices marcados
		System.out.println("id do Vértice Atual Inicial: " + verticeAtual.getId() + ", Nome: " + verticeAtual.getNome());
		fila.add(verticeAtual);//--> Adiciona o vertice atual na Fila
		while (fila.size() > 0) {
			Vertice visitado = fila.get(0); // Primeiro elemento a ser visitado da Fila
			for (int i = 0; i < visitado.getArestasSaida().size(); i = i + 1) {
				Vertice proximoVertice = visitado.getArestasSaida().get(i).getVerticeFim();
				if (!marcados.contains(proximoVertice)) { // Se o vértice ainda não foi marcado
					marcados.add(proximoVertice);//--> Adiciona o próximo Vértice no Array de vertices marcados
					// System.out.println("id do Próximo Vértice: " + verticeAtual.getId() + ",
					// Nome: " + verticeAtual.getNome());
					System.out.println("Visita " + proximoVertice.getId() + ", Nome: " + proximoVertice.getNome());
					fila.add(proximoVertice); //--> Adiciona o próximo Vértice no Array de vertices que serão visitados
				}
			}
			if (visitado.getId() == verticeChegada - 1) { // if bipolar
				break;
			}
			fila.remove(0);
		}
	}

	//MÉTODO: ALGORITMO DE DIJKSTRA
	public List<Vertice> encontrarMenorCaminhoDijkstra(int idVertice1, int idVertice2) { 
		Vertice v1 = getVertice(idVertice1); //v1 = Vértice do id passado como parâmetro --> Vértice de partida
		Vertice v2 = getVertice(idVertice2); //v2 = ... --> Vértice de chegada

		// Adiciona a origem na lista do menor caminho
		menorCaminho.add(v1); //--> Adiciona no ArrayList<Vertice> dos vértices de menor caminho (é o que queremos)

		// Colocando/settando as distancias iniciais
		for (int i = 0; i < this.vertices.size(); i++) { //Percorre todos os vértices do Array
			// Vertice Inicial tem distancia zero
			if (this.vertices.get(i).getId() == v1.getId()) {
				this.vertices.get(i).setDistancia(0);

			} else { // e todos os outros, distancia = 99999(em tese "infinita")
				this.vertices.get(i).setDistancia(99999);
			}
			// Insere todos os vertices na lista de vertices nao visitados
			this.naoVisitados.add(this.vertices.get(i));
		}
		Collections.sort(naoVisitados); // ORDENAÇÃO DOS VÉRTICES DESSE ARRAY (parâmetro da ordenação é o atributo distancia)

		// O algoritmo continua ate que todos os vértices sejam visitados
		while (!this.naoVisitados.isEmpty()) {
			// Pega-se sempre o vertice com menor distancia, que é o primeiro da lista(já que foi ordenado)

			atual = this.naoVisitados.get(0);
			// System.out.println("Pegou o vertice: " + atual + ", Distancia: " + atual.getDistancia() + "m");

			// Para cada vizinho (cada aresta), calcula-se a sua possivel distancia, somando a distancia 
			// do vertice atual com o peso da aresta até esse vizinho correspondente. Se essa distancia calculada
			// do vizinho for menor que a distancia ATUAL do vizinho , entao essa distancia do vizinho é atualizada.

			for (int i = 0; i < atual.getArestasSaida().size(); i++) {
				vizinho = atual.getArestasSaida().get(i).getVerticeFim();
				// System.out.println("Olhando o vizinho de " + atual + ": " + vizinho);
				if (!vizinho.verificarVisita()) { // Se vizinho não tiver sido visitado ainda
					// Comparando a distância do vizinho com a possível distância
					if (vizinho.getDistancia() > (atual.getDistancia() + atual.getArestasSaida().get(i).getPeso())) {
						vizinho.setDistancia(atual.getDistancia() + atual.getArestasSaida().get(i).getPeso());
						vizinho.setPai(atual);

						// Se o vizinho é o vertice procurado(v2->final), e foi feita uma mudanca na distancia, a
						// lista com o menor caminho anterior eh apagada, pois existe um caminho menor vertices pais, 
						//ate o vertice inicial.

						if (vizinho.equals(v2)) {
							menorCaminho.clear();
							verticeCaminho = vizinho;
							menorCaminho.add(vizinho);
							while (verticeCaminho.getPai() != null) {
								menorCaminho.add(verticeCaminho.getPai());
								verticeCaminho = verticeCaminho.getPai();
							}
							// Ordena a lista do menor caminho, para que ele seja exibido da origem ao destino.
							Collections.sort(menorCaminho); // ORDENAÇÃO DOS VÉRTICES QUE FORMAM O MENOR CAMINHO
						}
					}
				}
			}
			// Marca o vertice atual como visitado e o retira da lista de nao visitados
			atual.visitar();
			this.naoVisitados.remove(atual);

			// Ordena a lista, para que o vertice com menor distancia fique na primeira posicao
			Collections.sort(naoVisitados);
			// System.out.println("Nao foram visitados ainda:"+naoVisitados);
		}
		System.out.println("--- ALGORITMO DE DJIKSTRA ---");
		System.out.println("Menor rota: " + menorCaminho);
		System.out.println("Distância do Percurso: " + menorCaminho.get(menorCaminho.size() - 1).getDistancia() + "m");
		return menorCaminho;
	}

}
