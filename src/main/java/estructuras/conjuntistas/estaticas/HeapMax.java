package estructuras.conjuntistas.estaticas;

@SuppressWarnings("all")
public class HeapMax {
    private Comparable[] heap;
    private int ultimo;
    private final int SIZE = 25;

    public HeapMax() {
        this.heap = new Comparable[SIZE];
        this.ultimo = 0;
    }


    public boolean insertar(Comparable obj) {
        boolean res = false;
        if (this.ultimo < SIZE) {
            this.ultimo++;
            this.heap[ultimo] = obj;
            //hacerSubir(ultimo);
            res = true;
        }
        return res;
    }

    public boolean eliminarCima() {
        boolean res = false;
        if (this.ultimo > 0) {
            this.heap[1] = this.heap[ultimo];
            this.heap[ultimo] = null;
            this.ultimo--;
            //hacerBajar(1);
            res = true;
        }
        return res;
    }

    public boolean esVacio() {
        return (this.ultimo == 0);
    }

    public Comparable recuperarCima() {
        return this.ultimo == 0 ? "bruh" : this.heap[1];
    }

    public void hacerSubir(int posH) {
        int posP;
        Comparable temp = this.heap[posH];
        boolean salir = false;

        while (!salir) {
            posP = posH / 2;
            if (posP >= 1) {
                if (this.heap[posP].compareTo(temp) < 0) {
                    this.heap[posH] = this.heap[posP];
                    this.heap[posP] = temp;
                    posH = posP;
                } else {
                    salir = true;
                }
            } else {
                salir = true;
            }
        }
    }

    public void hacerBajar(int posP) {
        int posH;
        Comparable temp = this.heap[posP];
        boolean salir = false;
        while (!salir) {
            posH = posP * 2;
            if (posH <= this.ultimo) {
                if (posH < this.ultimo) {
                    if (this.heap[posH + 1].compareTo(this.heap[posH]) > 0) {
                        posH++;
                    }
                }
                if (this.heap[posH].compareTo(temp) > 0) {
                    this.heap[posP] = this.heap[posH];
                    this.heap[posH] = temp;
                    posP = posH;

                } else {
                    salir = true;
                }
            } else {
                salir = true;
            }
        }
    }

}
