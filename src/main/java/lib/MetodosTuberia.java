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
import java.util.HashMap;

import java.util.Scanner;
import java.util.stream.Stream;

public class MetodosTuberia {

    private final static Scanner sc = new Scanner(System.in);

    public static void cargarTuberiasDesde(ArbolAVL arbol, Grafo caminos, HashMap<TuberiaKey, Tuberia> tuberias)
            throws IOException {

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
                                    // Asumimos que los datos son correctos
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
                                            caminos.insertarArco(origen, destino, tuberia.getCaudalMax());
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
            if (partes.length == 2 && MetodosCiudad.validarNomenclatura(partes[0])
                    && MetodosCiudad.validarNomenclatura(partes[1])) {
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

            } else {
                Log.mensaje("Error: Formato de entrada inválido.").print().guardar();
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

    public static void agregarTuberia(HashMap<TuberiaKey, Tuberia> tuberias, ArbolAVL arbol, Grafo caminos) {
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
                    caminos.insertarArco(ciudadOrigen, ciudadDestino, tuberia.getCaudalMax());
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

    public static void obtenerCaminoDeCaudalPlenoMinimo(ArbolAVL ciudades, Grafo caminos,
            HashMap<TuberiaKey, Tuberia> tuberias) {

        Ciudad ciudadOrigen, ciudadDestino;
        Lista todosLosCaminos, unCamino, caminoElegido;
        String estadoCamino;

        System.out.println("Por favor, ingrese el nombre de la ciudad origen");
        ciudadOrigen = obtenerCiudad(ciudades);

        if (ciudadOrigen != null) {
            System.out.println("Por favor, ingrese el nombre de la ciudad destino");
            ciudadDestino = obtenerCiudad(ciudades);

            if (ciudadDestino != null) {
                todosLosCaminos = caminos.todosLosCaminos(ciudadOrigen, ciudadDestino);

                // Verifica que exista un camino entre la ciudad origen y destino
                if (!todosLosCaminos.esVacia()) {
                    // Verifica que la ciudad origen y destino no sea la misma. Si la primer lista
                    // de los caminos tiene
                    // una sola ciudad, significa que la ciudad origen y destino son las mismas y no
                    // hay ningún camino
                    unCamino = (Lista) todosLosCaminos.recuperar(1);

                    if (unCamino.longitud() > 1) {
                        caminoElegido = buscarCaminoDeCaudalPlenoMinimo(todosLosCaminos, tuberias);
                        estadoCamino = obtenerEstadoCamino(caminoElegido, tuberias); // Obtiene el estado del camino

                        // Resultados
                        System.out.println("Camino desde " + ciudadOrigen.getNombre() + " hasta " +
                                ciudadDestino.getNombre() + " con el caudal pleno minimo entre todos los "
                                + "caminos posibles: " + caminoElegido.toString());
                        System.out.println("Estado del camino: " + estadoCamino);
                    } else {
                        System.out.println("No hay caminos porque la ciudad origen y destino es la misma");
                    }

                } else {
                    System.out.println("No existe un camino desde " + ciudadOrigen.getNombre() + " hasta " +
                            ciudadDestino.getNombre());
                }
            } else {
                System.out.println("La ciudad destino ingresada no existe en el sistema");
            }
        } else {
            System.out.println("La ciudad origen ingresada no existe en el sistema");
        }
    }

    private static Ciudad obtenerCiudad(ArbolAVL ciudades) {

        String nombreCiudad;
        Ciudad ciudad, ciudadEncontrada;

        // Verifica si existe la ciudad en el árbol y retorna el resultado
        nombreCiudad = sc.nextLine().trim();
        ciudad = new Ciudad(nombreCiudad);
        ciudadEncontrada = (Ciudad) ciudades.obtener(ciudad);

        return ciudadEncontrada;

    }

    private static Lista buscarCaminoDeCaudalPlenoMinimo(Lista caminos, HashMap<TuberiaKey, Tuberia> tuberias) {

        Lista unCamino, caminoElegido;
        int pos, longitud;
        Tuberia tuberia, tuberiaCaudalMinimo;

        caminoElegido = new Lista();
        pos = 1;
        longitud = caminos.longitud();
        tuberiaCaudalMinimo = null;

        while (pos <= longitud) {
            unCamino = (Lista) caminos.recuperar(pos);

            tuberia = tuberiaDiametroMasPequenio(unCamino, tuberias);

            // Verifica que el caudal de la tubería con el diametro más pequeño del camino,
            // sea más pequeño que el
            // caudal de la tubería con el diametro más pequeño de un camino encontrado
            // hasta ahora
            if (tuberiaCaudalMinimo == null || tuberia.getCaudalMax() < tuberiaCaudalMinimo.getCaudalMax()) {
                tuberiaCaudalMinimo = tuberia;
                caminoElegido = unCamino;
            }

            pos += 1;
        }

        return caminoElegido;

    }

    private static Tuberia tuberiaDiametroMasPequenio(Lista camino, HashMap<TuberiaKey, Tuberia> tuberias) {

        int pos1, pos2, longitud, diametroMasPequenio;
        Tuberia tuberia, tuberiaElegida;

        pos1 = 1;
        pos2 = 2;
        longitud = camino.longitud();
        diametroMasPequenio = 0;
        tuberiaElegida = null;

        while (pos2 <= longitud) {
            tuberia = obtenerTuberia(camino, pos1, pos2, tuberias);

            // Verifica que el diametro de la tuberia sea más pequeño que el diametro más
            // pequeño encontrado hasta ahora o
            // si es la primer tuberia que se revisa
            if (tuberia.getDiametro() < diametroMasPequenio || diametroMasPequenio <= 0) {
                diametroMasPequenio = tuberia.getDiametro();
                tuberiaElegida = tuberia;
            }

            pos1 += 1;
            pos2 += 1;
        }

        return tuberiaElegida;

    }

    private static Tuberia obtenerTuberia(Lista camino, int pos1, int pos2, HashMap<TuberiaKey, Tuberia> tuberias) {

        String nomenclaturaCiudad1, nomenclaturaCiudad2;
        TuberiaKey key;
        Tuberia tuberia;

        // Se obtienen las nomenclaturas de las ciudades para obtener la tubería que las
        // conecta en el HashMap
        nomenclaturaCiudad1 = (String) ((Ciudad) camino.recuperar(pos1)).getNomenclatura();
        nomenclaturaCiudad2 = (String) ((Ciudad) camino.recuperar(pos2)).getNomenclatura();

        key = new TuberiaKey(nomenclaturaCiudad1, nomenclaturaCiudad2);
        tuberia = (Tuberia) tuberias.get(key);

        return tuberia;

    }

    private static String obtenerEstadoCamino(Lista caminoMasCorto, HashMap<TuberiaKey, Tuberia> tuberias) {

        int pos1, pos2, longitud;
        String estado;
        Tuberia tuberia;
        boolean terminar = false;

        pos1 = 1; // Posicion de la ciudad1
        pos2 = 2; // Posicion de la ciudad2
        longitud = caminoMasCorto.longitud(); // Se guarda la longitud para no estar calculandola constantemente en el
                                              // while
        estado = "ACTIVO"; // Estado por defecto

        while (pos2 <= longitud && !terminar) {
            tuberia = obtenerTuberia(caminoMasCorto, pos1, pos2, tuberias);

            switch (tuberia.getEstado()) {
                case Estado.DISENIO -> {
                    // Si una tubería está en diseño, todo el camino está en diseño y se termina el
                    // bucle
                    estado = "EN DISENIO";
                    terminar = true;
                }

                case Estado.INACTIVO -> {
                    estado = "INACTIVO";
                }

                case Estado.REPARACION -> {
                    // El estado actual del camino está en reparación, siempre y cuando no se haya
                    // encontrado una tubería inactiva
                    if (!estado.equals("INACTIVO")) {
                        estado = "EN REPARACION";
                    }
                }
                
            }

            pos1 += 1;
            pos2 += 1;
        }

        return estado;

    }

    public static void obtenerCaminoMasCorto(ArbolAVL ciudades, Grafo caminos, HashMap<TuberiaKey, Tuberia> tuberias) {

        Ciudad ciudadOrigen, ciudadDestino;
        Lista caminoMasCorto;
        String estadoCamino;

        System.out.println("Por favor, ingrese el nombre de la ciudad origen");
        ciudadOrigen = obtenerCiudad(ciudades);

        if (ciudadOrigen != null) {
            System.out.println("Por favor, ingrese el nombre de la ciudad destino");
            ciudadDestino = obtenerCiudad(ciudades);

            if (ciudadDestino != null) {
                caminoMasCorto = caminos.caminoMasCorto(ciudadOrigen, ciudadDestino);

                // Verifica que exista un camino entre la ciudad origen y destino
                if (!caminoMasCorto.esVacia()) {
                    // Verifica que la ciudad origen y destino no sea la misma
                    if (caminoMasCorto.longitud() > 1) {
                        estadoCamino = obtenerEstadoCamino(caminoMasCorto, tuberias); // Obtiene el estado del camino

                        // Resultados
                        System.out.println("Camino mas corto desde " + ciudadOrigen.getNombre() + " hasta " +
                                ciudadDestino.getNombre() + ": " + caminoMasCorto.toString());
                        System.out.println("Estado del camino: " + estadoCamino);
                    } else {
                        System.out.println("No hay camino porque la ciudad origen y destino es la misma");
                    }

                } else {
                    System.out.println("No existe un camino desde " + ciudadOrigen.getNombre() + " hasta " +
                            ciudadDestino.getNombre());
                }
            } else {
                System.out.println("La ciudad destino ingresada no existe en el sistema");
            }
        } else {
            System.out.println("La ciudad origen ingresada no existe en el sistema");
        }

    }

}
