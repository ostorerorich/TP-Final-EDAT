package entidades;

public class Ciudad implements Comparable<Ciudad> {

    private String nombre;
    private int habitantes;
    private String nomenclatura;
    private int superficie;
    private double cantM3Persona;


    public Ciudad(String nombre, String nomenclatura, int superficie, double cantM3Persona){
        this.nombre = nombre;
        this.habitantes = 0;
        this.nomenclatura = nomenclatura;
        this.superficie = superficie;
        this.cantM3Persona = cantM3Persona;
    }

    public Ciudad(String nombre){
        this.nombre = nombre;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getHabitantes() {
        return habitantes;
    }

    public void setHabitantes(int habitantes) {
        this.habitantes = habitantes;
    }

    public String getNomenclatura() {
        return nomenclatura;
    }

    public void setNomenclatura(String nomenclatura) {
        this.nomenclatura = nomenclatura;
    }

    public int getSuperficie() {
        return superficie;
    }

    public void setSuperficie(int superficie) {
        this.superficie = superficie;
    }

    public double getCantM3Persona() {
        return cantM3Persona;
    }

    public void setCantM3Persona(int cantM3Persona) {
        this.cantM3Persona = cantM3Persona;
    }


    @Override
    public int compareTo(Ciudad o){
        return this.nombre.compareTo(o.getNombre());
    }

    @Override
    public boolean equals(Object obj){
        boolean res = false;
        if(obj == this){
            res = true;
        }else if(obj != null && obj.getClass() != getClass()){
            Ciudad o = (Ciudad) obj;

            res = this.nombre.equals(o.nombre) && this.nomenclatura.equals(o.nomenclatura);
        }

        return res;
    }

    @Override
    public String toString(){
        return this.getNombre();
    }

}
