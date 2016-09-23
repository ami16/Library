package edu.cursor.bookStorage;

import java.util.List;

/**
 * Created by Root-UA on 23.09.2016.
 */
public class Test {
    public static void main(String[] args) {
        BookManager cm = new BookManager();

        Book c1 = new Book("Socrat", "Book1", 10);
        Book c2 = new Book("Shakespeare", "Book2", 20);
        Book c3 = new Book("King", "Book3", 30);

        System.out.println("ADD Book ==============");
        Integer cId1 = cm.addBook(c1);
        Integer cId2 = cm.addBook(c2);
        Integer cId3 = cm.addBook(c3);
        List<Book> result1 = cm.findBooks();
        for(Book c : result1) {
            System.out.println(c);
        }

        System.out.println("DELETE Book ==============");
        cm.deleteBook(cId1);
        List<Book> result3 = cm.findBooks();
        for(Book c : result3) {
            System.out.println(c);
        }

        System.out.println("GET Book ==============");
        Book Book = cm.getBook(cId2);
        System.out.println(Book);
    }
}
