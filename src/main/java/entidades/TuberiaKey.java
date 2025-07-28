package entidades;

import java.util.Objects;

public class TuberiaKey {
    private String nomenclaturaOrigen;
    private String nomenclaturaDestino;


    public TuberiaKey(String nomenclaturaOrigen, String nomenclaturaDestino) {
        this.nomenclaturaOrigen = nomenclaturaOrigen;
        this.nomenclaturaDestino = nomenclaturaDestino;
    }

    public String getNomenclaturaOrigen() {
        return nomenclaturaOrigen;
    }

    public void setNomenclaturaOrigen(String nomenclaturaOrigen) {
        this.nomenclaturaOrigen = nomenclaturaOrigen;
    }

    public String getNomenclaturaDestino() {
        return nomenclaturaDestino;
    }

    public void setNomenclaturaDestino(String nomenclaturaDestino) {
        this.nomenclaturaDestino = nomenclaturaDestino;
    }

    @Override
    public int hashCode(){
        return Objects.hash(nomenclaturaOrigen, nomenclaturaDestino);
    }


    @Override
    public boolean equals(Object obj){
        boolean res = false;
        if(obj != null && obj.getClass() == getClass()){
            if(obj == this){
                res = true;
            }else{
                TuberiaKey o = (TuberiaKey) obj;
                res = this.nomenclaturaOrigen.equals(o.nomenclaturaOrigen)
                        && this.nomenclaturaDestino.equals(o.nomenclaturaDestino);
            }
        }
        return res;
    }

    @Override
    public String toString(){
        return "TuberiaKey{" +
                "origen=" + nomenclaturaOrigen +
                ", hasta=" + nomenclaturaDestino +
                '}';
    }

}
