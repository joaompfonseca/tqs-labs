package tqs.lab5.lab5_2_books;

import java.time.LocalDate;

public class Book {
    private final String title;
    private final String author;
    private final LocalDate published;

    public Book(String title, String author, LocalDate published) {
        this.title = title;
        this.author = author;
        this.published = published;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDate getPublished() {
        return published;
    }
}
