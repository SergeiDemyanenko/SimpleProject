package org.simple;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileUtils {

    public static void writeList(String fileName, List<String> list) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileName)));
        for (String item : list) {
            writer.write(item);
            writer.write(System.lineSeparator());
        }
        writer.flush();
    }

    public static List<String> readList(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName));
    }
}
