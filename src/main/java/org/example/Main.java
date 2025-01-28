package org.example;

import org.apache.commons.cli.Options;
import org.example.constrain.Regex;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    //private static FilesCollector filesCollector;

    private static WriterFilesCollector writerFilesCollector;

    private static final Options options = new Options();
    static {
        options.addOption("o", "output", true, "output file path");
        options.addOption("p", "prefix", true, "prefix in file name");
        options.addOption("a", "additional", false, "additional existing files");
        options.addOption("s", "small-statistic", false, "small-statistic");
        options.addOption("f", "full-statistic", false, "full-statistic");
    }

    public static void main(String[] args) {

        /*
        (опц) Проверка всех флагов, определение конечной директории и прочие настройки
        Взятие неопределённого кол-ва файлов (тест - 2 файла)
        Создание 3х файлов
        Одновременное чтение из всех файлов.
        Определение типа данных текущей строки (regex)
        (опц) Сбор статистики
        Сохранение строки в соответствующий файл
        Проверка пустоты файла? (удаление пустого)
        вывод
        *

        ВАЖНО:
        Не забыть описать библиотеку Apache Commons CLI

        Вопросы:
        "При запуске утилиты в командной строке подается несколько файлов" - файлов будет 2 или их количество неограниченно
       */

        writerFilesCollector = new WriterFilesCollector("answer/integers.txt","answer/floats.txt", "answer/strings.txt");

        parseFile();

        writerFilesCollector.close();


    }

    public static void addToWriter(String line) throws IOException {
        if (line != null){
            if (line.matches(Regex.INT_REGEX)) {
                writerFilesCollector.integerWriter.write(line + "\n");
            } else if (line.matches(Regex.FLOAT_REGEX)) {
                writerFilesCollector.floatWriter.write(line + "\n");
            } else {
                writerFilesCollector.stringWriter.write(line + "\n");
            }
        }
    }

    private static void parseFile() {
        try{
            BufferedReader br1 = new BufferedReader(new FileReader("in1.txt"));
            BufferedReader br2 = new BufferedReader(new FileReader("in2.txt"));

            String line1 = br1.readLine();
            String line2 = br2.readLine();
            while (line1 != null || line2 != null) {
                addToWriter(line1);
                addToWriter(line2);

                line1 = br1.readLine();
                line2 = br2.readLine();
            }

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

//Deprecated
    /*
    @Deprecated
    private static void writeFile(List<String> files) {
        try{
            for (String file : files) {
                writeFilesFromCollectors(file);
            }
        }
        catch (IOException e ){
            System.err.println("Ошибка при записи файлов: " + e.getMessage());
        }
    }

    @Deprecated
    private static List<String> choseCollector(String fileName){
        if(fileName.contains("integers")){
            return filesCollector.getIntegers();
        }
        else if(fileName.contains("floats")){
            return filesCollector.getFloats();
        }
        else{
            return filesCollector.getStrings();
        }
    }

    @Deprecated
    private static void writeFilesFromCollectors(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        for (String value : choseCollector(fileName)) {
            writer.write(value + "\n");
        }
        writer.close();
    }*/
}