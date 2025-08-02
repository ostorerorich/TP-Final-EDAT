import entidades.Tuberia;
import entidades.TuberiaKey;
import estructuras.conjuntistas.dinamicas.ArbolAVL;
import estructuras.grafos.Grafo;
import lib.Color;
import lib.Log;
import lib.Menu;
import lib.MetodosCiudad;
import lib.MetodosTuberia;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static ArbolAVL ciudades = new ArbolAVL();
    private static Grafo recorrido = new Grafo();
    private static Map<TuberiaKey, Tuberia> listadoTuberias = new HashMap<TuberiaKey, Tuberia>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        try {
            System.out.println(Color.aplicar(Color.AZUL, "Bienvenido al sistema de gestion de ciudades y tuberias"));

            MetodosCiudad.cargarCiudadesDesde(ciudades, recorrido);
            MetodosTuberia.cargarTuberiasDesde(ciudades, recorrido, listadoTuberias);

            Menu.menu(ciudades, recorrido, listadoTuberias, sc);
        } catch (RuntimeException e) {
            System.out.println(Color.aplicar(Color.ROJO, "Error inesperado: " + e.getMessage()));
        } finally {
            sc.close();
            Log.cerrar();
        }

    }

}
