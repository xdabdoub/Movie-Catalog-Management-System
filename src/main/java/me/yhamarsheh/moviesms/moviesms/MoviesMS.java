package me.yhamarsheh.moviesms.moviesms;

import me.yhamarsheh.moviesms.moviesms.managers.PrimaryManager;
import me.yhamarsheh.moviesms.moviesms.ui.UIHandler;

public class MoviesMS {

    public static final PrimaryManager PRIMARY_MANAGER = new PrimaryManager();

    public static void main(String[] args) {
        UIHandler.launchApp(args);
    }
}
