package me.yhamarsheh.moviesms.moviesms.utilities;

import me.yhamarsheh.moviesms.moviesms.MoviesMS;
import me.yhamarsheh.moviesms.moviesms.objects.Movie;
import me.yhamarsheh.moviesms.moviesms.structure.AVLTree;
import me.yhamarsheh.moviesms.moviesms.structure.nodes.TNode;

public class GeneralUtils {

    public static boolean movieExists(Movie movie) {
        for (AVLTree<Movie> movies : MoviesMS.PRIMARY_MANAGER.getMovieCatalog().getMoviesTrees())
            if(movieExists(movies, movie, movies.getRoot())) return true;

        return false;
    }

    private static boolean movieExists(AVLTree<Movie> movies, Movie movie, TNode<Movie> node) {
        if (node != null) {
            if (node.left != null)
                return movieExists(movies, movie, node.left);
            if (node.data.compareTo(movie) == 0) return true;
            if (node.right != null)
                return movieExists(movies, movie, node.right);
        }

        return false;
    }

    public static Movie getMovieByTitle(String title) {
        for (AVLTree<Movie> movies : MoviesMS.PRIMARY_MANAGER.getMovieCatalog().getMoviesTrees()) {
            Movie movie = getMovieByTitle(movies, title, movies.getRoot());
            if (movie != null) return movie;
        }

        return null;
    }

    private static Movie getMovieByTitle(AVLTree<Movie> movies, String title, TNode<Movie> node) {
        if (node != null) {
            if (node.left != null)
                return getMovieByTitle(movies, title, node.left);
            if (node.data.getTitle().equalsIgnoreCase(title)) return node.data;
            if (node.right != null)
                return getMovieByTitle(movies, title, node.right);
        }

        return null;
    }
}
