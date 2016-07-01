package main;

import java.io.File;

/**
 * Created by gbsoj on 7/1/2016.
 */
public class Utils {
    public void listFilesForFolder(File folder) {
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                try {
                    System.out.println(fileEntry.getName());
                }
                catch (Exception e) {

                }
            }
        }
    }
}
