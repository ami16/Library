package edu.cursor.bookStorage;

/**
 * Created by Root-UA on 23.09.2016.
 */
public class BookDAOFactory {
    public static BookDAO getBookDAO() {
        return new BookSimpleDAO();
    }
}
