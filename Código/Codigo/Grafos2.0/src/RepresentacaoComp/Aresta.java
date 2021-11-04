package RepresentacaoComp;

public class Aresta {

	//Atributos da Aresta (Encapsulados)
	private int id;
	private int peso;
	private Vertice verticeInicio;
	private Vertice verticeFim;
	
	//Método Construtor
	public Aresta(int id, Vertice verticeInicio, Vertice verticeFim, int peso) {
		this.id = id;
		this.verticeInicio = verticeInicio;
		this.verticeFim = verticeFim;
		this.peso = peso;
	}
	
	//Métodos Acessores e Modificadores
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return this.id;
	}
	
	public void setPeso(int peso) {
		this.peso = peso;
	}
	public int getPeso() {
		return this.peso;
	}
	
	public void setVerticeInicio(Vertice verticeInicio) {
		this.verticeInicio = verticeInicio;
	}
	public Vertice getVerticeInicio() {
		return this.verticeInicio;
	}
	
	public void setVerticeFim(Vertice verticeFim) {
		this.verticeFim = verticeFim;
	}
	public Vertice getVerticeFim() {
		return this.verticeFim;
	}
	
	@Override
    public String toString(){
        return "Identificador: "+this.id+" \n"+
                "Peso:" +this.id+" \n"+
                "Origem: "+this.verticeInicio+" \n"+
                "Destino: "+this.verticeFim+" \n";
    }
}
