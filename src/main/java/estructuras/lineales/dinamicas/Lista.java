package estructuras.lineales.dinamicas;

public class Lista {
    private Nodo cabecera;
    private int longitud;

    public Lista() {
        this.cabecera = null;
        this.longitud = 0;
    }

    public boolean insertar(Object element, int pos){
        boolean res = true;

        if(pos < 1 || pos > this.longitud + 1){
            res = false;
        } else {
            if(pos == 1){
                this.cabecera = new Nodo(element, cabecera);
            }else{
                Nodo aux = this.cabecera;
                int i = 1;
                while(i< pos-1){
                    aux = aux.getEnlace();
                    i++;
                }
                Nodo nuevo = new Nodo(element, aux.getEnlace());
                aux.setEnlace(nuevo);
            }
            this.longitud++;
        }

        return res;
    }

    public boolean eliminar(int pos){
        boolean res = true;

        if(pos < 1 || pos > this.longitud){
            res = false;
        }else{
            if(this.cabecera != null){
                if(pos == 1){
                    this.cabecera = cabecera.getEnlace();
                } else {
                    Nodo aux = this.cabecera;
                    int i = 1;
                    while(i< pos-1){
                        aux = aux.getEnlace();
                        i++;
                    }
                    aux.setEnlace(aux.getEnlace().getEnlace());
                }
                this.longitud--;
            }
        }
        return res;
    }

    public Object recuperar(int pos){
        Object res = null;
        if(this.cabecera != null){
            if(pos == 1){
                res = cabecera.getElemento();
            }else if(pos <= this.longitud){
                Nodo aux = this.cabecera;
                int i = 1;
                while(i < pos){
                    aux = aux.getEnlace();
                    i++;
                }
                res = aux.getElemento();
            }
        }
        return res;
    }

    public int localizar(Object element){
        Nodo aux = this.cabecera;
        int pos = -1;
        int i = 1;
        boolean find = false;

        while(i<=this.longitud && !find){
            if(aux.getElemento().equals(element)){
                pos = i;
                find = true;
            }
            aux = aux.getEnlace();
            i++;
        }


        return pos;
    }
    public int longitud(){
        return this.longitud;
    }

    public boolean esVacia(){
        return this.longitud == 0;
    }

    public void vaciar(){
        this.cabecera = null;
        this.longitud = 0;
    }

    public Lista clone(){
        Lista clon = new Lista();

        if(this.cabecera != null){
            clon.cabecera = new Nodo(this.cabecera.getElemento(), null);
            Nodo aux = this.cabecera.getEnlace();
            Nodo auxClon = clon.cabecera;

            while(aux != null){
                Nodo nuevo = new Nodo(aux.getElemento(), null);
                auxClon.setEnlace(nuevo);
                auxClon = nuevo;
                aux = aux.getEnlace();
            }

            clon.longitud = this.longitud;

        }

        return clon;
    }

    @Override
    public String toString(){
        String res = "[";

        if (this.cabecera != null) {
            Nodo aux = this.cabecera;
            while (aux != null) {
                res += aux.getElemento();
                aux = aux.getEnlace();
                if (aux != null) {
                    res += ",";
                }
            }

        }

        res += "]";
        return res;
    }
}
