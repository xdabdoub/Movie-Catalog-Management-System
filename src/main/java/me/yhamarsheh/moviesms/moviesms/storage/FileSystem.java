package me.yhamarsheh.moviesms.moviesms.storage;

import me.yhamarsheh.moviesms.moviesms.MoviesMS;
import me.yhamarsheh.moviesms.moviesms.objects.Movie;
import me.yhamarsheh.moviesms.moviesms.structure.AVLTree;
import me.yhamarsheh.moviesms.moviesms.structure.nodes.TNode;
import me.yhamarsheh.moviesms.moviesms.utilities.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileSystem {

    private File moviesFile;

    public FileSystem() {

    }

    public void readMoviesFile() {
        if (moviesFile == null) return;
        try (Scanner scanner = new Scanner(moviesFile)) {
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isEmpty()) {
                    Movie movie;
                    try {
                        movie = FileUtils.getMovieFromString(sb.toString());
                    } catch (IllegalArgumentException ex) {
                        System.out.println(ex.getMessage());
                        continue;
                    }

                    MoviesMS.PRIMARY_MANAGER.getMovieCatalog().put(movie);
                    sb.setLength(0);
                } else {
                    sb.append(line);
                    if (scanner.hasNext()) sb.append(";");
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find flights file.");
        }
    }

    public void update() throws FileNotFoundException {
        saveMovies();
    }

    public void saveMovies() throws FileNotFoundException {
        if (moviesFile == null) return;

        try (PrintWriter writer = new PrintWriter(moviesFile)) {
            for (AVLTree<Movie> movies : MoviesMS.PRIMARY_MANAGER.getMovieCatalog().getMoviesTrees()) printMovies(writer, movies, movies.getRoot());
        }
    }

    private void printMovies(PrintWriter writer, AVLTree<Movie> movies, TNode<Movie> node) {
        if (node != null) {
            if (node.left != null)
                printMovies(writer, movies, node.left);
            printMovie(writer, node.data);
            if (node.right != null)
                printMovies(writer, movies, node.right);
        }
    }

    private void printMovie(PrintWriter writer, Movie movie) {
        writer.println("Title: " + movie.getTitle());
        writer.println("Description: " + movie.getDescription());
        writer.println("Release Year: " + movie.getYear());
        writer.println("Rating: " + movie.getRating());
        writer.println();
    }

    public File getMoviesFile() {
        return moviesFile;
    }

    public void setMoviesFile(File moviesFile) {
        this.moviesFile = moviesFile;
    }
}