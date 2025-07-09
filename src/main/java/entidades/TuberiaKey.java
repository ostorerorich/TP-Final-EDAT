package entidades;

public class TuberiaKey {
    private Tuberia tub1;
    private Tuberia tub2;


    public TuberiaKey(Tuberia tub1, Tuberia tub2){
        this.tub1 = tub1;
        this.tub2 = tub2;
    }

    public Tuberia getTub1(){
        return tub1;
    }

    public Tuberia getTub2(){
        return tub2;
    }

    @Override
    public int hashCode(){
        return this.tub1.hashCode() + this.tub2.hashCode();
    }


    @Override
    public boolean equals(Object obj){
        boolean res = false;
        if(obj == this){
            res = true;
        }else if(obj != null && obj.getClass() != getClass()){
            TuberiaKey o = (TuberiaKey) obj;

            res = this.tub1.equals(o.tub1) && this.tub2.equals(o.tub2);
        }

        return res;
    }

}
