package me.yhamarsheh.moviesms.moviesms.ui.screens.sub;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import me.yhamarsheh.moviesms.moviesms.MoviesMS;
import me.yhamarsheh.moviesms.moviesms.objects.Movie;
import me.yhamarsheh.moviesms.moviesms.structure.AVLTree;
import me.yhamarsheh.moviesms.moviesms.structure.nodes.TNode;
import me.yhamarsheh.moviesms.moviesms.ui.UIHandler;
import me.yhamarsheh.moviesms.moviesms.ui.screens.YazanScreen;
import me.yhamarsheh.moviesms.moviesms.utilities.UIUtils;

import java.util.Optional;

public class MoviesScreen extends YazanScreen {

    private AVLTree<Movie> currentMovies;
    private int currentMovieIndex;
    public MoviesScreen() {
        super("Movie Catalog Management System", "Movies Screen", false);
        this.currentMovies = MoviesMS.PRIMARY_MANAGER.getMovieCatalog().getMoviesTrees()[0];

        setCenter(setup());
    }

    @Override
    public Node setup() {
        VBox vBox = new VBox(20);

        TableView<Movie> tableView = new TableView<>();
        setupTableView(tableView);

        HBox searchBox = new HBox(10);
        TextField searchBar = new TextField();
        searchBar.setPromptText("\uD83D\uDD0D Enter search");
        searchBar.setPrefWidth(970);

        searchBar.setOnKeyTyped(e -> {
            if (searchBar.getText().isEmpty()) {
                initializeMovies(tableView);
                return;
            }

            search(tableView, searchBar.getText());
        });

        searchBox.getChildren().addAll(searchBar);
        searchBox.setAlignment(Pos.CENTER);

        VBox actions = new VBox(5);

        HBox editorButtons = new HBox(20);
        Button insert = new Button("Insert");
        insert.setPrefWidth(120);
        insert.setPrefHeight(20);

        insert.setOnAction(e -> {
            UIHandler.getInstance().open(new MovieEditorScreen(null), 800, 500);
        });

        Button update = new Button("Update");
        update.setPrefWidth(120);
        update.setPrefHeight(20);

        update.setOnAction(e -> {
            if (tableView.getSelectionModel().getSelectedItem() == null) {
                UIUtils.alert("A Movie must be selected to update!", Alert.AlertType.ERROR).show();
                return;
            }

            UIHandler.getInstance().open(new MovieEditorScreen(tableView.getSelectionModel().getSelectedItem()), 800, 500);
        });

        Button delete = new Button("Delete");
        delete.setPrefWidth(120);
        delete.setPrefHeight(20);

        delete.setOnAction(e -> {
            if (tableView.getSelectionModel().getSelectedItem() == null) {
                UIUtils.alert("A Movie must be selected to delete!", Alert.AlertType.ERROR).show();
                return;
            }

            Optional<ButtonType> confirmation = UIUtils.alert("Are you sure you'd like to PERMANENTLY delete this Movie?",
                    Alert.AlertType.CONFIRMATION).showAndWait();

            if (confirmation.isEmpty()) return;
            if (confirmation.get() == ButtonType.CANCEL) return;

            Movie movie = tableView.getSelectionModel().getSelectedItem();

            MoviesMS.PRIMARY_MANAGER.getMovieCatalog().erase(movie.getTitle());
            tableView.getItems().remove(movie);

            tableView.refresh();

            UIUtils.alert("Success!", Alert.AlertType.INFORMATION).show();
        });

        Button back = new Button("Back");
        back.setPrefHeight(20);
        back.setPrefWidth(120);

        back.setOnAction(e -> {
            UIHandler.getInstance().open(new HomeScreen(), 800, 500);
        });

        HBox otherButtons = new HBox(20);
        Button printSorted = new Button("Print Sorted");
        printSorted.setPrefWidth(120);
        printSorted.setPrefHeight(20);

        printSorted.setOnAction(e -> {
            System.out.println("Height of the Tree: " + currentMovies.height());
            currentMovies.traverseInOrder();
            System.out.println();
        });

        Button printRanked = new Button("Print Top & Least Ranked");
        printRanked.setPrefWidth(120);
        printRanked.setPrefHeight(20);

        printRanked.setOnAction(e -> {
            int i = 0;
            for (AVLTree<Movie> movies : MoviesMS.PRIMARY_MANAGER.getMovieCatalog().getMoviesTrees()) {
                if (movies == null) continue;

                System.out.println("Tree #" + i++);
                if (movies.isEmpty()) {
                    System.out.println("Tree is empty");
                    continue;
                }

                printTopAndLeastRanked(movies, movies.getRoot(), movies.getRoot(), movies.getRoot(), 0);
            }
        });

        otherButtons.getChildren().addAll(printSorted, printRanked);
        otherButtons.setAlignment(Pos.CENTER);

        HBox nextAndPrevious = new HBox(20);
        Label tree = new Label("Tree #" + currentMovieIndex);
        Button previous = new Button("Previous");
        previous.setPrefWidth(120);
        previous.setPrefHeight(20);
        previous.setOnAction(e -> {
            if (currentMovieIndex - 1 < 0) return;
            currentMovieIndex--;

            tree.setText("Tree #" + currentMovieIndex);
            currentMovies = MoviesMS.PRIMARY_MANAGER.getMovieCatalog().getMoviesTrees()[currentMovieIndex];
        });

        Button next = new Button("Next");
        next.setPrefWidth(120);
        next.setPrefHeight(20);
        next.setOnAction(e -> {
            if (currentMovieIndex + 1 > MoviesMS.PRIMARY_MANAGER.getMovieCatalog().getMoviesTrees().length - 1) return;
            currentMovieIndex++;

            tree.setText("Tree #" + currentMovieIndex);
            currentMovies = MoviesMS.PRIMARY_MANAGER.getMovieCatalog().getMoviesTrees()[currentMovieIndex];
        });

        nextAndPrevious.setAlignment(Pos.CENTER);
        nextAndPrevious.getChildren().addAll(previous, tree, next);

        HBox sortFilter = new HBox(20);
        Label filterLabel = UIUtils.label("Sort Filter:", null, 0, null);
        ComboBox<String> sortFilterCB = new ComboBox<>();
        sortFilterCB.setValue("Ascending");
        sortFilterCB.getItems().addAll("Ascending", "Descending");

        sortFilter.getChildren().addAll(filterLabel, sortFilterCB);
        sortFilter.setAlignment(Pos.CENTER);

        sortFilterCB.setOnAction(e -> {
            sort(tableView, (sortFilterCB.getValue().equalsIgnoreCase("Ascending") ? 1 : 0));
        });

        editorButtons.getChildren().addAll(insert, update, delete, back);
        editorButtons.setAlignment(Pos.CENTER);

        actions.getChildren().addAll(editorButtons, otherButtons, sortFilter, nextAndPrevious);
        actions.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(searchBox, tableView, actions);
        vBox.setAlignment(Pos.CENTER);

        return vBox;
    }

    private void setupTableView(TableView<Movie> tableView) {
        TableColumn<Movie, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));

        TableColumn<Movie, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription()));

        TableColumn<Movie, Integer> yearCol = new TableColumn<>("Release Year");
        yearCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getYear()).asObject());

        TableColumn<Movie, Double> ratingCol = new TableColumn<>("Rating");
        ratingCol.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getRating()).asObject());

        tableView.getColumns().add(titleCol);
        tableView.getColumns().add(descriptionCol);
        tableView.getColumns().add(yearCol);
        tableView.getColumns().add(ratingCol);

        initializeMovies(tableView);
    }

    private void initializeMovies(TableView<Movie> tableView) {
        tableView.getItems().clear();
        for (AVLTree<Movie> movies : MoviesMS.PRIMARY_MANAGER.getMovieCatalog().getMoviesTrees()) addMovies(tableView, movies, movies.getRoot());
    }

    private void addMovies(TableView<Movie> tableView, AVLTree<Movie> movies, TNode<Movie> node) {
        if (node != null) {
            if (node.left != null)
                addMovies(tableView, movies, node.left);
            tableView.getItems().add(node.data);
            if (node.right != null)
                addMovies(tableView, movies, node.right);
        }
    }

    private void search(TableView<Movie> tableView, String input) {

        ObservableList<Movie> temp = FXCollections.observableArrayList(tableView.getItems());
        tableView.getItems().clear();

        for (Movie movie : temp) {
            if (movie.getTitle().toLowerCase().startsWith(input) || String.valueOf(movie.getYear()).toLowerCase().startsWith(input)) {
                tableView.getItems().add(movie);
            }
        }

        tableView.refresh();
    }

    private void sort(TableView<Movie> tableView, int filter) {
        ObservableList<Movie> temp = FXCollections.observableArrayList(tableView.getItems());
        tableView.getItems().clear();

        if (filter == 0) temp.sort((movie1, movie2) -> movie2.compareTo(movie1));
        else if (filter == 1) temp.sort((movie1, movie2) -> movie1.compareTo(movie2));

        for (Movie movie : temp) {
            tableView.getItems().add(movie);
        }
    }

    private void printTopAndLeastRanked(AVLTree<Movie> movies, TNode<Movie> node, TNode<Movie> top, TNode<Movie> least, int start) {
        if (node != null) {
            if (node.left != null)
                printTopAndLeastRanked(movies, node.left, top, least, 1);

            if (node.right != null)
                printTopAndLeastRanked(movies, node.right, top, least, 1);

            if (node.data.getRating() > top.data.getRating()) top = node;
            if (node.data.getRating() < least.data.getRating()) least = node;
        }
    }

}
