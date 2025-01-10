package me.yhamarsheh.moviesms.moviesms.managers;

import me.yhamarsheh.moviesms.moviesms.objects.MovieCatalog;
import me.yhamarsheh.moviesms.moviesms.storage.FileSystem;

public class PrimaryManager {

    private final MovieCatalog movieCatalog;
    private final FileSystem fileSystem;

    public PrimaryManager() {
        movieCatalog = new MovieCatalog();
        fileSystem = new FileSystem();
    }

    public MovieCatalog getMovieCatalog() {
        return movieCatalog;
    }

    public FileSystem getFileSystem() {
        return fileSystem;
    }
}
