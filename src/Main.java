import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void saveGame(String directory, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(directory);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void zipFiles(String zipDirectory, List<String> filesDirectory) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipDirectory))) {
            for(String directory : filesDirectory) {
                try (FileInputStream fis = new FileInputStream(directory)) {
                    ZipEntry entry = new ZipEntry(directory.substring(directory.lastIndexOf("save")));
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        GameProgress save1 = new GameProgress(100, 2, 1, 10.0);
        GameProgress save2 = new GameProgress(90, 3, 2, 15.5);
        GameProgress save3 = new GameProgress(80, 1, 3, 20.0);
        saveGame("E:/Games/savegames/save1.dat", save1);
        saveGame("E:/Games/savegames/save2.dat", save2);
        saveGame("E:/Games/savegames/save3.dat", save3);
        List<String> list = new ArrayList<>();
        list.add("E:/Games/savegames/save1.dat");
        list.add("E:/Games/savegames/save2.dat");
        list.add("E:/Games/savegames/save3.dat");
        zipFiles("E:/Games/savegames/zip.zip", list);

        for (String directory : list) {
            File file = new File(directory);
            file.delete();
        }
    }
}
