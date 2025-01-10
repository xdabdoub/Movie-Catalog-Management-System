package me.yhamarsheh.moviesms.moviesms.utilities;

import me.yhamarsheh.moviesms.moviesms.objects.Movie;

public class FileUtils {

    public static Movie getMovieFromString(String s) {
        String[] data = s.split(";");
        System.out.println(data.length);
        if (data.length != 4) throw new IllegalArgumentException("Invalid movie string: " + s);

        for (int i = 0; i < data.length;  i++) {
            String[] subData = data[i].split(": ");
            if (subData.length != 2) throw new IllegalArgumentException("Invalid movie string: " + s);

            data[i] = subData[1];
        }

        double rating;
        int year;
        try {
            rating = Double.parseDouble(data[3]);
            year = Integer.parseInt(data[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid movie string: " + s);
        }

        String title = data[0];
        String description = data[1];

        return new Movie(title, description, year, rating);
    }
}
