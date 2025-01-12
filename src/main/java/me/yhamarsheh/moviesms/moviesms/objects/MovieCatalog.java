package me.yhamarsheh.moviesms.moviesms.objects;

import me.yhamarsheh.moviesms.moviesms.MoviesMS;
import me.yhamarsheh.moviesms.moviesms.structure.AVLTree;
import me.yhamarsheh.moviesms.moviesms.structure.SHash;
import me.yhamarsheh.moviesms.moviesms.structure.nodes.TNode;

import java.io.File;
import java.io.FileNotFoundException;

public class MovieCatalog {

    private SHash<Movie> movies;
    public MovieCatalog() {
        allocate();
    }

    private void allocate() {
        this.movies = new SHash<>(10);
    }

    public void put(Movie movie) {
        movies.insert(movie);
    }

    public Movie get(String title) {
        int index = hashFunction(title);
        AVLTree<Movie> tree = movies.getTreeOfItem(index);

        return getMovieByTitle(tree, tree.getRoot(), title);
    }

    public void erase(String title) {
        Movie movie = get(title);
        movies.delete(movie);
    }

    public void saveMoviesToFile(File file) throws FileNotFoundException {
        MoviesMS.PRIMARY_MANAGER.getFileSystem().update();
    }

    public void loadMoviesFromFile(File file) {
        MoviesMS.PRIMARY_MANAGER.getFileSystem().setMoviesFile(file);
        MoviesMS.PRIMARY_MANAGER.getFileSystem().readMoviesFile();
    }

    public int hashFunction(String title) {
        final int prime = 31;
        int result = 1;

        if (title != null) {
            for (int i = 0; i < title.length(); i++) {
                result = prime * result + title.charAt(i);
            }
        }

        return result;
    }

    public void deallocate(String title) {
        for (int i = 0; i < movies.getHashTable().length; i++) {
            movies.getHashTable()[i] = new AVLTree<>();
        }
    }

    private Movie getMovieByTitle(AVLTree<Movie> tree, TNode<Movie> node, String title) {
        if (node != null) {
            if (node.left != null)
                getMovieByTitle(tree, node.left, title);
            if (node.data.getTitle().equalsIgnoreCase(title)) return node.data;
            if (node.right != null)
                getMovieByTitle(tree, node.right, title);
        }

        return null;
    }

    public AVLTree<Movie>[] getMoviesTrees() {
        return movies.getHashTable();
    }
}
