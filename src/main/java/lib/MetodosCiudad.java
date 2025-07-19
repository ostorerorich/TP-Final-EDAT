package lib;

import entidades.Ciudad;
import estructuras.conjuntistas.dinamicas.ArbolAVL;
import estructuras.grafos.Grafo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.stream.Stream;

public class MetodosCiudad {

    private static final String REG = "^[A-Z]{2}\\d{4}$";
    private static final Scanner sc = new Scanner(System.in);
    private static final String letras = "^[A-Za-zÁÉÍÓÚáéíóúÑñ\\-\\s]+$";
    private static final String numeros = "^\\d+$";
    private static final String decimales = "^\\d+\\.\\d{2}$";

    // TODO: agregar validaciones del archivo.
    // TODO: Simplificar el codigo, crear un metodo generico para cargar los datos desde un archivo.
    public static void cargarCiudadesDesde(ArbolAVL arbol, Grafo caminos) throws IOException {

        System.out.println("Cargando ciudades...");
        File archivo = Load.cargarArchivo();
        if(archivo != null){
            Log.mensaje("Cargando ciudades desde: " + archivo.getAbsolutePath())
                    .print().guardar();

            // Pone el archivo en una lista(stream) para luego poder recorrerla eficientemente, separando cada linea por ";"
            // y luego se valida que tenga la cantidad de campos necesarios para crear una ciudad.
            // Si es válido lo inserta en el árbol, si no, se guarda un mensaje de error en el log.
            try(Stream<String> lineas = Files.lines(archivo.toPath())){
                lineas.skip(1)
                        .map(l -> l.split(";"))
                        .forEach(l -> {
                            if(l.length == 4){
                                    if(validar(l[0],l[1], l[2], l[3])) {
                                        Ciudad ciudad = new Ciudad(l[0], l[1], Integer.parseInt(l[2]), Double.parseDouble(l[3]));
                                        agregarCiudad(arbol, caminos, ciudad);
                                        Log.mensaje("Ciudad cargada: " + ciudad.getNombre() + " con nomenclatura: " + ciudad.getNomenclatura())
                                                .print().guardar();
                                    }else{
                                        Log.mensaje("Error al cargar la ciudad: " + l[0] + ". Formato incorrecto.")
                                                .print().guardar();

                                    }
                            }else {
                                Log.mensaje("Error al cargar la ciudad: " + l[0] + ". Formato incorrecto.").guardar();
                            }
                        });
            }catch(NumberFormatException e){
                Log.mensaje("Error al cargar las ciudades: " + e.getMessage())
                        .print().guardar();


            }
        }

    }


    // Checkear todo lo que es input validaciones y agregar ciudades.
    //TODO: this
    private static boolean validar(String nombre, String nomenclatura, String superficie, String cantM3Persona){
        return nombre.matches(letras) && validarNomenclatura(nombre, nomenclatura) && superficie.matches(numeros) && cantM3Persona.matches(decimales);
    }
    //TODO: this
    public static boolean agregarCiudad(ArbolAVL arbol, Grafo caminos, Ciudad ciudad) {
        boolean res = false;

        if(ciudad !=null){
            if(arbol.pertenece(ciudad)){
                Log.mensaje("La ciudad " + ciudad.getNombre() + " ya existe en el sistema.")
                        .print().guardar();

            }else{
                arbol.insertar(ciudad);
                caminos.insertarVertice(ciudad);
                Log.mensaje("Ciudad " + ciudad.getNombre() + " agregada al sistema.")
                        .print().guardar();
                res = true;
            }
        }

        return res;

    }


    //TODO: this
    public static void agregarCiudadInput(ArbolAVL arbol, Grafo caminos) {
        Ciudad ciudad = null;
        boolean res = false;
        String nombre, nomenclatura, superficie, cantM3Persona;

        System.out.println("Ingresar nombre de la ciudad:");
        nombre = sc.nextLine();
        System.out.print("Ingresar nomenclatura de la ciudad (Formato: AA0000): ");
        nomenclatura = sc.nextLine();
        System.out.println();
        System.out.print("Ingresar superficie de la ciudad (en m2): ");
        superficie = sc.nextLine();
        System.out.println();
        System.out.print("Ingresar cantidad de m3 por persona: ");
        cantM3Persona = sc.nextLine();
        System.out.println();
        if (validar(nombre, nomenclatura, superficie, cantM3Persona)) {
            try {
                ciudad = new Ciudad(nombre, nomenclatura, Integer.parseInt(superficie), Double.parseDouble(cantM3Persona));
                res = agregarCiudad(arbol, caminos, ciudad);
                if(res) {
                    Log.mensaje("Ciudad " + ciudad.getNombre() + " agregada al sistema.")
                            .print().guardar();

                } else {
                    Log.mensaje("La ciudad " + ciudad.getNombre() + " ya existe en el sistema.")
                            .print().guardar();

                }
            } catch (NumberFormatException e) {
                Log.mensaje("Error al agregar la ciudad: " + e.getMessage())
                        .print().guardar();
            }

        }
    }

    //TODO: this
    private static boolean validarNomenclatura(String nombre, String nomenclatura) {
        boolean res = false;
        StringBuilder sb = new StringBuilder();
        String nombreMayus = nombre.toUpperCase().trim();
        int numero  = Integer.parseInt(nomenclatura.substring(2));

        if(numero > 3000 && numero < 4000 && nomenclatura.matches(REG)){
                String[] partes = nombreMayus.split("[\\\\s-]");
                if(partes.length == 2){
                    sb.append(partes[0].substring(0,1)).append(partes[1].substring(0,1));
                }else{
                    sb.append(partes[0].substring(0,2));
                }
                sb.append(numero);
                if(sb.toString().equals(nomenclatura)) {
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

    public static void eliminarCiudad(ArbolAVL arbol){
        System.out.println("Ingrese el nombre de la ciudad a eliminar:");
        String nombre = sc.nextLine().trim();
        if(nombre.isEmpty() || !nombre.matches(letras)){
            Log.mensaje("El nombre de la ciudad no puede estar vacío.")
                    .print().guardar();
        }else{
            Ciudad ciudad = new Ciudad(nombre);
            if(arbol.eliminar(ciudad)){
                Log.mensaje("Ciudad " + ciudad.getNombre() + " eliminada del sistema.")
                        .print().guardar();
            } else {
                Log.mensaje("La ciudad " + ciudad.getNombre() + " no existe en el sistema.")
                        .print().guardar();

            }
        }
    }

    public static void buscarCiudad(ArbolAVL arbol) {
        System.out.println("Ingrese el nombre de la ciudad a buscar:");
        String nombre = sc.nextLine().trim();
        if(nombre.isEmpty() || !nombre.matches(letras)){
            Log.mensaje("El nombre de la ciudad no puede estar vacío.")
                    .print().guardar();
        }else{
            Ciudad ciudad = new Ciudad(nombre);
            Ciudad res = (Ciudad) arbol.obtener(ciudad);
            if(res != null){
                Log.mensaje("Ciudad encontrada: " + res.getNombre())
                        .print().guardar();
            } else {
                Log.mensaje("La ciudad " + ciudad.getNombre() + " no existe en el sistema.")
                        .print().guardar();
            }
        }
    }

}
