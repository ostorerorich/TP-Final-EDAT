package entidades;

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
        return this.origen.hashCode() + this.hasta.hashCode();
    }


    @Override
    public boolean equals(Object obj){
        boolean res = false;
        if(obj == this){
            res = true;
        }else if(obj != null && obj.getClass() != getClass()){
            TuberiaKey o = (TuberiaKey) obj;

            res = this.origen.equals(o.origen) && this.hasta.equals(o.hasta);
        }

        return res;
    }

}
