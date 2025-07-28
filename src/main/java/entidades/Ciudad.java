package entidades;

import java.time.Year;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

public class Ciudad implements Comparable<Ciudad> {

    private String nombre;
    private Map<Integer, Integer[]> habitantes;
    private String nomenclatura;
    private int superficie;
    private double cantM3Persona;

    public Ciudad(String nombre, String nomenclatura, int superficie, double cantM3Persona, Integer cantHabitantes) {
        this.nombre = nombre;
        this.habitantes = this.generarAnios(cantHabitantes);
        this.nomenclatura = nomenclatura;
        this.superficie = superficie;
        this.cantM3Persona = cantM3Persona;
    }

    public Ciudad(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public void setCantM3Persona(double cantM3Persona) {
        this.cantM3Persona = cantM3Persona;
    }

    public Map<?, ?> getMapHabitantes() {
        return this.habitantes;
    }

    @Override
    public int compareTo(Ciudad o) {
        return this.nombre.compareTo(o.getNombre());
    }

    @Override
    public boolean equals(Object obj) {
        boolean res = false;
        if (obj != null && obj.getClass() == getClass()) {
            if (obj == this) {
                res = true;
            } else {
                Ciudad o = (Ciudad) obj;
                res = this.nombre.equals(o.nombre);
            }
        }
        return res;
    }

    private Map<Integer, Integer[]> generarAnios(Integer cantHabitantes) {
        Map<Integer, Integer[]> res = new HashMap<>();
        Integer anio = Year.now().getValue();
        for (int i = 0; i < 10; i++) {
            Integer[] arr = new Integer[12];
            Arrays.fill(arr, cantHabitantes);
            res.put(anio - i, arr);
        }
        return res;
    }

    public boolean anioValido(int anio) {
        return this.habitantes.get(anio) != null;
    }

    public boolean agregarCantHabitantes(Integer anio, Integer mes, Integer cant) {
        Integer[] yearArr = habitantes.getOrDefault(anio, null);
        boolean res = false;

        // Verificar años siguientes cuando se le modifica a x mes de x año.
        if (yearArr != null && mes >= 1 && mes <= 12) {
            yearArr[mes - 1] = cant;
            res = true;
        }
        return res;
    }

    public Integer getCantHabitantes(Integer anio, Integer mes) {
        Integer[] yearArr = habitantes.getOrDefault(anio, null);
        Integer res = null;
        if (yearArr != null && mes >= 1 && mes <= 12) {
            res = yearArr[mes - 1];
        }
        return res;
    }




    public String obtenerTodosLosHabitantes() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, Integer[]> entry : habitantes.entrySet()) {
            Integer anio = entry.getKey();
            Integer[] meses = entry.getValue();
            sb.append("Año: ").append(anio).append("\n");
            for (int i = 0; i < meses.length; i++) {
                sb.append("Mes ").append(i + 1).append(": ").append(meses[i] != null ? meses[i] : "No registrado")
                        .append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Ciudad{" +
                "nombre='" + nombre + '\'' +
                ", nomenclatura='" + nomenclatura + '\'' +
                ", superficie=" + superficie +
                ", cantM3Persona=" + cantM3Persona +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}
