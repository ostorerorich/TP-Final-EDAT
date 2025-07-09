package lib;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public void guardar() throws IOException {
        crearDir();

        String hora = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        String log = String.format("[%s] %s", hora, mes);

        if (writer ==null){
            iniWriter();
        }
        writer.println(log);
        writer.flush();

    }

    private static void iniWriter() throws IOException{
        verificarLogFile();

            writer = new PrintWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(LOG_DIR + File.separator + LOG_FILE, true), // Modo append
                            StandardCharsets.UTF_8
                    ),
                    true
            );

    }

    private static void crearDir() throws IOException {
        File dir = new File(LOG_DIR);
        if (!dir.exists() && !dir.mkdirs()) {

                System.out.println("No se pudo crear el directorio");
        }

        File logFile = new File(LOG_DIR + File.separator + LOG_FILE);
        if(!logFile.exists() && !logFile.createNewFile() && !logFile.canWrite()){
            throw new IOException("No se pudo crear.");
        }
    }

    private static void verificarLogFile() throws IOException{
        File log = new File(LOG_DIR + File.separator + LOG_FILE);

        if(!log.exists()){
            throw new IOException("El archivo no existe");
        }

        if(!log.canWrite()){
            throw new IOException("No se puede escribir el archivo");
        }
    }

    public static void cerrar(){
        if(writer != null){
            writer.close();
            writer=null;
        }
    }


}
