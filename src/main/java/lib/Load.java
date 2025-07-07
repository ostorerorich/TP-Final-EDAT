package lib;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Load {


    private final static JFileChooser fileChooser = new JFileChooser();
    private final static FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto", "csv");


    static {
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Seleccionar un archivo");
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    }

    public static void main(String[] args) throws IOException {
/*        File asd = cargarArchivos();
        String[] asd2 = Files.readAllLines(asd.toPath()).getFirst().split(";");

        System.out.println(asd2[1]);*/
    }


    public static File cargarArchivos(){
        File res = null;
        try{
            int diag = fileChooser.showOpenDialog(null);
            if(diag == JFileChooser.APPROVE_OPTION){
                res = fileChooser.getSelectedFile();
                if(!res.getName().endsWith("csv")){
                    System.out.println("Archivo invalido");
                    res = null;
                }else{
                    System.out.println(" Archivo seleccionado "+ res.getName());
                }
            }else{
                System.out.println("No se selecciono ningun archivo");
            }
        }catch (RuntimeException e){
            throw new RuntimeException("Error: ?");
        }


        return res;
    }





}
