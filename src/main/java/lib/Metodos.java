package lib;

import entidades.Ciudad;
import estructuras.conjuntistas.dinamicas.ArbolAVL;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.stream.Stream;

public class Metodos {

    private static final String REG = "^[A-Z]{2}\\d{4}$";
    private static final Scanner sc = new Scanner(System.in);

    // TODO: agregar validaciones del archivo.
    public static void cargarCiudadesDesde(ArbolAVL arbol) throws IOException {

        System.out.println("Cargando ciudades...");
        File archivo = Load.cargarArchivo();
        if(archivo != null){
            Log.mensaje("Cargando ciudades desde: " + archivo.getAbsolutePath()).guardar();

            // Pone el archivo en una lista(stream) para luego poder recorrerla eficientemente, separando cada linea por ";"
            // y luego se valida que tenga la cantidad de campos necesarios para crear una ciudad.
            // Si es válido lo inserta en el árbol, si no, se guarda un mensaje de error en el log.
            try(Stream<String> lineas = Files.lines(archivo.toPath())){
                lineas.skip(1)
                        .map(l -> l.split(";"))
                        .forEach(l -> {
                            if(l.length == 5){
                                arbol.insertar(
                                        new Ciudad(
                                                l[0].trim(),
                                                l[1].trim(),
                                                Integer.parseInt(l[2].trim()),
                                                Double.parseDouble(l[3].trim())
                                        )
                                );
                            }else {
                                Log.mensaje("Error al cargar la ciudad: " + l[0] + ". Formato incorrecto.").guardar();
                            }
                        });
            }catch(NumberFormatException e){
                Log.mensaje("Error al cargar las ciudades: " + e.getMessage()).guardar();
                System.out.println("Error al cargar las ciudades: " + e.getMessage());

            }
        }

    }


    //TODO: Agregar validaciones, comprobar que no exista una ciudad con el mismo nombre o nomenclatura.
    public static boolean agregarCiudad(ArbolAVL arbol){
        boolean res = false;
        Ciudad ciudad = null;
        System.out.println("Ingresar nombre de la ciudad:");
        String nombre = sc.nextLine();
        System.out.println("Ingresar nomenclatura de la ciudad (Formato: AA0000):");
        String nomenclatura = sc.nextLine();
        System.out.println("Ingresar superficie de la ciudad (en m2):");
        int superficie = Integer.parseInt(sc.nextLine());
        System.out.println("Ingresar cantidad de m3 por persona:");
        double cantM3Persona = Double.parseDouble(sc.nextLine());
        if(nomenclatura.matches(REG)){
            try {
                ciudad = new Ciudad(nombre, nomenclatura, superficie, cantM3Persona);
                arbol.insertar(ciudad);
                res = true;
                Log.mensaje("Ciudad agregada: " + ciudad.getNombre()).guardar();
            }catch (NumberFormatException e){
                Log.mensaje("Error al agregar la ciudad: " + e.getMessage()).guardar();
                System.out.println("Error al agregar la ciudad: " + e.getMessage());
            }
        }

        return res;

    }

}
