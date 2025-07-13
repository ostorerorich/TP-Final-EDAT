package lib;

import entidades.Ciudad;
import entidades.TuberiaKey;
import estructuras.conjuntistas.dinamicas.ArbolAVL;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Stream;

public class MetodosTuberia {
    //private static final Scanner sc = new Scanner(System.in);

    public static void cargarTuberiasDesde(ArbolAVL arbol, HashMap<TuberiaKey, Integer> tuberias) throws IOException {

        if(arbol.esVacio()){
            Log
                    .mensaje("No hay ciudades cargadas. Cargue ciudades primero.")
                    .print()
                    .guardar();
        }else{
            File archivo = Load.cargarArchivo();
            if(archivo!=null){
                Log.mensaje("Cargando tuberias desde: " + archivo.getAbsolutePath()).print().guardar();

                try(Stream<String> lineas = Files.lines(archivo.toPath())){
                    lineas.skip(1)
                            .map(l -> l.split(";"))
                            .forEach(l ->{
                                if(l.length == 3){
                                    Ciudad origen = new Ciudad(l[0].trim());
                                    Ciudad destino = new Ciudad(l[1].trim());
                                    int metros = Integer.parseInt(l[2].trim());

                                    if(!arbol.pertenece(origen) || !arbol.pertenece(destino)){
                                        Log.mensaje("Error al cargar la tubería: Ciudad no encontrada.").print().guardar();
                                    }else {
                                        TuberiaKey key = new TuberiaKey((Ciudad) arbol.obtener(origen), (Ciudad) arbol.obtener(destino));
                                        if(tuberias.containsKey(key)){
                                            Log.mensaje("Error al cargar la tubería: Ya existe una tubería entre estas ciudades.").print().guardar();
                                        }else{
                                            tuberias.put(key, metros);
                                            Log.mensaje("Tubería cargada: " + origen + " - " + destino + " (" + metros + " metros)").print().guardar();
                                        }
                                    }
                                }else{
                                    Log.mensaje("Error al cargar la tubería: Formato incorrecto.").print().guardar();
                                }
                            });

                    }
                }
            }
        }
    }

