package estructuras.lineales.dinamicas;

public class Nodo {
    private Object elemento;
    private Nodo enlace;

    public Nodo(Object elemento, Nodo enlace) {
        this.elemento = elemento;
        this.enlace = enlace;
    }

    public void setElemento(Object elemento) {
        this.elemento = elemento;
    }

    public void setEnlace(Nodo enlace) {
        this.enlace = enlace;
    }

    public Nodo getEnlace() {
        return this.enlace;
    }

    public Object getElemento() {
        return this.elemento;
    }

}
