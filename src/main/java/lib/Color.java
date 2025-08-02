package lib;

public enum Color {
    RESET("\u001B[0m"),
    NEGRO("\u001B[30m"),
    ROJO("\u001B[31m"),
    VERDE("\u001B[32m"),
    AMARILLO("\u001B[33m"),
    AZUL("\u001B[34m"),
    MAGENTA("\u001B[35m"),
    CYAN("\u001B[36m"),
    BLANCO("\u001B[37m");

    private final String color;

    Color(String color) {
        this.color = color;
    }

    public String get() {
        return color;
    }

    public static String aplicar(Color color, String texto) {
        return color.get() + texto + RESET.get();
    }
}
