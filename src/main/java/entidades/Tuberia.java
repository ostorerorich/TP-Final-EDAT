package entidades;

public class Tuberia {
    private String nomenclatura;
    private int caudalMax;
    private int caudalMin;
    private double diametro;
    private String estado;

    public Tuberia(String nomenclatura, int caudalMax, int caudalMin, double diametro, String estado){
        this.nomenclatura = nomenclatura;
        this.caudalMax = caudalMax;
        this.caudalMin = caudalMin;
        this.diametro = diametro;
        this.estado = estado;
    }

    public String getNomenclatura() {
        return nomenclatura;
    }

    public void setNomenclatura(String nomenclatura) {
        this.nomenclatura = nomenclatura;
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

    public double getDiametro() {
        return diametro;
    }

    public void setDiametro(double diametro) {
        this.diametro = diametro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


}
