package me.yhamarsheh.moviesms.moviesms.objects;

public class Movie implements Comparable<Movie> {

    private String title;
    private String description;
    private int year;
    private double rating;
    public Movie(String title, String description, int year, double rating) {
        this.title = title;
        this.description = description;
        this.year = year;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Movie [title=" + title + ", description=" + description + ", year=" + year + ", rating=" + rating + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        if (title != null) {
            for (int i = 0; i < title.length(); i++) {
                result = prime * result + title.charAt(i);
            }
        }

        return result;
    }

    @Override
    public int compareTo(Movie o) {
        return getTitle().compareTo(o.getTitle());
    }
}
