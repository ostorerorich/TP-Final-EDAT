package entidades;

import java.util.Objects;

public class TuberiaKey {
    private Ciudad origen;
    private Ciudad hasta;


    public TuberiaKey(Ciudad origen, Ciudad hasta){
        this.origen = origen;
        this.hasta = hasta;
    }

    public Ciudad getOrigen(){
        return origen;
    }

    public Ciudad getHasta(){
        return hasta;
    }

    @Override
    public int hashCode(){
        return Objects.hash(origen, hasta);
    }


    @Override
    public boolean equals(Object obj){
        boolean res = false;
        if(obj != null && obj.getClass() == getClass()){
            if(obj == this){
                res = true;
            }else{
                TuberiaKey o = (TuberiaKey) obj;
                res = this.origen.equals(o.origen) && this.hasta.equals(o.hasta);
            }
        }
        return res;
    }

    @Override
    public String toString(){
        return "TuberiaKey{" +
                "origen=" + origen +
                ", hasta=" + hasta +
                '}';
    }

}
