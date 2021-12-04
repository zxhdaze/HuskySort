package edu.neu.coe.huskySort.sort.huskySort;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.*;

public class FileUtil {

    private static String path = "./";

    final static private String defaultFilename = "shuffledChinese.txt";

    /**
     * Get all strings
     * @param filename string filename
     * @return array list of name string
     */
    public static List<String> getAllNames(String filename) {
        List<String> names = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path + filename))) {
            String line;
            while ((line = br.readLine())!=null)
                names.add(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return names;
    }

    /**
     * Get all strings by default file
     * @return array list of name string
     */
    public static List<String> getAllNames() {
        return getAllNames(defaultFilename);
    }

    /**
     * Get all strings by default file
     * @return array of name string
     */
    public static String[] getAllNamesToArray() {
        return getAllNamesToArray(defaultFilename);
    }

    /**
     * Get all strings
     * @return array of name string
     */
    public static String[] getAllNamesToArray(String filename) {
        return getAllNames(filename).toArray(new String[0]);
    }

    /**
     * set work path
     * default path working path
     * @param path path string
     */
    public static void setPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException e) {
            e.printStackTrace();
        }

        FileUtil.path = path;
    }

    public static void main(String[] args) {
        // get names as list
        List<String> names = getAllNames();
        // get names as array
        String[] namesArray = getAllNamesToArray();

        System.out.println(names.size());
        System.out.println(names.get(0));
        System.out.println(names.get(names.size()-1));
        System.out.println(namesArray.length);
        System.out.println(namesArray[0]);
        System.out.println(namesArray[namesArray.length-1]);
    }
}
