package edu.cursor.library.book.utils;


import edu.cursor.library.book.service.BookServiceImpl;

import java.util.Scanner;

public class BookMenuUtils {
    private static final Scanner scan = new Scanner(System.in);
    BookServiceImpl service = new BookServiceImpl();

    public void bookMenuAdmin() {
        System.out.println("--- BOOK MENU ---");
        System.out.println("1. Add new book");
        System.out.println("2. Add exist book");
        System.out.println("3. Take book");
        System.out.println("4. Remove book");
        System.out.println("5. View library books");
        System.out.println("6. Back to main menu");
        switch (Character.toLowerCase(scan.next().charAt(0))) {
            case '1': {
                System.out.println("Pls enter ISBN for new book.");
                Integer ISBNnew = scan.nextInt();
                String left = scan.nextLine();
                System.out.println("Pls enter Author for new book.");
                String authorNew = scan.nextLine();
                System.out.println("Pls enter Title for new book.");
                String titleNew = scan.nextLine();
                System.out.println("Pls enter Published Year(YYYY-MM-DD) for new book.");
                String publYearNew = scan.nextLine();
                System.out.println("Pls enter Written Year (YYYY-MM-DD) for new book.");
                String writYearNew = scan.nextLine();
                System.out.println("Pls enter genre for new book.");
                String genreNew = scan.nextLine();
                service.addBookNew(ISBNnew, authorNew, titleNew, publYearNew, writYearNew, genreNew);
                break;
            }
            case '2': {
                System.out.println("Select book`s ISBN for add: ");
                service.addBookOld(scan.nextInt());
                break;
            }
            case '3': {
                System.out.println("Select book ISBN: ");
                service.replaceBook(scan.nextInt());
                break;
            }
            case '4': {
                System.out.println("Select book's ISBN for removing: ");
                service.removeBook(scan.nextInt());
                break;
            }
            case '5': {
                service.viewBookList();
                break;
            }
            case '6': {
                return;
            }
            default:
                System.out.println("Select correct item (1-6)");
                bookMenuAdmin();
        }
    }

    public void bookMenuUser() {
        System.out.println("--- BOOK MENU ---");
        System.out.println("1. View library books");
        System.out.println("2. Take book");
        System.out.println("3. Back to main menu");
        switch (Character.toLowerCase(scan.next().charAt(0))) {
            case '1':
                service.viewBookList();
                break;
            case '2':
                System.out.println("Select book ISBN: ");
                service.replaceBook(scan.nextInt());
                break;
            case '3':
                return;
            case '4':
                System.out.println("Select correct item (1-3)");
                bookMenuUser();
        }
    }

}
