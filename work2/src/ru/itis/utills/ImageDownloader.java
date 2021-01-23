package ru.itis.utills;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class ImageDownloader {
    private InputStream in;
    private String url;
    private static int count;
    private String folder;

    public ImageDownloader(String url, String folder) {
        this.url = url;
        this.folder = folder;
        count += 1;


    }

    public void download() {
        try (InputStream in = new URL(url).openStream();) {
            String mime = new URL(url).openConnection().getContentType();
            count++;
            Files.copy(in, Paths.get(new StringBuilder(folder).append("/image").append(count).append(".")
                    .append(mime.split("/")[1]).toString()).normalize().toAbsolutePath(), StandardCopyOption.REPLACE_EXISTING);



        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
