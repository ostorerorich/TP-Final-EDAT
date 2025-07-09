import entidades.TuberiaKey;
import estructuras.conjuntistas.dinamicas.ArbolAVL;
import grafos.Grafo;
import lib.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    private static ArbolAVL ciudades = new ArbolAVL();
    private static Grafo tuberias = new Grafo();
    private static HashMap<TuberiaKey, Integer> hash = new HashMap();
    private static Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
        try {
            Log.mensaje("Test").guardar();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        finally {
          Log.cerrar();
        }
    }



    private static void menu(){
        boolean fin = false;

        while(!fin){
            System.out.println("---MENU---- /n 1- Modificacion Ciudades" +
                    "/n 2 - Modificacion Tuberia" +
                    "/n 3 - Alta cantidad Habitantes /n" +
                    "/n 4 - Consulta Ciudad" +
                    "/n 5 - Consulta Transporte" +
                    "/n 6 - Listado de ciudades" +
                    "/n 7 - Mostrar sistema (Debug)" +
                    "/n 0 - Terminar");

            // next line es para que no se quede en memoria el enter y lea la linea de manera correcta

            int res =  Integer.parseInt(sc.nextLine());
            switch (res){
                case 1:
                    // Alta, baja y mod ciudades
                    break;
                case 2:
                    // Alta baja y mod tuberias
                    break;
                case 3:
                    // Alta de informacion de la cantidad de habitantes
                    break;
                case 4:
                    // Consulta de ciudades
                    break;
                case 5:
                    // Consulta de transporte
                    break;
                case 6:
                    // Listado de Ciudades en ordenaadas por el consumo de agua anual de mayor a menor
                    break;
                case 7:
                    // Debug del sistema
                    break;
                case 0:
                    // Termina el menu
                    fin = true;
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }


        }
    }
}
