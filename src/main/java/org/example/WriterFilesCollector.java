package org.example;

import lombok.Data;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import org.example.constant.OptionCMD;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.example.constant.OptionCMD.*;

@Data
public class WriterFilesCollector {

    private BufferedWriter integerWriter;
    private BufferedWriter floatWriter;
    private BufferedWriter stringWriter;
    private CommandLineParser parser = new DefaultParser();

    public WriterFilesCollector(String[] args) {
        try {
            CommandLine cmd = parser.parse(OptionCMD.options, args);
            String path = createFilePath(cmd);
            String prefix = createFileName(cmd);

            // Создание директории, если она не существует
            File directory = new File(path);
            if (!directory.exists() && !path.isEmpty()) {
                directory.mkdirs();
                System.out.println("Directory: " + path + ", not found. Create directory: " + directory.exists());
            }

            // Создание файлов с учетом существования файлов
            integerWriter = new BufferedWriter(new FileWriter(path + prefix + INT_FILE_NAME, cmd.hasOption("a")));
            floatWriter = new BufferedWriter(new FileWriter(path + prefix + FLOAT_FILE_NAME, cmd.hasOption("a")));
            stringWriter = new BufferedWriter(new FileWriter(path + prefix + STR_FILE_NAME, cmd.hasOption("a")));
        } catch (ParseException e) {
            System.err.println("Exception parse command: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("File access error: " + e.getMessage());
            System.err.println("The names or paths you suggested are not available, the base names are used.");
            new WriterFilesCollector();
        }
    }

    public WriterFilesCollector(){
        try {
            integerWriter = new BufferedWriter(new FileWriter(INT_FILE_NAME));
            floatWriter = new BufferedWriter(new FileWriter(FLOAT_FILE_NAME));
            stringWriter = new BufferedWriter(new FileWriter(STR_FILE_NAME));
        }catch (IOException e) {
            System.err.println("The base names are not available, there is no way to create the resulting files.");
        }
    }

    private String createFilePath(CommandLine cmd){
        return cmd.hasOption("o") ? cmd.getOptionValue("o") + "/" : "";
    }
    private String createFileName(CommandLine cmd){
        return cmd.hasOption("p") ? cmd.getOptionValue("p") : "";
    }

    @Deprecated
    public WriterFilesCollector(String fileIntegers, String fileFloats, String fileStrings) {
        try {
            integerWriter = new BufferedWriter(new FileWriter(fileIntegers));
            floatWriter = new BufferedWriter(new FileWriter(fileFloats));
            stringWriter = new BufferedWriter(new FileWriter(fileStrings));
        }catch (IOException e) {
            System.err.println("The names or paths you suggested are not available, the base names are used.");
            System.err.println(e.getMessage());
            new WriterFilesCollector();
        }
    }

    public void close() {
        try {
            integerWriter.close();
            floatWriter.close();
            stringWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
