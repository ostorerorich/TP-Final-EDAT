package entidades;

public enum Estado {
    ACTIVO("Activo"), REPARACION("Reparación"), DISENIO("Diseño"), INACTIVO("Inactivo");

    private String nombre;

    Estado(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
