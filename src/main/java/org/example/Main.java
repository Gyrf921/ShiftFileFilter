package org.example;

import org.apache.commons.cli.*;
import org.example.constant.Regex;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Main {

    private static WriterFilesCollector writerFilesCollector;


    public static void main(String[] args) throws ParseException {

        /*
        (опц) Проверка всех флагов, определение конечной директории и прочие настройки
        Взятие неопределённого кол-ва файлов (тест - 2 файла)
        Создание 3х файлов
        Одновременное чтение из всех файлов.
        Определение типа данных текущей строки (regex)
        (опц) Сбор статистики
            -o нужно уметь задавать путь для результатов.
            Опция -p задает префикс имен выходных файлов.
            Например -o /some/path -p result_ задают вывод в файлы /some/path/result_integers.txt, /some/path/result_strings.txt и тд.
            С помощью опции -a можно задать режим добавления в существующие файлы.

В процессе фильтрации данных необходимо собирать статистику по каждому типу данных.
Статистика должна поддерживаться двух видов: краткая и полная. Выбор статистики
производится опциями -s и -f соответственно. Краткая статистика содержит только
количество элементов записанных в исходящие файлы. Полная статистика для чисел
дополнительно содержит минимальное и максимальное значения, сумма и среднее.
Полная статистика для строк, помимо их количества, содержит также размер самой
короткой строки и самой длинной.
        ВАЖНО:
        Не забыть описать библиотеку Apache Commons CLI
       */
        List<String> files = Arrays.stream(args).filter(str -> str.contains(".txt")).toList();

        writerFilesCollector = new WriterFilesCollector(args);

        parseFile(files);

        writerFilesCollector.close();

        System.out.println("Done");
    }

    public static void addToWriter(String line) throws IOException {
        if (line != null){
            if (line.matches(Regex.INT_REGEX)) {
                writerFilesCollector.getIntegerWriter().write(line + "\n");
            } else if (line.matches(Regex.FLOAT_REGEX)) {
                writerFilesCollector.getFloatWriter().write(line + "\n");
            } else {
                writerFilesCollector.getStringWriter().write(line + "\n");
            }
        }
    }

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

    private static List<String> readLines(List<BufferedReader> brs) throws IOException {
        List<String> lines = new ArrayList<>();
        for (BufferedReader br : brs) {
            lines.add(br.readLine());
        }
        return lines;
    }

    private static void parseFile(List<String> files) {
        try{
            List<BufferedReader> brs = createBufferedReaders(files);

            List<String> lines = readLines(brs);
            while (lines.stream().anyMatch(Objects::nonNull)) {
                lines.forEach(line -> {
                    try {
                        addToWriter(line);
                    } catch (IOException e) {
                        System.err.println("Exception when trying to write to a file:" + e.getMessage());
                    }
                });

                lines = readLines(brs);
            }

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}