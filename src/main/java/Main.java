import entidades.Tuberia;
import entidades.TuberiaKey;
import estructuras.conjuntistas.dinamicas.ArbolAVL;
import estructuras.grafos.Grafo;

import lib.MetodosCiudad;
import lib.MetodosTuberia;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    private static ArbolAVL ciudades = new ArbolAVL();
    private static Grafo recorrido = new Grafo();
    private static HashMap<TuberiaKey, Tuberia> listadoTuberias = new HashMap<TuberiaKey, Tuberia>();
    private static Scanner sc = new Scanner(System.in);


    public static void main(String[] args) throws IOException{
/*        try {
            Log.mensaje("Test").guardar();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            Log.cerrar();

            System.out.println("bruh");
            System.out.println(System.getProperty("user.home") + File.separator + "Documents" + File.separator + "logs_edat");
        }*/
        MetodosCiudad.cargarCiudadesDesde(ciudades, recorrido);
        MetodosTuberia.cargarTuberiasDesde(ciudades, recorrido, listadoTuberias);

        listadoTuberias.forEach((k, v) -> System.out.println(k + " = " + v + " metros"));

        menu();
    }


    private static void menu(){
        boolean fin = false;

        while(!fin){
            System.out.println("""
                1 - Menu Ciudades
                2 - Modificacion Tuberia
                3 - Alta cantidad Habitantes
                4 - Consulta Ciudad
                5 - Consulta Transporte
                6 - Listado de ciudades
                7 - Mostrar sistema (Debug)
                0 - Salir del programa"""
            );

            // next line es para que no se quede en memoria el enter y lea la linea de manera correcta

            int res =  Integer.parseInt(sc.nextLine());
            switch (res){
                case 1 -> menuCiudades();
                case 2 -> menuTuberias();
                case 3 -> System.out.println("Alta cantidad Habitantes");
                case 4 -> System.out.println("Consulta Ciudad");
                case 5 -> consultasTransporte();
                case 6 -> System.out.println("Listado de ciudades: " + ciudades.listarInOrden());
                case 7 -> System.out.println("Mostrar sistema (Debug)");
                case 0 -> {
                    fin = true;
                    System.out.println("Saliendo del programa...");
                }
                default -> System.out.println("Opcion invalida, intente nuevamente.");
            }


        }
    }

    private static void menuCiudades(){
        System.out.println("""
                1 - Alta Ciudad
                2 - Baja Ciudad
                3 - Modificacion Ciudad
                4 - Listar Ciudades
                5 - Buscar Ciudad
                6 - Volver al menu principal"""
        );
        int res = Integer.parseInt(sc.nextLine());
        switch (res) {
            case 1 -> {
                    MetodosCiudad.agregarCiudadInput(ciudades, recorrido);
            }
            case 2 -> MetodosCiudad.eliminarCiudad(ciudades);
            case 3 -> System.out.println("Modificacion Ciudad");
            case 4 -> System.out.println("Listar Ciudades: " + ciudades.listarInOrden());
            case 5 -> MetodosCiudad.buscarCiudad(ciudades);
            case 6 -> {}
            default -> System.out.println("Opcion invalida");
        }
    }


    private static void menuTuberias(){
        System.out.println("""
                1 - Alta Tuberia
                2 - Baja Tuberia
                3 - Modificacion Tuberia
                4 - Listar Tuberias
                5 - Buscar Tuberia
                6 - Volver al menu principal"""
        );
        int res = Integer.parseInt(sc.nextLine());
        switch (res) {
            case 1 -> MetodosTuberia.agregarTuberia(listadoTuberias, ciudades, recorrido);
            case 2 -> MetodosTuberia.eliminarTuberia(listadoTuberias);
            case 3 -> MetodosTuberia.modificarTuberia(listadoTuberias);
            case 4 -> MetodosTuberia.mostrarTuberias(listadoTuberias);
            case 5 -> MetodosTuberia.buscarTuberia(listadoTuberias);
            case 6 -> {}
            default -> System.out.println("Opcion invalida");
        }
    }
    
    private static void consultasTransporte(){
                
        System.out.println("1 - Obtener camino que llegue de una ciudad A a una ciudad B tal que el caudal pleno del " +
                           "camino completo sea el minimo entre todos los caminos posibles");
        System.out.println("2 - Obtener el camino que llegue de una ciudad A a una ciudad B pasando por la minima cantidad de ciudades");
        
        int resultado = Integer.parseInt(sc.nextLine());
        
        switch (resultado) {
            case 1 -> {
                MetodosTuberia.obtenerCaminoDeCaudalPlenoMinimo(ciudades, recorrido, listadoTuberias);
            }
            case 2 -> {
                MetodosTuberia.obtenerCaminoMasCorto(ciudades, recorrido, listadoTuberias);
            }
            default -> System.out.println("Opcion invalida");
        }
        
    }
    
}
