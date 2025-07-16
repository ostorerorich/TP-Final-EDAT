package entidades;

public class Tuberia {

    private String nomOrigen;
    private String nomDestino;
    private int diametro;
    private int caudalMax;
    private int caudalMin;
    private Estado estado;


    public Tuberia(String nomOrigen, String nomDestino, int diametro, int caudalMax, int caudalMin, Estado estado) {
        this.nomOrigen = nomOrigen;
        this.nomDestino = nomDestino;
        this.diametro = diametro;
        this.caudalMax = caudalMax;
        this.caudalMin = caudalMin;
        this.estado = estado;
    }

    public Tuberia(String nomOrigen, String nomDestino) {
        this.nomOrigen = nomOrigen;
        this.nomDestino = nomDestino;
        this.diametro = 0; // Default value
        this.caudalMax = 0; // Default value
        this.caudalMin = 0; // Default value
        this.estado = Estado.INACTIVO; // Default state
    }


    public String getNomOrigen() {
        return nomOrigen;
    }

    public void setNomOrigen(String nomOrigen) {
        this.nomOrigen = nomOrigen;
    }

    public String getNomDestino() {
        return nomDestino;
    }

    public void setNomDestino(String nomDestino) {
        this.nomDestino = nomDestino;
    }

    public int getDiametro() {
        return diametro;
    }

    public void setDiametro(int diametro) {
        this.diametro = diametro;
    }

    public int getCaudalMax() {
        return caudalMax;
    }

    public void setCaudalMax(int caudalMax) {
        this.caudalMax = caudalMax;
    }

    public int getCaudalMin() {
        return caudalMin;
    }

    public void setCaudalMin(int caudalMin) {
        this.caudalMin = caudalMin;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }


}


