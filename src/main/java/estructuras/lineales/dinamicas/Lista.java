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

    // Simulacro Ej: 1a.
    public Lista obtenerMultiplos(int num){
        int i = 1;
        Lista res = new Lista();
        Nodo aux = this.cabecera;
        Nodo ultimo = null;
        while(i<= this.longitud){
            if(i%num == 0){
                Nodo nuevo = new Nodo(aux.getElemento(), null);
                if(res.cabecera == null){
                    res.cabecera = nuevo;
                }else{
                    ultimo.setEnlace(nuevo);
                }
                ultimo = nuevo;
                res.longitud++;
            }
            i++;
            aux = aux.getEnlace();
        }
        return res;
    }

    // Simulacro Ej: 1b.
    public void eliminarApariciones(Object element){
        Nodo actual = this.cabecera;
        Nodo siguiente = null;
        while(actual != null){
            if(actual.getElemento().equals(element)){
                if(siguiente == null){
                    this.cabecera = actual.getEnlace();
                }else{
                    siguiente.setEnlace(actual.getEnlace());
                }
                this.longitud--;
            }else{
                siguiente = actual;
            }
            actual = actual.getEnlace();
        }
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


    // Parcial Punto 2: 2023
    public boolean moverAAnteultimaPosicion(int pos){
        boolean res = false;
        Nodo actual = this.cabecera;
        Nodo anterior = null;
        Nodo nodoAMover = null;
        int i = 1;

        if(pos >= 1 && pos <= this.longitud){
            if(this.longitud>1){
                if(this.longitud == 2 && pos == 2){
                    Nodo aux = this.cabecera;
                    this.cabecera = this.cabecera.getEnlace();
                    this.cabecera.setEnlace(aux);
                    aux.setEnlace(null);
                    res = true;
                } else if(pos != this.longitud - 1) {
                    if(pos == 1){
                        nodoAMover = this.cabecera;
                        this.cabecera = this.cabecera.getEnlace(); // quitamos
                    } else {
                        while(i < pos - 1){
                            actual = actual.getEnlace();
                            i++;
                        }
                        anterior = actual;
                        nodoAMover = anterior.getEnlace();
                        anterior.setEnlace(nodoAMover.getEnlace()); // lo sacamos
                    }

                    // Insertar en anteúltima posición
                    actual = this.cabecera;
                    i = 1;
                    while(i < this.longitud - 2){
                        actual = actual.getEnlace();
                        i++;
                    }

                    nodoAMover.setEnlace(actual.getEnlace());
                    actual.setEnlace(nodoAMover);
                    res = true;
                }
            }
        }

        return res;
    }

    public boolean ante(int pos) {
        boolean exito = true;

        if (pos < 1 || pos >= this.longitud || this.longitud < 2) {
            exito = false;
        } else {
            Nodo actual = this.cabecera;
            Nodo nodoPos = null;
            Nodo nodoAntesDeAnteultimo = null;

            int i = 1;
            while (actual.getEnlace() != null) {
                if (i == pos) {
                    nodoPos = actual;
                }

                if (i == this.longitud - 2) {
                    nodoAntesDeAnteultimo = actual;
                }

                actual = actual.getEnlace();
                i++;
            }

            // Ahora nodoAntesDeAnteultimo.getEnlace() es el anteúltimo nodo
            // Intercambiamos los elementos
            Object temp = nodoPos.getElemento();
            nodoPos.setElemento(nodoAntesDeAnteultimo.getEnlace().getElemento());
            nodoAntesDeAnteultimo.getEnlace().setElemento(temp);
        }

        return exito;
    }


    // Simulacro 2019
    // Agregar el metodo agregarElem (Obj, int) que recorre la lista y agrega el elemento nuevo en la primera posicion
    // y luego lo repite cada x posiciones
    // [1,2,3,4,5,6,7] obj=0, int=2, -> [0,1,2,0,3,4,0,5,6,0,7]
    public void agregarElem(Object elemento, int rep){
        if(this.cabecera != null){
            this.cabecera = new Nodo(elemento, this.cabecera);
            this.longitud++;
            Nodo aux2 = this.cabecera.getEnlace();
            int cont = 0;

            while(aux2 != null){
                cont++;
                if(cont == rep){
                    Nodo flopa = new Nodo(elemento, aux2.getEnlace());
                    aux2.setEnlace(flopa);
                    this.longitud++;
                    aux2 = flopa.getEnlace();
                    cont=0;
                }else{
                    aux2 = aux2.getEnlace();
                }

            }
        }
    }
}
