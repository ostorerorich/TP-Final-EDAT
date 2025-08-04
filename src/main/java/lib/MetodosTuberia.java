package lib;

import entidades.Ciudad;
import entidades.Estado;
import entidades.Tuberia;
import entidades.TuberiaKey;
import estructuras.lineales.dinamicas.Lista;
import estructuras.conjuntistas.dinamicas.ArbolAVL;
import estructuras.grafos.Grafo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class MetodosTuberia {

    private final static Scanner sc = new Scanner(System.in);

    public static void cargarTuberiasDesde(ArbolAVL arbol, Grafo caminos, Map<TuberiaKey, Tuberia> tuberias)
            throws IOException {

        if (arbol.esVacio()) {
            Log
                    .mensaje("No hay ciudades cargadas. Cargue ciudades primero.")
                    .print(Color.ROJO)
                    .guardar();
        } else {
            File archivo = Load.cargarArchivo("Seleccione un archivo CSV de tuberías");
            if (archivo != null) {
                Log.mensaje("Cargando tuberias desde: " + archivo.getAbsolutePath()).print(Color.MAGENTA).guardar();

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
                                    // Asumimos que los datos son correctos
                                    if (origen == null || destino == null) {
                                        Log.mensaje("Error al cargar la tubería: Ciudad no encontrada.")
                                                .print(Color.ROJO)
                                                .guardar();
                                    } else {
                                        TuberiaKey key = new TuberiaKey(origen.getNomenclatura(),
                                                destino.getNomenclatura());
                                        if (tuberias.containsKey(key)) {
                                            Log.mensaje(
                                                    "Error al cargar la tubería: Ya existe una tubería entre estas ciudades.")
                                                    .print(Color.ROJO).guardar();
                                        } else {
                                            Tuberia tuberia = new Tuberia(origen.getNomenclatura(),
                                                    destino.getNomenclatura(), diametro, caudalMax, caudalMin,
                                                    Estado.ACTIVO);
                                            tuberias.put(key, tuberia);
                                            caminos.insertarArco(origen.getNomenclatura(), destino.getNomenclatura(),
                                                    tuberia.getCaudalMax());
                                            Log.mensaje("Tubería cargada: " + tuberia).print().guardar();
                                        }
                                    }
                                } else {
                                    Log.mensaje("Error al cargar la tubería: Formato incorrecto.").print(Color.ROJO)
                                            .guardar();
                                }
                            });

                }
            }
        }
    }

    private static Estado obtenerEstadoTuberia() {
        Color.print("Seleccionar un nuevo estado para la tubería:");
        boolean loop = true;

        int estadoSeleccionado = 0;

        while (loop) {
            Color.print("1 - ACTIVO" +
                    "\n2 - REPARACION" +
                    "\n3 - DISENIO" +
                    "\n4 - INACTIVO");

            estadoSeleccionado = Integer.parseInt(sc.nextLine().trim());
            if (estadoSeleccionado >= 1 && estadoSeleccionado <= 4) {
                loop = false;
            } else {
                Color.print("Opción inválida, por favor intente nuevamente:");
            }
        }

        return switch (estadoSeleccionado) {
            case 1 -> Estado.ACTIVO;
            case 2 -> Estado.REPARACION;
            case 3 -> Estado.DISENIO;
            case 4 -> Estado.INACTIVO;
            default -> Estado.ACTIVO;
        };
    }

    public static void modificarTuberia(Map<TuberiaKey, Tuberia> tuberias, Grafo caminos) {

        if (!tuberias.isEmpty()) {
            Color.print("Seleccione la tubería a modificar:");
            tuberias.forEach((k, v) -> System.out.println(k + " = " + v));
            System.out.print("Ingrese la nomenclatura de origen y destino (ej: AB3000-CD3001): ");
            String input = sc.nextLine().trim();
            String[] partes = input.split("-");
            if (partes.length == 2 && MetodosCiudad.validarNomenclatura(partes[0])
                    && MetodosCiudad.validarNomenclatura(partes[1])) {
                String origen = partes[0].trim();
                String destino = partes[1].trim();
                TuberiaKey key = new TuberiaKey(origen, destino);
                if (tuberias.containsKey(key)) {
                    Tuberia tuberia = tuberias.get(key);
                    Color.printOk("Tubería encontrada: " + tuberia);
                    Color.print("Ingrese el nuevo diámetro: ");
                    int diametro = Integer.parseInt(sc.nextLine().trim());
                    Color.print("Ingrese el nuevo caudal máximo: ");
                    int caudalMax = Integer.parseInt(sc.nextLine().trim());
                    Color.print("Ingrese el nuevo caudal mínimo: ");
                    int caudalMin = Integer.parseInt(sc.nextLine().trim());

                    tuberia.setEstado(obtenerEstadoTuberia());

                    if (diametro <= 0 || caudalMin <= 0 || caudalMax <= 0 || caudalMin >= caudalMax) {
                        Log.mensaje("Error: Valores de diámetro o caudales inválidos.").print(Color.ROJO).guardar();
                    } else {
                        tuberia.setDiametro(diametro);
                        tuberia.setCaudalMax(caudalMax);
                        tuberia.setCaudalMin(caudalMin);
                        caminos.modificarEtiqueta(origen, destino, caudalMax);
                        Log.mensaje("Tubería modificada: " + tuberia).print().guardar();
                    }
                } else {
                    Log.mensaje("Error: Tubería no encontrada.").print(Color.ROJO).guardar();
                }

            } else {
                Log.mensaje("Error: Formato de entrada inválido.").print(Color.ROJO).guardar();
            }
        } else {
            Log.mensaje("No hay tuberías cargadas.").print(Color.ROJO).guardar();
        }

    }

    public static void eliminarTuberia(Map<TuberiaKey, Tuberia> tuberias, Grafo caminos) {
        if (!tuberias.isEmpty()) {
            Color.print("Seleccione la tubería a eliminar:");
            mostrarTuberias(tuberias);
            Color.print("Ingrese la nomenclatura de origen y destino (ej: AB3001-CD3002): ");
            String input = sc.nextLine().trim();
            String[] partes = input.split("-");

            if (partes.length == 2 && MetodosCiudad.validarNomenclatura(partes[0])
                    && MetodosCiudad.validarNomenclatura(partes[1])) {
                String origen = partes[0].trim();
                String destino = partes[1].trim();
                TuberiaKey key = new TuberiaKey(origen, destino);
                if (tuberias.containsKey(key)) {
                    tuberias.remove(key);
                    caminos.eliminarArco(origen, destino);
                    Log.mensaje("Tubería eliminada: " + key).print().guardar();
                } else {
                    Log.mensaje("Error: Tubería no encontrada.").print(Color.ROJO).guardar();
                }
            }
        } else {
            Log.mensaje("No hay tuberías cargadas.").print(Color.ROJO).guardar();
        }
    }

    public static void mostrarTuberias(Map<TuberiaKey, Tuberia> tuberias) {
        if (!tuberias.isEmpty()) {
            Color.print("Listado de tuberías:");
            tuberias.forEach((k, v) -> System.out.println(k + " = " + v));
        } else {
            Log.mensaje("No hay tuberías cargadas.").print(Color.ROJO).guardar();
        }
    }

    public static void agregarTuberia(Map<TuberiaKey, Tuberia> tuberias, ArbolAVL arbol, Grafo caminos) {
        Color.print("Ingrese la ciudad de origen: ");
        String origen = sc.nextLine().trim();
        Color.print("Ingrese la ciudad de destino: ");
        String destino = sc.nextLine().trim();
        Ciudad ciudadOrigen = (Ciudad) arbol.obtener(new Ciudad(origen));
        Ciudad ciudadDestino = (Ciudad) arbol.obtener(new Ciudad(destino));

        if (ciudadOrigen == null || ciudadDestino == null) {
            Log.mensaje("Error: Una o ambas ciudades no existen.").print(Color.ROJO).guardar();
        } else {
            Color.print("Ingrese el diámetro de la tubería: ");
            int diametro = Integer.parseInt(sc.nextLine().trim());
            Color.print("Ingrese el caudal máximo: ");
            int caudalMax = Integer.parseInt(sc.nextLine().trim());
            Color.print("Ingrese el caudal mínimo: ");
            int caudalMin = Integer.parseInt(sc.nextLine().trim());

            if (diametro <= 0 || caudalMin <= 0 || caudalMax <= 0 || caudalMin >= caudalMax) {
                Log.mensaje("Error: Valores de diámetro o caudales inválidos.").print(Color.ROJO).guardar();
            } else {
                TuberiaKey key = new TuberiaKey(ciudadOrigen.getNomenclatura(), ciudadDestino.getNomenclatura());
                if (tuberias.containsKey(key)) {
                    Log.mensaje("Error: Ya existe una tubería entre estas ciudades.").print(Color.ROJO).guardar();
                } else {
                    Tuberia tuberia = new Tuberia(ciudadOrigen.getNomenclatura(), ciudadDestino.getNomenclatura(),
                            diametro, caudalMax, caudalMin, Estado.ACTIVO);

                    tuberia.setEstado(obtenerEstadoTuberia());

                    tuberias.put(key, tuberia);

                    caminos.insertarArco(ciudadOrigen.getNomenclatura(), ciudadDestino.getNomenclatura(),
                            tuberia.getCaudalMax());

                    Log.mensaje("Tubería agregada: " + tuberia).print().guardar();
                }
            }

        }

    }

    public static void buscarTuberia(Map<TuberiaKey, Tuberia> tuberias) {
        Color.print("Ingrese la nomenclatura de origen y destino (ej: AB3001-CD3002): ");
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
                Log.mensaje("Error: Tubería no encontrada.").print(Color.ROJO).guardar();
            }
        } else {
            Log.mensaje("Error: Formato de entrada incorrecto.").print(Color.ROJO).guardar();
        }
    }

    public static void obtenerCaminoDeCaudalPlenoMinimo(ArbolAVL ciudades, Grafo caminos,
            Map<TuberiaKey, Tuberia> tuberias) {

        Ciudad ciudadOrigen, ciudadDestino;
        String ciudadOrigenNombre, ciudadDestinoNombre, nomenclaturaOrigen, nomenclaturaDestino, estadoCamino;
        Lista caminoCaudalPlenoMinimo;

        Color.print("Ingrese el nombre de la ciudad origen:");
        ciudadOrigenNombre = sc.nextLine().trim();
        ciudadOrigen = MetodosCiudad.obtenerCiudad(ciudades, ciudadOrigenNombre);

        if (ciudadOrigen != null) {
            Color.print("Ingrese el nombre de la ciudad destino:");
            ciudadDestinoNombre = sc.nextLine().trim();
            ciudadDestino = MetodosCiudad.obtenerCiudad(ciudades, ciudadDestinoNombre);

            if (ciudadDestino != null) {
                nomenclaturaOrigen = ciudadOrigen.getNomenclatura();
                nomenclaturaDestino = ciudadDestino.getNomenclatura();

                // Verifica que la nomenclatura de ambas ciudades no sean las mismas
                if (!nomenclaturaOrigen.equals(nomenclaturaDestino)) {
                    caminoCaudalPlenoMinimo = caminos.caminoCaudalPlenoMinimo(nomenclaturaOrigen, nomenclaturaDestino);

                    // Verifica que exista un camino entre la ciudad origen y destino
                    if (!caminoCaudalPlenoMinimo.esVacia()) {
                        estadoCamino = obtenerEstadoCamino(caminoCaudalPlenoMinimo, tuberias); // Obtiene el estado del
                                                                                               // camino

                        // Resultados
                        Log.mensaje("Camino desde " + nomenclaturaOrigen + " hasta " +
                                nomenclaturaDestino + " con el caudal pleno minimo entre todos los "
                                + "caminos posibles: " + caminoCaudalPlenoMinimo.toString()).print().guardar();
                        Log.mensaje("Estado del camino: " + estadoCamino).print().guardar();
                    } else {
                        Color.printErr("No existe un camino desde " + nomenclaturaOrigen + " hasta " +
                                nomenclaturaDestino);
                    }
                } else {
                    Color.printErr("No hay caminos porque la ciudad origen y destino es la misma");
                }
            } else {
                Color.printErr("La ciudad destino ingresada no existe en el sistema");
            }
        } else {
            Color.printErr("La ciudad origen ingresada no existe en el sistema");
        }
    }

    private static String obtenerEstadoCamino(Lista camino, Map<TuberiaKey, Tuberia> tuberias) {

        int pos1, pos2, longitud;
        String estado;
        Tuberia tuberia;
        boolean terminar = false;

        pos1 = 1; // Posicion de la ciudad1
        pos2 = 2; // Posicion de la ciudad2
        longitud = camino.longitud(); // Se guarda la longitud para no estar calculandola constantemente en el
                                      // while
        estado = "Activo"; // Estado por defecto

        while (pos2 <= longitud && !terminar) {
            tuberia = obtenerTuberia((String) camino.recuperar(pos1), (String) camino.recuperar(2), tuberias);

            switch (tuberia.getEstado().getNombre()) {
                case "Diseño" -> {
                    // Si una tubería está en diseño, todo el camino está en diseño y se termina el
                    // bucle
                    estado = "Diseño";
                    terminar = true;
                }

                case "Inactivo" -> {
                    estado = "Inactivo";
                }

                case "Reparación" -> {
                    // El estado actual del camino está en reparación, siempre y cuando no se haya
                    // encontrado una tubería inactiva
                    if (!estado.equals("Inactivo")) {
                        estado = "Reparación";
                    }
                }

            }

            pos1 += 1;
            pos2 += 1;
        }

        return estado;

    }

    private static Tuberia obtenerTuberia(String nomenclaturaCiudad1, String nomenclaturaCiudad2,
            Map<TuberiaKey, Tuberia> tuberias) {

        TuberiaKey key;
        Tuberia tuberia;

        // Se obtiene la tubería que conecta ambas ciudades en el HashMap
        key = new TuberiaKey(nomenclaturaCiudad1, nomenclaturaCiudad2);
        tuberia = (Tuberia) tuberias.get(key);

        return tuberia;

    }

    public static void obtenerCaminoMasCorto(ArbolAVL ciudades, Grafo caminos, Map<TuberiaKey, Tuberia> tuberias) {

        Ciudad ciudadOrigen, ciudadDestino;
        String ciudadOrigenNombre, ciudadDestinoNombre, nomenclaturaOrigen, nomenclaturaDestino, estadoCamino;
        Lista caminoMasCorto;

        Color.print("Por favor, ingrese el nombre de la ciudad origen");
        ciudadOrigenNombre = sc.nextLine().trim();
        ciudadOrigen = MetodosCiudad.obtenerCiudad(ciudades, ciudadOrigenNombre);

        if (ciudadOrigen != null) {
            Color.print("Por favor, ingrese el nombre de la ciudad destino");
            ciudadDestinoNombre = sc.nextLine().trim();
            ciudadDestino = MetodosCiudad.obtenerCiudad(ciudades, ciudadDestinoNombre);

            if (ciudadDestino != null) {
                nomenclaturaOrigen = ciudadOrigen.getNomenclatura();
                nomenclaturaDestino = ciudadDestino.getNomenclatura();

                // Verifica que la nomenclatura de ambas ciudades no sean las mismas
                if (!nomenclaturaOrigen.equals(nomenclaturaDestino)) {
                    caminoMasCorto = caminos.caminoMasCorto(nomenclaturaOrigen, nomenclaturaDestino);

                    // Verifica que exista un camino entre la ciudad origen y destino
                    if (!caminoMasCorto.esVacia()) {
                        estadoCamino = obtenerEstadoCamino(caminoMasCorto, tuberias); // Obtiene el estado del camino

                        // Resultados
                        Log.mensaje("Camino mas corto desde " + nomenclaturaOrigen + " hasta " +
                                nomenclaturaDestino + ": " + caminoMasCorto.toString()).print().guardar();
                        Log.mensaje("Estado del camino: " + estadoCamino).print().guardar();
                    } else {
                        Color.printErr("No existe un camino desde " + nomenclaturaOrigen + " hasta " +
                                nomenclaturaDestino);
                    }
                } else {
                    Color.printErr("No hay caminos porque la ciudad origen y destino es la misma");
                }
            } else {
                Color.printErr("La ciudad destino ingresada no existe en el sistema");
            }
        } else {
            Color.printErr("La ciudad origen ingresada no existe en el sistema");
        }

    }

}
