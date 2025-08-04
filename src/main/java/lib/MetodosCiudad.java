package lib;

import entidades.Ciudad;
import entidades.Tuberia;
import entidades.TuberiaKey;
import estructuras.conjuntistas.dinamicas.ArbolAVL;
import estructuras.grafos.Grafo;
import estructuras.lineales.dinamicas.Lista;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Stream;

public class MetodosCiudad {

    private static final String REG = "^[A-Z]{2}\\d{4}$";
    private static final Scanner sc = new Scanner(System.in);
    private static final String letras = "^[A-Za-zÁÉÍÓÚáéíóúÑñ\\-\\s]+$";
    private static final String numeros = "^\\d+$";
    private static final String decimales = "^\\d+\\.\\d{2}$";

    public static void cargarCiudadesDesde(ArbolAVL arbol, Grafo caminos) throws IOException {

        Color.print(Color.MAGENTA, "Cargando ciudades...");
        File archivo = Load.cargarArchivo("Seleccione un archivo CSV de ciudades");
        if (archivo != null) {
            Log.mensaje("Cargando ciudades desde: " + archivo.getAbsolutePath())
                    .print(Color.MAGENTA).guardar();

            // Pone el archivo en una lista(stream) para luego poder recorrerla
            // eficientemente, separando cada linea por ";"
            // y luego se valida que tenga la cantidad de campos necesarios para crear una
            // ciudad.
            // Si es válido lo inserta en el árbol, si no, se guarda un mensaje de error en
            // el log.
            try (Stream<String> lineas = Files.lines(archivo.toPath())) {
                lineas.skip(1)
                        .map(l -> l.split(";"))
                        .forEach(l -> {
                            if (l.length == 5) {
                                if (validar(l[0], l[1], l[2], l[3], l[4])) {
                                    Ciudad ciudad = new Ciudad(l[0], l[1], Integer.parseInt(l[2]),
                                            Double.parseDouble(l[3]), Integer.parseInt(l[4]));
                                    agregarCiudad(arbol, caminos, ciudad);
                                    Log.mensaje("Ciudad cargada: " + ciudad.getNombre() + " con nomenclatura: "
                                            + ciudad.getNomenclatura())
                                            .print(Color.VERDE).guardar();
                                } else {
                                    Log.mensaje("Error al cargar la ciudad: " + l[0] + ". Formato incorrecto.")
                                            .print(Color.ROJO).guardar();

                                }
                            } else {
                                Log.mensaje("Error al cargar la ciudad: " + l[0] + ". Formato incorrecto.")
                                        .print(Color.ROJO).guardar();
                            }
                        });
            } catch (NumberFormatException e) {
                Log.mensaje("Error al cargar las ciudades: " + e.getMessage())
                        .print(Color.ROJO).guardar();

            }
        }

    }

    public static void modificarCiudad(ArbolAVL arbol) {
        Color.print("Ingrese el nombre de la ciudad a la que desea modificar:");
        String nombre = sc.nextLine().trim();

        if (nombre.isEmpty() || !nombre.matches(letras)) {
            Log.mensaje("El nombre de la ciudad no puede estar vacío.")
                    .print(Color.ROJO).guardar();
        } else {
            Ciudad res = obtenerCiudad(arbol, nombre);
            if (res != null) {
                System.out.println(
                        String.format(" %s - Modificar cantidad habitantes\n %s - Modificar cantidad de consumo",
                                Color.aplicar(Color.AZUL, "1"),
                                Color.aplicar(Color.AZUL, "2")));
                int opcion = Integer.parseInt(sc.nextLine().trim());

                switch (opcion) {
                    case 1 -> {
                        modificarHabitantesCiudad(res);
                    }
                    case 2 -> {
                        modificarConsumoCiudad(res);
                    }
                    default -> {
                        Color.printErr("Opción no válida. Por favor, elija una opción correcta.");
                    }
                }
            } else {
                Log.mensaje("La ciudad " + nombre + " no existe en el sistema.")
                        .print(Color.ROJO).guardar();
            }
        }
    }

    private static void modificarHabitantesCiudad(Ciudad ciudad) {
        Color.print("Ingrese el año:");
        int anio = Integer.parseInt(sc.nextLine().trim());
        Color.print("Ingrese el mes (1-12):");
        int mes = Integer.parseInt(sc.nextLine().trim());
        Color.print("Ingrese la cantidad de habitantes:");
        int cantHabitantes = Integer.parseInt(sc.nextLine().trim());

        if (ciudad.agregarCantHabitantes(anio, mes, cantHabitantes)) {
            Log.mensaje("Cantidad de habitantes agregada a la ciudad " + ciudad.getNombre() + " para el año "
                    + anio + " y mes " + mes)
                    .print(Color.VERDE).guardar();
        } else {
            Log.mensaje("Error al agregar habitantes a la ciudad " + ciudad.getNombre()
                    + ". Verifique los datos ingresados.")
                    .print(Color.ROJO).guardar();
        }
    }

    private static void modificarConsumoCiudad(Ciudad ciudad) {

        Color.print("Ingrese consumo de agua por persona (m3):");
        double consumo = Double.parseDouble(sc.nextLine().trim());
        if (consumo > 0) {
            ciudad.setCantM3Persona(consumo);
            Log.mensaje("Consumo de agua por persona modificado a " + consumo + " m3.")
                    .print().guardar();
        } else {
            Color.printErr("El consumo de agua debe ser un número positivo.");
        }

    }

    private static boolean validar(String nombre, String nomenclatura, String superficie, String cantM3Persona,
            String habitantes) {
        return nombre.matches(letras) && crearNomenclatura(nombre, nomenclatura) && superficie.matches(numeros)
                && cantM3Persona.matches(decimales) && habitantes.matches(numeros);
    }

    public static boolean agregarCiudad(ArbolAVL arbol, Grafo caminos, Ciudad ciudad) {
        boolean res = false;

        if (ciudad != null) {
            if (arbol.pertenece(ciudad)) {
                Log.mensaje("La ciudad " + ciudad.getNombre() + " ya existe en el sistema.")
                        .print(Color.ROJO).guardar();

            } else {
                arbol.insertar(ciudad);
                caminos.insertarVertice(ciudad.getNomenclatura());
                Log.mensaje("Ciudad " + ciudad.getNombre() + " agregada al sistema.")
                        .print().guardar();
                res = true;
            }
        }

        return res;

    }

    public static void agregarCiudadInput(ArbolAVL arbol, Grafo caminos) {
        String nombre, nomenclatura, superficie, cantM3Persona;
        Ciudad ciudad = null;
        boolean res = false;

        Color.print("Ingrese los datos de la ciudad:");
        nombre = sc.nextLine();

        Color.print("Ingresar nomenclatura de la ciudad (Formato: AA0000): ");
        nomenclatura = sc.nextLine();

        System.out.println();
        Color.print("Ingresar superficie de la ciudad (en m2): ");
        superficie = sc.nextLine();

        Color.print("Ingresar cantidad de m3 por persona: ");
        cantM3Persona = sc.nextLine();

        Color.print("Ingrese cantidad de habitantes:");
        String habitantes = sc.nextLine();

        System.out.println();

        if (validar(nombre, nomenclatura, superficie, cantM3Persona, habitantes)) {
            try {
                ciudad = new Ciudad(nombre, nomenclatura, Integer.parseInt(superficie),
                        Double.parseDouble(cantM3Persona), Integer.parseInt(habitantes));
                res = agregarCiudad(arbol, caminos, ciudad);
                if (res) {
                    Log.mensaje("Ciudad " + ciudad.getNombre() + " agregada al sistema.")
                            .print().guardar();

                } else {
                    Log.mensaje("La ciudad " + ciudad.getNombre() + " ya existe en el sistema.")
                            .print(Color.ROJO).guardar();

                }
            } catch (NumberFormatException e) {
                Log.mensaje("Error al agregar la ciudad: " + e.getMessage())
                        .print(Color.ROJO).guardar();
            }

        }
    }

    private static boolean crearNomenclatura(String nombre, String nomenclatura) {
        boolean res = false;
        StringBuilder sb = new StringBuilder();
        String nombreMayus = nombre.toUpperCase().trim();
        int numero = Integer.parseInt(nomenclatura.substring(2));

        if (numero > 3000 && numero < 4000 && nomenclatura.matches(REG)) {
            String[] partes = nombreMayus.split("[\\\\s-]");
            if (partes.length == 2) {
                sb.append(partes[0].substring(0, 1)).append(partes[1].substring(0, 1));
            } else {
                sb.append(partes[0].substring(0, 2));
            }
            sb.append(numero);
            if (sb.toString().equals(nomenclatura)) {
                res = true;
            }

        }

        return res;
    }

    public static boolean validarNomenclatura(String nomenclatura) {
        return nomenclatura.matches(REG) &&
                Integer.parseInt(nomenclatura.substring(2)) > 3000 &&
                Integer.parseInt(nomenclatura.substring(2)) < 4000;
    }

    public static void eliminarCiudad(ArbolAVL arbol, Map<TuberiaKey, Tuberia> tuberias, Grafo caminos) {
        System.out.println(Color.aplicar(Color.CYAN, "Ingrese el nombre de la ciudad a eliminar:"));
        String nombre = sc.nextLine().trim();
        if (nombre.isEmpty() || !nombre.matches(letras)) {
            Log.mensaje("El nombre de la ciudad no puede estar vacío.")
                    .print(Color.ROJO).guardar();
        } else {
            Ciudad ciudad = new Ciudad(nombre);
            Ciudad ciudadEncontrada = (Ciudad) arbol.obtener(ciudad);
            if (ciudadEncontrada != null) {
                arbol.eliminar(ciudad);
                caminos.eliminarVertice(ciudadEncontrada.getNomenclatura());

                Iterator<Map.Entry<TuberiaKey, Tuberia>> iterador = tuberias.entrySet().iterator();

                while (iterador.hasNext()) {
                    Map.Entry<TuberiaKey, Tuberia> entrada = iterador.next();

                    if (entrada.getKey().getNomenclaturaOrigen().equals(ciudadEncontrada.getNomenclatura()) ||
                            entrada.getKey().getNomenclaturaDestino().equals(ciudadEncontrada.getNomenclatura())) {
                        iterador.remove();
                    }
                }
                Log.mensaje("Ciudad " + ciudad.getNombre() + " eliminada del sistema.")
                        .print().guardar();
            } else {
                Log.mensaje("La ciudad " + ciudad.getNombre() + " no existe en el sistema.")
                        .print(Color.ROJO).guardar();
            }
        }
    }

    public static void buscarCiudad(ArbolAVL arbol) {
        Color.print("Ingrese el nombre de la ciudad a buscar:");
        String nombre = sc.nextLine().trim();
        if (nombre.isEmpty() || !nombre.matches(letras)) {
            Log.mensaje("El nombre de la ciudad no puede estar vacío.")
                    .print(Color.ROJO).guardar();
        } else {
            Ciudad res = obtenerCiudad(arbol, nombre);
            if (res != null) {
                Log.mensaje("Ciudad encontrada: " + res.getNombre())
                        .print().guardar();
            } else {
                Log.mensaje("La ciudad " + nombre + " no existe en el sistema.")
                        .print(Color.ROJO).guardar();
            }
        }
    }

    public static void mostrarHabitantesCiudad(ArbolAVL arbol) {
        Color.print("Ingrese el nombre de la ciudad para mostrar sus habitantes:");
        String nombre = sc.nextLine().trim();
        if (nombre.isEmpty() || !nombre.matches(letras)) {
            Log.mensaje("El nombre de la ciudad no puede estar vacío.")
                    .print().guardar();
        } else {
            Ciudad res = obtenerCiudad(arbol, nombre);
            if (res != null) {
                String habitantes = res.obtenerTodosLosHabitantes();
                Log.mensaje("Habitantes de la ciudad " + res + ":\n" + habitantes)
                        .print().guardar();
            } else {
                Log.mensaje("La ciudad " + nombre + " no existe en el sistema.")
                        .print().guardar();
            }
        }
    }

    public static void mostrarDatosCiudad(ArbolAVL arbol) {
        Color.print("Ingrese el nombre de la ciudad para mostrar sus datos:");
        String nombre = sc.nextLine().trim();
        if (nombre.isEmpty() || !nombre.matches(letras)) {
            Log.mensaje("El nombre de la ciudad no puede estar vacío.")
                    .print(Color.ROJO).guardar();
        } else {
            Ciudad res = obtenerCiudad(arbol, nombre);
            if (res != null) {
                Log.mensaje("Datos de la ciudad " + res.getNombre() + ":\n" + res.toString())
                        .print().guardar();
            } else {
                Log.mensaje("La ciudad " + nombre + " no existe en el sistema.")
                        .print(Color.ROJO).guardar();
            }
        }
    }

    public static Ciudad obtenerCiudad(ArbolAVL ciudades, String nombreCiudad) {
        Ciudad ciudadEncontrada = null;
        // Si el nombre no está vacío y tiene los caracteres correctos, entonces
        // verifica si la ciudad esta en el árbol
        if (!nombreCiudad.isEmpty() && nombreCiudad.matches(letras)) {

            Ciudad ciudad = new Ciudad(nombreCiudad);
            ciudadEncontrada = (Ciudad) ciudades.obtener(ciudad);

        }
        return ciudadEncontrada;
    }

    public static void mostrarCantHabitantesYConsumo(ArbolAVL ciudades) {
        String nombreCiudad, nombreMes;
        Ciudad ciudad, ciudadEncontrada;
        int anio, mes;
        Integer cantHabitantes;
        double volumenAgua;

        Color.print("Ingrese el nombre de la ciudad");
        nombreCiudad = sc.nextLine().trim();

        // Verifica que el nombre ingresado no esté vacío y tenga los caracteres
        // correctos
        if (!nombreCiudad.isEmpty() && nombreCiudad.matches(letras)) {
            ciudad = new Ciudad(nombreCiudad);
            ciudadEncontrada = (Ciudad) ciudades.obtener(ciudad);

            if (ciudadEncontrada != null) {
                Color.print("Ingrese el año");
                anio = Integer.parseInt(sc.nextLine().trim());

                Color.print("Ingrese el numero del mes (1-12)");
                mes = Integer.parseInt(sc.nextLine().trim());

                cantHabitantes = ciudadEncontrada.getCantHabitantes(anio, mes); // Obtiene la cantidad de habitantes

                if (cantHabitantes != null) {
                    volumenAgua = calcularVolumenAgua(ciudadEncontrada, cantHabitantes, mes); // Calcula el volumen de
                                                                                              // agua
                    nombreMes = obtenerNombreMes(mes); // Obtiene el nombre del mes por su número

                    Log.mensaje(
                            "Cantidad de habitantes en " + nombreCiudad + " en " + nombreMes + " de " + anio + ": " +
                                    cantHabitantes)
                            .print().guardar();
                    Log.mensaje("Volumen de agua que se habria distribuido en " + nombreCiudad + " en " + nombreMes +
                            " de " + anio + ": " + volumenAgua).print().guardar();
                } else {
                    Color.printErr("El año o mes ingresado es incorrecto");
                }
            } else {
                Color.printErr("La ciudad ingresada no existe en el sistema");
            }
        } else {
            Color.printErr("El nombre de la ciudad no puede estar vacio o tiene caracteres incorrectos");
        }

    }

    private static double calcularVolumenAgua(Ciudad ciudad, int cantHabitantes, int mes) {
        double consumoPromedio = ciudad.getCantM3Persona();
        int cantDias = obtenerCantDias(mes);

        return cantHabitantes * consumoPromedio * cantDias;
    }

    private static String obtenerNombreMes(int numeroMes) {
        String nombreMes = "";

        switch (numeroMes) {
            case 1 -> nombreMes = "Enero";
            case 2 -> nombreMes = "Febrero";
            case 3 -> nombreMes = "Marzo";
            case 4 -> nombreMes = "Abril";
            case 5 -> nombreMes = "Mayo";
            case 6 -> nombreMes = "Junio";
            case 7 -> nombreMes = "Julio";
            case 8 -> nombreMes = "Agosto";
            case 9 -> nombreMes = "Septiembre";
            case 10 -> nombreMes = "Octubre";
            case 11 -> nombreMes = "Noviembre";
            case 12 -> nombreMes = "Diciembre";
        }

        return nombreMes;
    }

    private static int obtenerCantDias(int numeroMes) {
        int cantDias;

        if (numeroMes == 1 || numeroMes == 3 || numeroMes == 5 || numeroMes == 7 || numeroMes == 8 || numeroMes == 10 ||
                numeroMes == 12) {
            // Devuelve la cantidad de días de los meses Enero, Marzo, Mayo, Julio, Agosto,
            // Octubre y Diciembre
            cantDias = 31;
        } else {
            if (numeroMes == 4 || numeroMes == 6 || numeroMes == 9 || numeroMes == 11) {
                // Devuelve la cantidad de días de los meses Abril, Junio, Septiembre y
                // Noviembre
                cantDias = 30;
            } else {
                // Devuelve la cantidad de días del mes de Febrero
                cantDias = 28;
            }
        }
        return cantDias;
    }

    public static void generarListaCiudadesConsumo(ArbolAVL arbol) {
        if (!arbol.esVacio()) {
            // Se utilizo Treemap para ordenar los elementos de mayor a menor
            // ya que si usabamos Heap + Lista, no podriamos almacenar el nombre de la
            // ciudad.
            // Y hashmap no guarda los elementos de forma ordenada.
            TreeMap<Double, String> ciudadesOrdenadas = new TreeMap<>(Collections.reverseOrder());
            Color.print("Ingresar año: ");

            int res = Integer.parseInt(sc.nextLine());
            Ciudad el = (Ciudad) arbol.maximoElem();

            while (!el.anioValido(res)) {
                Color.print("Ingresar un año valido: ");
                res = Integer.parseInt(sc.nextLine());
            }
            Lista listaCiudades = arbol.listar();
            double aux = 0.0;
            for (int i = 1; i <= listaCiudades.longitud(); i++) {
                Ciudad ciudad = (Ciudad) listaCiudades.recuperar(i);
                for (int j = 1; j <= 12; j++) {
                    aux = calcularVolumenAgua(ciudad, ciudad.getCantHabitantes(res, j), j);
                }
                ciudadesOrdenadas.put(aux, ciudad.getNombre());
            }

            for (Double key : ciudadesOrdenadas.keySet()) {
                Color.print(Color.CYAN, String.format("%.2f", key) + " " + ciudadesOrdenadas.get(key));
            }

        }

    }

    public static void mostrarCiudadesRango(ArbolAVL ciudades) {
        Lista rangoCiudades = new Lista();
        Lista ciudadesFiltradas = new Lista();
        Ciudad minNomb, maxNomb;
        double minVol, maxVol;
        Color.print("Ingrese el nombre minimo del rango: ");
        minNomb = new Ciudad(sc.nextLine().trim());
        Color.print("Ingrese el nombre maximo del rango: ");
        maxNomb = new Ciudad(sc.nextLine().trim());
        Color.print("Ingrese el volumen minimo de agua: ");
        minVol = Double.parseDouble(sc.nextLine().trim());
        Color.print("Ingrese el volumen maximo: ");
        maxVol = Double.parseDouble(sc.nextLine().trim());
        rangoCiudades = ciudades.listarRango(minNomb, maxNomb);

        if (!rangoCiudades.esVacia()) {
            int mes, anio;
            Color.print("Ingresar año:");
            anio = Integer.parseInt(sc.nextLine().trim());
            Color.print("Ingresar el numero del mes (1-12):");
            mes = Integer.parseInt(sc.nextLine().trim());
            for (int i = 1; i <= rangoCiudades.longitud(); i++) {
                Ciudad ciudadAux = (Ciudad) rangoCiudades.recuperar(i);
                Integer habi = ciudadAux.getCantHabitantes(anio, mes);
                if (habi != null) {
                    Double volAgua = calcularVolumenAgua(ciudadAux, habi, mes);
                    if (volAgua >= minVol && volAgua <= maxVol) {
                        ciudadesFiltradas.insertar(ciudadAux.getNombre(), ciudadesFiltradas.longitud() + 1);
                    }
                } else {
                    Color.printErr("El año o mes ingresado es incorrecto");
                }
            }
        } else {
            Color.printErr("No se encontraron ciudades dentro del rango");
        }
        Log.mensaje(ciudadesFiltradas.toString()).print().guardar();
    }

    public static void mostrarListaCiudades(ArbolAVL arbol) {
        Lista listaCiudades = arbol.listar();
        if (!listaCiudades.esVacia()) {
            Color.printOk("Lista de ciudades:");
            for (int i = 1; i <= listaCiudades.longitud(); i++) {
                Ciudad ciudad = (Ciudad) listaCiudades.recuperar(i);
                System.out.println(String.format("%s - %s", Color.aplicar(Color.AZUL, String.valueOf(i)),
                        Color.aplicar(ciudad.getNomenclatura() + " - " + ciudad.getNombre())));
            }
        } else {
            Color.printErr("No hay ciudades cargadas en el sistema.");
        }
    }
}
