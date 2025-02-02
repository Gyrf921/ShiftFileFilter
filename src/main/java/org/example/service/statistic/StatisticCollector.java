package org.example.service.statistic;

import lombok.Data;
import org.apache.commons.cli.CommandLine;
import org.example.service.FileDataCollector;

import java.util.Comparator;
import java.util.List;

@Data
public class StatisticCollector {
    private FileDataCollector fileDataCollector;
    private CommandLine cmd;

    public StatisticCollector(FileDataCollector fileDataCollector, CommandLine cmd) {
        this.fileDataCollector = fileDataCollector;
        this.cmd = cmd;
    }

    /**
     * Печатает статистику по всем собранным данным.
     * В зависимости от переданных опций командной строки выводит краткую или полную статистику.
     */
    public void printStatistics() {
        if (cmd!=null) {
            if(cmd.hasOption("s")){
                printSmallStatistics();
            }
            if (cmd.hasOption("f")) {
                printFullStatistics();
            }
        }
    }

    /**
     * Печатает краткую статистику - количество всех элементов,
     * собранных в `FileDataCollector` и записанных в файлы
     */
    private void printSmallStatistics() {
        System.out.println("The number of items written to the outgoing files is: " +
                (fileDataCollector.getIntegers().size() + fileDataCollector.getFloats().size() + fileDataCollector.getStrings().size()));
    }

    /**
     * Печатает полную статистику по всем собранным данным:
     * отдельные статистики для целых чисел, дробных и строк.
     */
    private void printFullStatistics() {
        System.out.println("\nFull statistics for integers");
        numberStatistic(fileDataCollector.getIntegers());
        System.out.println("\nFull statistics for floats");
        numberStatistic(fileDataCollector.getFloats());
        System.out.println("\nFull statistics for strings");
        stringStatistic(fileDataCollector.getStrings());
    }

    /**
     * Вычисляет и выводит статистику по числовым данным, включая:
     * минимальный элемент, максимальный элемент, количество элементов,
     * среднее значение и сумму элементов.
     * @param numbers список чисел для статистики
     * @param <T> тип элементов (должен наследовать Number)
     */
    private <T extends Number> void numberStatistic(List<T> numbers) {
        System.out.println("Min element = " + numbers.stream().min(Comparator.comparing(Number::doubleValue)).orElse(null));
        System.out.println("Max element = " + numbers.stream().max(Comparator.comparing(Number::doubleValue)).orElse(null));
        System.out.println("Count elements = " + numbers.size());

        double sum = numbers.stream().mapToDouble(Number::doubleValue).sum();
        double avg = !numbers.isEmpty() ? sum / numbers.size() : 0;

        System.out.println("Average = " + avg);
        System.out.println("Sum = " + sum);
    }

    /**
     * Вычисляет и выводит статистику по строковым данным, включая:
     * минимальный элемент и максимальный элемент (по длине строки),
     * а также количество элементов.
     *
     * @param strings список строк для статистического анализа
     */
    private void stringStatistic(List<String> strings) {
        System.out.println("Min element = " + strings.stream().min(Comparator.comparingInt(String::length)).orElse(null));
        System.out.println("Max element = " + strings.stream().max(Comparator.comparingInt(String::length)).orElse(null));
        System.out.println("Count elements = " + strings.size());
    }
}
