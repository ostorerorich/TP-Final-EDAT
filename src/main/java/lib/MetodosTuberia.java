package lib;

import entidades.Ciudad;
import entidades.Estado;
import entidades.Tuberia;
import entidades.TuberiaKey;
import estructuras.conjuntistas.dinamicas.ArbolAVL;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import java.util.Scanner;
import java.util.stream.Stream;

public class MetodosTuberia {

    private final static Scanner sc = new Scanner(System.in);

    public static void cargarTuberiasDesde(ArbolAVL arbol, HashMap<TuberiaKey, Tuberia> tuberias) throws IOException {

        if (arbol.esVacio()) {
            Log
                    .mensaje("No hay ciudades cargadas. Cargue ciudades primero.")
                    .print()
                    .guardar();
        } else {
            File archivo = Load.cargarArchivo();
            if (archivo != null) {
                Log.mensaje("Cargando tuberias desde: " + archivo.getAbsolutePath()).print().guardar();

                try (Stream<String> lineas = Files.lines(archivo.toPath())) {
                    lineas.skip(1)
                            .map(l -> l.split(";"))
                            .forEach(l -> {
                                if (l.length == 5) {
                                    Ciudad origen = (Ciudad) arbol.obtener(new Ciudad(l[0].trim()));
                                    Ciudad destino = (Ciudad) arbol.obtener(new Ciudad(l[1].trim()));
                                    int caudalMax = Integer.parseInt(l[2].trim());
                                    int caudalMin = Integer.parseInt(l[3].trim());
                                    int diametro = Integer.parseInt(l[4].trim());

                                    if (origen == null || destino == null) {
                                        Log.mensaje("Error al cargar la tubería: Ciudad no encontrada.").print()
                                                .guardar();
                                    } else {
                                        TuberiaKey key = new TuberiaKey(origen.getNomenclatura(),
                                                destino.getNomenclatura());
                                        if (tuberias.containsKey(key)) {
                                            Log.mensaje(
                                                    "Error al cargar la tubería: Ya existe una tubería entre estas ciudades.")
                                                    .print().guardar();
                                        } else {
                                            Tuberia tuberia = new Tuberia(origen.getNomenclatura(),
                                                    destino.getNomenclatura(), diametro, caudalMax, caudalMin,
                                                    Estado.ACTIVO);
                                            tuberias.put(key, tuberia);
                                            Log.mensaje("Tubería cargada: " + tuberia).print().guardar();
                                        }
                                    }
                                } else {
                                    Log.mensaje("Error al cargar la tubería: Formato incorrecto.").print().guardar();
                                }
                            });

                }
            }
        }
    }

    public static void modificarTuberia(HashMap<TuberiaKey, Tuberia> tuberias) {

        if (!tuberias.isEmpty()) {
            System.out.println("Seleccione la tubería a modificar:");
            tuberias.forEach((k, v) -> System.out.println(k + " = " + v));
            System.out.print("Ingrese la nomenclatura de origen y destino (ej: AB-CD): ");
            String input = sc.nextLine().trim();
            String[] partes = input.split("-");
            if (partes.length == 2) {
                String origen = partes[0].trim();
                String destino = partes[1].trim();
                TuberiaKey key = new TuberiaKey(origen, destino);
                if (tuberias.containsKey(key)) {
                    Tuberia tuberia = tuberias.get(key);
                    System.out.println("Tubería encontrada: " + tuberia);
                    System.out.print("Ingrese el nuevo diámetro: ");
                    int diametro = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Ingrese el nuevo caudal máximo: ");
                    int caudalMax = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Ingrese el nuevo caudal mínimo: ");
                    int caudalMin = Integer.parseInt(sc.nextLine().trim());
                    System.out.println("Seleccionar un nuevo estado para la tubería:");
                    System.out.println("1 - ACTIVO" +
                            "\n2 - REPARACION" +
                            "\n3 - DISENIO" +
                            "\n4 - INACTIVO");

                    int estadoSeleccionado = Integer.parseInt(sc.nextLine().trim());
                    if (estadoSeleccionado < 1 || estadoSeleccionado > 4) {
                        tuberia.setEstado(Estado.ACTIVO);
                    } else {
                        switch (estadoSeleccionado) {
                            case 1 -> tuberia.setEstado(Estado.ACTIVO);
                            case 2 -> tuberia.setEstado(Estado.REPARACION);
                            case 3 -> tuberia.setEstado(Estado.DISENIO);
                            case 4 -> tuberia.setEstado(Estado.INACTIVO);
                        }
                    }

                    if (diametro <= 0 || caudalMin <= 0 || caudalMax <= 0 || caudalMin >= caudalMax) {
                        Log.mensaje("Error: Valores de diámetro o caudales inválidos.").print().guardar();
                    } else {
                        tuberia.setDiametro(diametro);
                        tuberia.setCaudalMax(caudalMax);
                        tuberia.setCaudalMin(caudalMin);
                        Log.mensaje("Tubería modificada: " + tuberia).print().guardar();
                    }
                } else {
                    Log.mensaje("Error: Tubería no encontrada.").print().guardar();
                }

            }
        } else {
            Log.mensaje("No hay tuberías cargadas.").print().guardar();
        }

    }

    public static void eliminarTuberia(HashMap<TuberiaKey, Tuberia> tuberias) {
        if (!tuberias.isEmpty()) {
            System.out.println("Seleccione la tubería a eliminar:");
            mostrarTuberias(tuberias);
            System.out.print("Ingrese la nomenclatura de origen y destino (ej: AB3001-CD3002): ");
            String input = sc.nextLine().trim();
            String[] partes = input.split("-");

            if (partes.length == 2 && MetodosCiudad.validarNomenclatura(partes[0])
                    && MetodosCiudad.validarNomenclatura(partes[1])) {
                String origen = partes[0].trim();
                String destino = partes[1].trim();
                TuberiaKey key = new TuberiaKey(origen, destino);
                if (tuberias.containsKey(key)) {
                    tuberias.remove(key);
                    Log.mensaje("Tubería eliminada: " + key).print().guardar();
                } else {
                    Log.mensaje("Error: Tubería no encontrada.").print().guardar();
                }
            }
        } else {
            Log.mensaje("No hay tuberías cargadas.").print().guardar();
        }
    }

    public static void mostrarTuberias(HashMap<TuberiaKey, Tuberia> tuberias) {
        if (!tuberias.isEmpty()) {
            System.out.println("Listado de tuberías:");
            tuberias.forEach((k, v) -> System.out.println(k + " = " + v));
        } else {
            Log.mensaje("No hay tuberías cargadas.").print().guardar();
        }
    }

    public static void agregarTuberia(HashMap<TuberiaKey, Tuberia> tuberias, ArbolAVL arbol) {
        System.out.print("Ingrese la ciudad de origen: ");
        String origen = sc.nextLine().trim();
        System.out.print("Ingrese la ciudad de destino: ");
        String destino = sc.nextLine().trim();
        Ciudad ciudadOrigen = (Ciudad) arbol.obtener(new Ciudad(origen));
        Ciudad ciudadDestino = (Ciudad) arbol.obtener(new Ciudad(destino));

        if (ciudadOrigen == null || ciudadDestino == null) {
            Log.mensaje("Error: Una o ambas ciudades no existen.").print().guardar();
        } else {
            System.out.print("Ingrese el diámetro de la tubería: ");
            int diametro = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Ingrese el caudal máximo: ");
            int caudalMax = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Ingrese el caudal mínimo: ");
            int caudalMin = Integer.parseInt(sc.nextLine().trim());

            if (diametro <= 0 || caudalMin <= 0 || caudalMax <= 0 || caudalMin >= caudalMax) {
                Log.mensaje("Error: Valores de diámetro o caudales inválidos.").print().guardar();
            } else {
                TuberiaKey key = new TuberiaKey(ciudadOrigen.getNomenclatura(), ciudadDestino.getNomenclatura());
                if (tuberias.containsKey(key)) {
                    Log.mensaje("Error: Ya existe una tubería entre estas ciudades.").print().guardar();
                } else {
                    Tuberia tuberia = new Tuberia(ciudadOrigen.getNomenclatura(), ciudadDestino.getNomenclatura(),
                            diametro, caudalMax, caudalMin, Estado.ACTIVO);
                    System.out.println("Seleccionar un estado para la tubería:");
                    System.out.println("1 - ACTIVO" +
                            "\n2 - REPARACION" +
                            "\n3 - DISENIO" +
                            "\n4 - INACTIVO");
                    int estadoSeleccionado = Integer.parseInt(sc.nextLine().trim());
                    if (estadoSeleccionado < 1 || estadoSeleccionado > 4) {
                        tuberia.setEstado(Estado.ACTIVO);
                    } else {
                        switch (estadoSeleccionado) {
                            case 1 -> tuberia.setEstado(Estado.ACTIVO);
                            case 2 -> tuberia.setEstado(Estado.REPARACION);
                            case 3 -> tuberia.setEstado(Estado.DISENIO);
                            case 4 -> tuberia.setEstado(Estado.INACTIVO);
                        }
                    }
                    tuberias.put(key, tuberia);
                    Log.mensaje("Tubería agregada: " + tuberia).print().guardar();
                }
            }

        }

    }

    public static void buscarTuberia(HashMap<TuberiaKey, Tuberia> tuberias) {
        System.out.print("Ingrese la nomenclatura de origen y destino (ej: AB3001-CD3002): ");
        String input = sc.nextLine().trim();
        String[] partes = input.split("-");
        if (partes.length == 2 && MetodosCiudad.validarNomenclatura(partes[0])
                && MetodosCiudad.validarNomenclatura(partes[1])) {
            String origen = partes[0].trim();
            String destino = partes[1].trim();
            TuberiaKey key = new TuberiaKey(origen, destino);
            if (tuberias.containsKey(key)) {
                Tuberia tuberia = tuberias.get(key);
                Log.mensaje("Tubería encontrada: " + tuberia).print().guardar();
            } else {
                Log.mensaje("Error: Tubería no encontrada.").print().guardar();
            }
        } else {
            Log.mensaje("Error: Formato de entrada incorrecto.").print().guardar();
        }
    }
}
