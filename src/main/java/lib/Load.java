package lib;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class Load {

    private final static JFileChooser fileChooser = new JFileChooser();
    private final static FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto", "csv");

    static {
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Seleccionar un archivo");
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    }

    public static File cargarArchivo(String titulo) {
        File res = null;
        try {
            fileChooser.setDialogTitle(titulo);
            int diag = fileChooser.showOpenDialog(null);
            if (diag == JFileChooser.APPROVE_OPTION) {
                res = fileChooser.getSelectedFile();
                if (!res.getName().endsWith("csv")) {
                    System.out.println("Archivo invalido");
                    res = null;
                } else {
                    System.out.println(" Archivo seleccionado " + res.getName());
                }
            } else {
                System.out.println("No se selecciono ningun archivo");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Error: ?");
        }

        return res;
    }

}
