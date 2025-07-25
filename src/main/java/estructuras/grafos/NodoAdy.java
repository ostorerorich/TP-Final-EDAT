package estructuras.grafos;

public class NodoAdy {
    
    // Atributos
    private NodoVert vertice;
    private NodoAdy sigAdyacente;
    private int etiqueta;
    
    // Constructor
    public NodoAdy(NodoVert unVertice, NodoAdy unSigAdyacente, int unaEtiqueta){
        this.vertice = unVertice;
        this.sigAdyacente = unSigAdyacente;
        this.etiqueta = unaEtiqueta;
    }
    
   // Visualizadores
   public NodoVert getVertice(){
       return this.vertice;
   }
   
   public NodoAdy getSigAdyacente(){
       return this.sigAdyacente;
   }
   
   public int getEtiqueta(){
       return this.etiqueta;
   }
   
   // Modificadores
   public void setVertice(NodoVert unVertice){
       this.vertice = unVertice;
   }
   
   public void setSigAdyacente(NodoAdy unSigAdyacente){
       this.sigAdyacente = unSigAdyacente;
   }
   
   public void setEtiqueta(int unaEtiqueta){
      this.etiqueta = unaEtiqueta;
   }
    
}
