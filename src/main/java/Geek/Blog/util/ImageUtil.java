package Geek.Blog.util;

import java.io.File;
        import java.io.FileInputStream;
        import java.io.IOException;

public class ImageUtil {

    public static byte[] convertImageToByteArray(String filePath) {
        File file = new File(filePath);
        byte[] imageData = new byte[(int) file.length()];
        try (FileInputStream imageInFile = new FileInputStream(file)) {
            int readBytes = imageInFile.read(imageData);
            if (readBytes != file.length()) {
                throw new IOException("File read error");
            }
        } catch (IOException e) {
            System.out.println("ImageUtil Error: " + e.getMessage());
        }
        return imageData;
    }

}
