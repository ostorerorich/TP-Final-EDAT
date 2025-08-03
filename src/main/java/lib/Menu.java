package lib;

import java.util.Map;
import java.util.Scanner;

import entidades.Tuberia;
import entidades.TuberiaKey;
import estructuras.conjuntistas.dinamicas.ArbolAVL;
import estructuras.grafos.Grafo;

public class Menu {

    public static void menu(ArbolAVL ciudades, Grafo recorrido,
            Map<TuberiaKey, Tuberia> listadoTuberias, Scanner sc) {
        boolean fin = false;
        try {
            while (!fin) {
                System.out.println(String.format(
                        " %s - Menu Ciudades\n %s - Modificacion Tuberia\n %s - Alta cantidad Habitantes\n %s - Consulta Ciudad\n %s - Consulta Transporte\n %s - Listado de ciudades\n %s - Mostrar sistema (Debug)\n %s - Salir del programa\n",
                        Color.aplicar(Color.AZUL, "1"), Color.aplicar(Color.AZUL, "2"), Color.aplicar(Color.AZUL, "3"),
                        Color.aplicar(Color.AZUL, "4"), Color.aplicar(Color.AZUL, "5"), Color.aplicar(Color.AZUL, "6"),
                        Color.aplicar(Color.AZUL, "7"), Color.aplicar(Color.AZUL, "0")));

                // next line es para que no se quede en memoria el enter y lea la linea de
                // manera correcta
                Color.printWar("Ingrese una opcion:");
                int res = Integer.parseInt(sc.nextLine());

                switch (res) {
                    case 1 -> menuCiudades(ciudades, listadoTuberias, recorrido, sc);
                    case 2 -> menuTuberias(ciudades, recorrido, listadoTuberias, sc);
                    case 3 -> MetodosCiudad.agregarHabitantesCiudad(ciudades);
                    case 4 -> consultasCiudad(ciudades, recorrido, sc);
                    case 5 -> consultasTransporte(ciudades, listadoTuberias, recorrido, sc);
                    case 6 ->
                        System.out.println(Color.aplicar(Color.AZUL, "Listado de ciudades :") + ciudades.listar());
                    case 7 -> mostrarSistema(ciudades, recorrido, listadoTuberias);
                    case 0 -> {
                        fin = true;
                        Color.printErr("Saliendo del programa...");
                    }
                    default -> Color.printErr("Opcion invalida, por favor intente nuevamente.");
                }

            }
        } catch (NumberFormatException e) {
            Color.printErr("Error: Debe ingresar un numero valido.");
        } catch (Exception e) {
            Color.printErr("Error: " + e.getMessage());
        }
    }

    private static void menuCiudades(ArbolAVL ciudades, Map<TuberiaKey, Tuberia> listadoTuberias, Grafo recorrido,
            Scanner sc) {
        try {
            System.out.println(String.format(
                    " %s - Alta ciudad\n %s - Baja Ciudad\n %s - Modificacion Ciudad\n %s - Listar Ciudades\n %s - Buscar Ciudad\n %s - Mostrar habitantes de una ciudad\n %s - Volver al menu principal",
                    Color.aplicar(Color.AZUL, "1"), Color.aplicar(Color.AZUL, "2"), Color.aplicar(Color.AZUL, "3"),
                    Color.aplicar(Color.AZUL, "4"), Color.aplicar(Color.AZUL, "5"), Color.aplicar(Color.AZUL, "6"),
                    Color.aplicar(Color.AZUL, "7")));

            Color.printWar("Ingrese una opcion:");
            int res = Integer.parseInt(sc.nextLine());
            switch (res) {
                case 1 -> {
                    MetodosCiudad.agregarCiudadInput(ciudades, recorrido);
                }
                case 2 -> MetodosCiudad.eliminarCiudad(ciudades, listadoTuberias, recorrido);
                case 3 -> System.out.println("Modificacion Ciudad");
                case 4 -> System.out.println("Listar Ciudades: " + ciudades.listar());
                case 5 -> MetodosCiudad.buscarCiudad(ciudades);
                case 6 -> {
                    MetodosCiudad.mostrarHabitantesCiudad(ciudades);
                }
                case 7 -> {
                }
                default -> System.out.println("Opcion invalida");
            }
        } catch (NumberFormatException e) {
            System.out.println(Color.aplicar(Color.ROJO, "Error: Debe ingresar un numero valido."));
        } catch (Exception e) {
            System.out.println(Color.aplicar(Color.ROJO, "Error: " + e.getMessage()));
        }
    }

    private static void menuTuberias(ArbolAVL ciudades, Grafo recorrido,
            Map<TuberiaKey, Tuberia> listadoTuberias, Scanner sc) {
        try {
            System.out.println(String.format(
                    " %s - Agregar Tuberia\n %s - Eliminar Tuberia\n %s - Modificar Tuberia\n %s - Mostrar Tuberias\n %s - Buscar Tuberia\n %s - Volver al menu principal",
                    Color.aplicar(Color.AZUL, "1"), Color.aplicar(Color.AZUL, "2"), Color.aplicar(Color.AZUL, "3"),
                    Color.aplicar(Color.AZUL, "4"), Color.aplicar(Color.AZUL, "5"), Color.aplicar(Color.AZUL, "6")));
            Color.printWar("Ingrese una opcion:");
            int res = Integer.parseInt(sc.nextLine());
            switch (res) {
                case 1 -> MetodosTuberia.agregarTuberia(listadoTuberias, ciudades, recorrido);
                case 2 -> MetodosTuberia.eliminarTuberia(listadoTuberias, recorrido);
                case 3 -> MetodosTuberia.modificarTuberia(listadoTuberias, recorrido);
                case 4 -> MetodosTuberia.mostrarTuberias(listadoTuberias);
                case 5 -> MetodosTuberia.buscarTuberia(listadoTuberias);
                case 6 -> {
                }
                default -> System.out.println("Opcion invalida");
            }
        } catch (NumberFormatException e) {
            Color.printErr("Error: Debe ingresar un numero valido.");
        } catch (Exception e) {
            Color.printErr("Error: " + e.getMessage());
        }
    }

    private static void consultasCiudad(ArbolAVL ciudades, Grafo recorrido, Scanner sc) {

        try {
            System.out.println(String.format(
                    " %s - Obtener cantidad de habitantes y volumen de agua distribuido en una ciudad en un mes y anio\n %s - Obtener ciudades por rango de consumo en un mes y anio\n %s - Volver al menu principal",
                    Color.aplicar(Color.AZUL, "1"), Color.aplicar(Color.AZUL, "2"), Color.aplicar(Color.AZUL, "3")));
            Color.printWar("Ingrese una opcion:");
            int resultado = Integer.parseInt(sc.nextLine());

            switch (resultado) {
                case 1 -> {
                    MetodosCiudad.mostrarCantHabitantesYConsumo(ciudades);
                }
                case 2 -> {
                    MetodosCiudad.mostrarCiudadesRango(ciudades);
                }
                case 3 -> {
                }
                default -> System.out.println("Opcion invalida");
            }
        } catch (NumberFormatException e) {
            Color.printErr("Error: Debe ingresar un numero valido.");
        } catch (Exception e) {
            Color.printErr("Error: " + e.getMessage());
        }

    }

    private static void consultasTransporte(ArbolAVL ciudades, Map<TuberiaKey, Tuberia> listadoTuberias,
            Grafo recorrido, Scanner sc) {

        try {
            System.out.println(String.format(
                    " %s - Obtener camino que llegue de una ciudad A a una ciudad B tal que el caudal pleno del camino completo sea el minimo entre todos los caminos posibles\n %s - Obtener el camino que llegue de una ciudad A a una ciudad B pasando por la minima cantidad de ciudades\n %s - Volver al menu principal",
                    Color.aplicar(Color.AZUL, "1"), Color.aplicar(Color.AZUL, "2"), Color.aplicar(Color.AZUL, "3")));
            Color.printWar("Ingrese una opcion:");

            int resultado = Integer.parseInt(sc.nextLine());

            switch (resultado) {
                case 1 -> {
                    MetodosTuberia.obtenerCaminoDeCaudalPlenoMinimo(ciudades, recorrido, listadoTuberias);
                }
                case 2 -> {
                    MetodosTuberia.obtenerCaminoMasCorto(ciudades, recorrido, listadoTuberias);
                }
                case 3 -> {
                }
                default -> System.out.println("Opcion invalida");
            }
        } catch (NumberFormatException e) {
            Color.printErr("Error: Debe ingresar un numero valido.");
        } catch (Exception e) {
            Color.printErr("Error: " + e.getMessage());
        }

    }

    private static void mostrarSistema(ArbolAVL ciudades, Grafo recorrido, Map<TuberiaKey, Tuberia> listadoTuberias) {

        System.out.println("Arbol AVL: ");
        System.out.println(ciudades.toString());

        System.out.println("Grafo: ");
        System.out.println(recorrido.toString());

        listadoTuberias.forEach((k, v) -> System.out.println(k + " = " + v +
                " metros"));

    }
}