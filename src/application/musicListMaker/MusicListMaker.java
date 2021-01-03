package application.musicListMaker;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

/**
 * 1. Зайти на свою страницу ВК с треклистами.
 * 2. Пролистать всю страницу до конца.
 * 3. Нажать ctrl + A.
 * 4. Скопировать весь выделенный текст.
 * 5. Вставить выделенный текст в заранее заготовленный файл.
 * 6. Удалить из текста все, что идет перед началом списка песен.
 * 7. Проставить после последнего трека строку "Конец файла".
 * 8. Сохранить файл.
 * 9. Запустить приложение.
 * 10. Следовать инструкциям в консоли.
 *
 * -----------------------------------------------------------------
 *
 * 1. Go to your VK page with tracklists.
 * 2. Scroll the entire page to the end.
 * 3. Press ctrl + A.
 * 4. Copy all selected text.
 * 5. Insert the selected text into a previously prepared file.
 * 6. Remove from the text anything that comes before the beginning of the song list.
 * 7. Put the line "Конец файла" after the last track.
 * 8. Save the file.
 * 9. Run the application.
 * 10. Follow the instructions in the console.
 */

public class MusicListMaker {

    public static void main(String[] args) {
        startApplication();
    }

    private static void startApplication() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите путь к начальному файлу");
        String INPUT_FILE_PATH = sc.next();
        System.out.println("Введите путь к куда Вы хотите сохранить отредактированный список треков");
        String OUTPUT_FILE_PATH = sc.next();
        makeFileWithTrackList(INPUT_FILE_PATH, OUTPUT_FILE_PATH);
        System.out.print("Формирование списка завершено.\nФайл находится в " + OUTPUT_FILE_PATH);
    }

    private static void makeFileWithTrackList(String readFrom, String writeTo) {
        File outputFile = new File(writeTo);
        try {
            FileInputStream fstream = new FileInputStream(readFrom);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            int counter = 0;
            while (!(strLine = br.readLine()).equals("Конец файла")) {
                if (strLine.isEmpty()) {
                    continue;
                }
                if (!strLine.contains(":") && counter != 2) {
                    counter++;
                    writeRowToFile(strLine, outputFile, counter);
                } else {
                    if (counter == 2) {
                        counter = 0;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void writeRowToFile(String row, File file, int counter) throws IOException {
        StringBuilder finalRow = new StringBuilder(row);
        if (counter != 2) {
            finalRow.append(" --- ");
        }
        if (counter == 2) {
            finalRow.append("\n");
        }
        Path fileName = Path.of(file.toString());
        if (file.exists()) {
            Files.writeString(fileName, finalRow, StandardOpenOption.APPEND);
        } else {
            Files.createFile(file.toPath());
            Files.writeString(fileName, finalRow);
        }
    }
}
