package lib;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


// Clase log para guardar los mensajes en un archivo.
public class Log {

    private String mes;
    private static final String LOG_DIR = System.getProperty("user.home") + File.separator  + "Documents" + File.separator + "logs_edat";
    private static final String LOG_FILE = "edat.log";
    private static PrintWriter writer;

    private Log(String mes){
        this.mes = mes;
    }

    public static Log mensaje(String mes){
        return new Log(mes);
    }


    // Guarda el mensaje en el log, si no se pudo guardar, imprime un error en consola.
    // Se utiliza aparte de guardarAux para manejar exepciones de IO y evitar cortar el flujo del programa.
    public void guardar() {
        try {
            guardarAux();
        } catch (IOException e) {
            System.err.println("Error al guardar log: " + e.getMessage());
        }
    }

    public void guardarAux() throws IOException {


            crearDir();

            // Obtiene la fecha y hora actual en formato ISO_DATE_TIME.
            String hora = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
            String log = String.format("[%s] %s", hora, mes);

            // Si el PrintWriter no está inicializado, lo inicializa.
            if (writer ==null){
                iniWriter();
            }
            writer.println(log);
            writer.flush();


    }

    // Inicializa el PrintWriter para escribir en el archivo de log.
    private static void iniWriter() throws IOException{
        // Verifica que el directorio y el archivo exista, en caso contrario se los crea.

        verificarLogFile();

        // Crea el PrintWriter para escribir en el archivo de log, en modo append.
        // Utiliza UTF-8 para la codificación de caracteres.
        // El segundo parámetro de PrintWriter es true para habilitar el modo append.
        // Esto significa que los nuevos mensajes se agregarán al final del archivo en lugar de sobrescribirlo.
            writer = new PrintWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(LOG_DIR + File.separator + LOG_FILE, true), // Modo append
                            StandardCharsets.UTF_8
                    ),
                    true
            );

    }

    // Crea el directorio y el archivo de log si no existen.
    // Si no se puede crear, lanza una IOException.
    private static void crearDir() throws IOException {
        File dir = new File(LOG_DIR);
        if (!dir.exists() && !dir.mkdirs()) {

                System.out.println("No se pudo crear el directorio");
        }

        File logFile = new File(LOG_DIR + File.separator + LOG_FILE);
        if(!logFile.exists() && !logFile.createNewFile() && !logFile.canWrite()){
            System.out.println("PASO ALGO");

            throw new IOException("No se pudo crear.");
        }
    }

    // Verifica que el archivo de log exista y sea escribible.
    private static void verificarLogFile() throws IOException{
        File log = new File(LOG_DIR + File.separator + LOG_FILE);

        if(!log.exists()){
            throw new IOException("El archivo no existe");
        }

        if(!log.canWrite()){
            throw new IOException("No se puede escribir el archivo");
        }
    }

    // Cierra el PrintWriter si está abierto.
    public static void cerrar(){
        if(writer != null){
            writer.close();
            writer=null;
        }
    }


}
