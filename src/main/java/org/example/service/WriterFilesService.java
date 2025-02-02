package org.example.service;

import lombok.Data;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.example.constant.OptionCMD.*;

@Data
public class WriterFilesService {

    private BufferedWriter integerWriter;
    private BufferedWriter floatWriter;
    private BufferedWriter stringWriter;

    private CommandLineParser parser = new DefaultParser();

    /**
     * Конструктор, который создает экземпляр WriterFilesService.
     * Инициализирует директории и BufferedWriter на основе командной строки.
     * @param cmd объект командной строки, содержащий опции
     */
    public WriterFilesService(CommandLine cmd, FileDataCollector fileDataCollector) {
        if (cmd != null) {
            createDirectory(cmd);
            initializeWriters(fileDataCollector, createFilePath(cmd) + createFileName(cmd), cmd.hasOption("a"));
        } else {
            initializeWriters(fileDataCollector, "", false);
        }
    }

    /**
     * Инициализирует BufferedWriter для целых чисел, дробных чисел и строк,
     * устанавливая, будет ли файл открыт в режиме добавления.
     * @param fileDataCollector класс со считанными данными из переданных файлов.
     * @param subPath путь к файлу
     * @param appendFile флаг, указывающий, открывать ли файл в режиме добавления
     */
    private void initializeWriters(FileDataCollector fileDataCollector, String subPath, boolean appendFile) {
        if (!fileDataCollector.getIntegers().isEmpty()){
            integerWriter = setBufferWriter(subPath + INT_FILE_NAME, appendFile);
        }
        if (!fileDataCollector.getFloats().isEmpty()){
            floatWriter = setBufferWriter(subPath + FLOAT_FILE_NAME, appendFile);
        }
        if (!fileDataCollector.getStrings().isEmpty()){
            stringWriter = setBufferWriter(subPath + STR_FILE_NAME, appendFile);
        }
    }

    /**
     * Создает BufferedWriter для указанного пути и режима добавления.
     * @param path путь к файлу
     * @param appendFile флаг, указывающий, открывать ли файл в режиме добавления
     * @return BufferedWriter или null в случае ошибки
     */
    private BufferedWriter setBufferWriter(String path, boolean appendFile) {
        try {
            return new BufferedWriter(new FileWriter(path, appendFile));
        } catch (IOException ex) {
            System.err.println("Failed to open file: " + path);
            System.err.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Создает директорию для файлов, если она не существует.
     * @param cmd объект командной строки, используемый для извлечения пути
     */
    private void createDirectory(CommandLine cmd) {
        String path = createFilePath(cmd);
        File directory = new File(path);
        if (!directory.exists() && !path.isEmpty()) {
            directory.mkdirs();
            System.out.println("Directory: " + path + ", created: " + directory.exists());
        }
    }

    /**
     * Возвращает путь к файлу на основе опций командной строки.
     * @param cmd объект командной строки
     * @return строка с путем к файлу
     */
    private String createFilePath(CommandLine cmd){
        return cmd.hasOption("o") ? cmd.getOptionValue("o") + "/" : "";
    }

    /**
     * Возвращает префикс файла на основе опций командной строки.
     * @param cmd объект командной строки
     * @return строка с именем файла
     */
    private String createFileName(CommandLine cmd){
        return cmd.hasOption("p") ? cmd.getOptionValue("p") : "";
    }

    /**
     * Записывает данные из FileDataCollector в соответствующие файлы.
     * @param fileDataCollector объект, содержащий данные для записи
     */
    public void writeFiles(FileDataCollector fileDataCollector) {
        try {
            if (!fileDataCollector.getIntegers().isEmpty() && integerWriter != null){
                writeData(fileDataCollector.getIntegers(), integerWriter);
            }
            if (!fileDataCollector.getFloats().isEmpty() && floatWriter != null){
                writeData(fileDataCollector.getFloats(), floatWriter);
            }
            if (!fileDataCollector.getStrings().isEmpty() && stringWriter != null){
                writeData(fileDataCollector.getStrings(), stringWriter);
            }
        } catch (IOException e) {
            System.err.println("Failed to write file, message: " + e.getMessage());
        } finally {
            closeWriters();
        }
    }

    /**
     * Закрывает все BufferedWriter, если они не равны null.
     */
    private void closeWriters() {
        closeWriter(integerWriter);
        closeWriter(floatWriter);
        closeWriter(stringWriter);
    }

    /**
     * Закрывает указанный BufferedWriter.
     * @param writer BufferedWriter, который нужно закрыть
     */
    private void closeWriter(BufferedWriter writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                System.err.println("Exception when closing a file: " + e.getMessage());
            }
        }
    }

    /**
     * Записывает данные в файл с использованием заданного BufferedWriter.
     * @param data коллекция данных для записи
     * @param writer BufferedWriter, который используется для записи данных
     * @param <T> тип элементов данных
     * @throws IOException если возникает ошибка ввода/вывода
     */
    private <T> void writeData(Iterable<T> data, BufferedWriter writer) throws IOException {
        if (writer != null) {
            for (T value : data) {
                writer.write(value.toString() + "\n");
            }
        }
    }
}
