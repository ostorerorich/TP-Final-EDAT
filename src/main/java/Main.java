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
    private static int[] nomenclatura = { 3000 };

    public static void main(String[] args) throws IOException {

        try {
            System.out.println(Color.aplicar(Color.AZUL, "Bienvenido al sistema de gestion de ciudades y tuberias"));
            System.out.println();

            Color.print("Cargar datos desde un archivo? (s/n): ");
            String respuesta = sc.nextLine().trim().toLowerCase();
            if (respuesta.equals("s")) {
                MetodosCiudad.cargarCiudadesDesde(ciudades, recorrido, nomenclatura);
                MetodosTuberia.cargarTuberiasDesde(ciudades, recorrido, listadoTuberias);
            } else {
                Color.print("No se cargaran datos desde un archivo.");
            }

            Menu.menu(ciudades, recorrido, listadoTuberias, sc, nomenclatura);
        } catch (RuntimeException e) {
            System.out.println(Color.aplicar(Color.ROJO, "Error inesperado: " + e.getMessage()));
        } finally {
            Log.mensaje(ciudades.toString()).guardar();
            Log.mensaje(recorrido.toString()).guardar();
            Log.mensaje(listadoTuberias.toString()).guardar();
            Log.mensaje("Programa finalizado.").print().guardar();
            sc.close();
            Log.cerrar();
        }

    }

}
