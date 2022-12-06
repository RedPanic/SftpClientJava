package pl.szetalpo.utils.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DirectoryHandler {
    public static List<String> getFiles(String path){
        try {
            Stream<Path> files = Files.list(Path.of(path));
            return files.map((file) -> file.getFileName().toString()).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
