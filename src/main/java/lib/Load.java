package lib;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Load {


    private final static JFileChooser fileChooser = new JFileChooser();
    private final static FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto", "txt");


    static {
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Seleccionar un archivo");

    }


    public static void main(String[] args) {
        fileChooser.showDialog(null, "Seleccionar");
    }




}
