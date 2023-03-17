package tqs.lab5.lab5_2_books;

import java.time.LocalDate;

public class Utils {

    public static LocalDate isoTextToLocalDate(String year, String month, String day) {
        return LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    }

    public static LocalDate isoTextToLocalDate(String isoText) {
        String[] parts = isoText.split("-");
        return isoTextToLocalDate(parts[0], parts[1], parts[2]);
    }
}
