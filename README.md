### Информация о проекте

#### Версия Java

- Java 17

#### Система сборки

- Maven 3.9.9

#### Сторонние библиотеки

- Apache Commons CLI 1.4
    - Ссылка на библиотеку (https://commons.apache.org/proper/commons-cli/)
- Project Lombok
  - Ссылка на библиотеку (https://projectlombok.org/setup/maven)

Для использования данных библиотек добавлены зависимости в файл pom.xml.

    <dependencies>
        <dependency>
            <groupId>commons-cli</groupId>
            <artifactId>commons-cli</artifactId>
            <version>1.9.0</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.36</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
---

### Запуск проекта

- Выполнить команду "mvn clean package"
- Найти файл с названием [ShiftFileFilter-1.0-SNAPSHOT-jar-with-dependencies.jar](target/ShiftFileFilter-1.0-SNAPSHOT-jar-with-dependencies.jar). Он будет в папке target/
- При желании перенести jar файл в нужную директорию или просто запустить его из консоли командой.

Пример команды для запуска :
`` java -jar ShiftFileFilter-1.0-SNAPSHOT-jar-with-dependencies.jar -a -s -f -o answers -p test- in1.txt in2.txt
``

Опции для настройки запуска приложения:
- -o путь куда нужно сохранить готовые файлы (указать значение после опции) *
- -p префикс для названия сохранённых файлов (указать значение после опции)
- -a флаг которые показывает, дописывать файл или перезаписывать в случае, если они уже существуют.
- -s выводит краткую статистику по обработке файлов
- -f выводит полную статистику по обработке файлов