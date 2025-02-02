package org.example;

import org.apache.commons.cli.*;
import org.example.constant.OptionCMD;
import org.example.service.FileDataCollector;
import org.example.service.WriterFilesService;
import org.example.service.statistic.StatisticCollector;

import java.io.*;
import java.util.*;

public class Main {
    private static final FileDataCollector fileDataCollector = new FileDataCollector();

    public static void main(String[] args) {
        List<String> files = getTextFiles(args);

        CommandLine cmd = parseCommandLine(args);


        StatisticCollector statisticCollector = new StatisticCollector(fileDataCollector, cmd);
        processFiles(files);

        WriterFilesService writerFilesService = new WriterFilesService(cmd, fileDataCollector);
        writerFilesService.writeFiles(fileDataCollector);

        statisticCollector.printStatistics();

        System.out.println("Done");
    }


    /**
     * Фильтрует имена файлов и возвращает список текстовых файлов из аргументов командной строки.
     * @param args аргументы командной строки
     * @return список текстовых файлов
     */
    private static List<String> getTextFiles(String[] args) {
        return Arrays.stream(args)
                .filter(str -> str.endsWith(".txt"))
                .toList();
    }

    /**
     * Парсит аргументы командной строки и возвращает объект CommandLine.
     * @param args аргументы командной строки
     * @return объект CommandLine или null в случае ошибки парсинга
     */
    private static CommandLine parseCommandLine(String[] args) {
        CommandLineParser parser = new DefaultParser();
        try {
            return parser.parse(OptionCMD.options, args);
        } catch (ParseException e) {
            System.err.println("Command line parsing exception: " + e.getMessage());
            return null;
        }
    }

    /**
     * Обрабатывает список файлов, читая их содержимое.
     * @param files список файлов для обработки
     */
    private static void processFiles(List<String> files) {
        try {
            List<BufferedReader> readers = createBufferedReaders(files);
            readFileContents(readers);
        } catch (IOException e) {
            System.err.println("File reading exception: " + e.getMessage());
        }
    }

    /**
     * Читает содержимое файлов, используя заданные BufferedReader.
     * @param readers список BufferedReader для чтения файлов
     * @throws IOException если возникает ошибка ввода/вывода
     */
    private static void readFileContents(List<BufferedReader> readers) throws IOException {
        List<String> lines;
        do {
            lines = readLines(readers);
            lines.forEach(fileDataCollector::add);
        } while (lines.stream().anyMatch(Objects::nonNull));
    }

    /**
     * Создает список BufferedReader для чтения указанных файлов.
     * @param files список файлов для чтения
     * @return список BufferedReader для файлов
     */
    private static List<BufferedReader> createBufferedReaders(List<String> files){
        List<BufferedReader> readers = new ArrayList<>();
        for (String file : files){
            try {
                readers.add(new BufferedReader(new FileReader(file)));
            }
            catch (IOException e){
                System.err.println("File " + file + " could not be opened. Skipping.");
                System.err.println("Exception: " + e.getMessage());
            }
        }
        return readers;
    }

    /**
     * Читает строки из указанных BufferedReader и возвращает их в виде списка.
     * @param readers список BufferedReader для чтения строк
     * @return список строк, прочитанных из файлов
     * @throws IOException если возникает ошибка ввода/вывода
     */
    private static List<String> readLines(List<BufferedReader> readers) throws IOException {
        List<String> lines = new ArrayList<>();
        for (BufferedReader reader : readers) {
            String line = reader.readLine();
            if (line != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}