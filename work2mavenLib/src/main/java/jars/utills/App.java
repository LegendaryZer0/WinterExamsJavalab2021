package jars.utills;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.IParameterSplitter;
import jars.utills.ImageDownloader;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Parameters(separators = "=")
public class App {
    @Parameter(names = {"--mode"})
    private String mode;
    @Parameter(names = {"--count"})
    public Integer count;

    @Parameter(names = {"--files"}, splitter = SemiColonSplitter.class)
    private List<String> urls;
    @Parameter(names = {"--folder"})
    private String folder;
    ImageDownloader downloader;
    private ImageDownloader imageDownloader;
    ExecutorService threadPool;

    public App() {
        urls = Collections.synchronizedList(new ArrayList<String>());

    }

    public void start() {
        threadPool = Executors.newFixedThreadPool(count);
        System.out.println(urls.toString());
        Iterator it = urls.iterator();

        while (it.hasNext()) {
            Object url = it.next();
            System.out.println(url);
            threadPool.submit(() -> {
                downloader = new ImageDownloader(url.toString(), folder);
                downloader.download();
                System.out.println(Thread.currentThread().getName());
            });
        }


    }


    static class SemiColonSplitter implements IParameterSplitter {
        public List<String> split(String value) {
            return Arrays.asList(value.split(";"));
        }
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }
}
