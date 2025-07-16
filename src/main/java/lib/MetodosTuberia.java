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

import java.util.stream.Stream;

public class MetodosTuberia {

    public static void cargarTuberiasDesde(ArbolAVL arbol, HashMap<TuberiaKey, Tuberia> tuberias) throws IOException {

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
                                    Ciudad origen = (Ciudad) arbol.obtener(new Ciudad(l[0].trim()));
                                    Ciudad destino = (Ciudad) arbol.obtener(new Ciudad(l[1].trim()));
                                    int diametro = Integer.parseInt(l[2].trim());
                                    int caudalMax = 0;
                                    int caudalMin = 0;


                                    if(origen == null || destino == null){
                                        Log.mensaje("Error al cargar la tubería: Ciudad no encontrada.").print().guardar();
                                    }else {
                                        TuberiaKey key = new TuberiaKey(origen.getNomenclatura(), destino.getNomenclatura());
                                        if(tuberias.containsKey(key)){
                                            Log.mensaje("Error al cargar la tubería: Ya existe una tubería entre estas ciudades.").print().guardar();
                                        }else{
                                            Tuberia tuberia = new Tuberia(origen.getNomenclatura(), destino.getNomenclatura(), diametro, caudalMax, caudalMin, Estado.ACTIVO);
                                            tuberias.put(key, tuberia);
                                            Log.mensaje("Tubería cargada: " + tuberia).print().guardar();
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

