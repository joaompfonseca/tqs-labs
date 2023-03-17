package tqs.lab5.lab5_2_books;

import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BookSearchSteps {

    Library library = new Library();
    List<Book> result = new ArrayList<>();

    @ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
    public LocalDate iso8601Date(String year, String month, String day) {
        return Utils.isoTextToLocalDate(year, month, day);
    }

    @DataTableType
    public Book bookEntry(Map<String, String> entry) {
        return new Book(
                entry.get("title"),
                entry.get("author"),
                Utils.isoTextToLocalDate(entry.get("published")));
    }

    @Given("the library has the following books:")
    public void addBooks(List<Book> books) {
        books.forEach(library::addBook);
    }

    @Given("a/another book with the title {string}, written by {string}, published in {iso8601Date}")
    public void addNewBook(final String title, final String author, final LocalDate published) {
        Book book = new Book(title, author, published);
        library.addBook(book);
    }

    @When("the customer searches for books published between {iso8601Date} and {iso8601Date}")
    public void setSearchParameters(final LocalDate from, final LocalDate to) {
        result = library.findBooks(from, to);
    }

    @When("the customer searches for books authored by {string}")
    public void setSearchParameters(final String author) {
        result = library.findBooks(author);
    }

    @Then("{int} book/books should have been found")
    public void verifyAmountOfBooksFound(final int booksFound) {
        assertThat(result.size(), equalTo(booksFound));
    }

    @Then("Book {int} should have the title {string}")
    public void verifyBookAtPosition(final int position, final String title) {
        assertThat(result.get(position - 1).getTitle(), equalTo(title));
    }
}
