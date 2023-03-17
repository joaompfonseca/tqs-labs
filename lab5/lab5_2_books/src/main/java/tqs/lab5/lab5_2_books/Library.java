package tqs.lab5.lab5_2_books;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Library {
    private final List<Book> store = new ArrayList<>();

    public void addBook(final Book book) {
        store.add(book);
    }

    public List<Book> findBooks(final LocalDate from, final LocalDate to) {
        return store.stream()
                .filter(book -> book.getPublished().isAfter(from) && book.getPublished().isBefore(to))
                .sorted(Comparator.comparing(Book::getPublished))
                .collect(Collectors.toList());
    }

    public List<Book> findBooks(final String author) {
        return store.stream()
                .filter(book -> book.getAuthor().equals(author))
                .sorted(Comparator.comparing(Book::getPublished))
                .collect(Collectors.toList());
    }
}