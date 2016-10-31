package edu.cursor.library.user.registry;

import edu.cursor.library.book.entity.TblBook;
import edu.cursor.library.book.service.BookServiceImpl;
import edu.cursor.library.user.entity.TblUser;
import edu.cursor.library.user.registry.entity.TblUserBooksRegistry;

import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;

public class UserBooksRegistryImpl implements UserBooksRegistry {

    BookServiceImpl bookService = BookServiceImpl.getInstance();

    private static String projPath = System.getProperty("user.dir"),
            dbPath = "/src/main/resources/",
            fileName = "userBooksRegistry.csv",
            file = projPath + dbPath + fileName;


    public UserBooksRegistryImpl() {
    }
//   private static UserBooksRegistryImpl instance ;
//   public static UserBooksRegistryImpl getInstance(){
//      if(instance == null){
//         synchronized (UserBooksRegistryImpl.class){
//            if(instance == null){
//               instance = new UserBooksRegistryImpl();
//            }
//         }
//      }
//      return instance;
//   }


    public List<TblUserBooksRegistry> getRegistryRecords() {
        List<TblUserBooksRegistry> userBooksRegistries = new ArrayList<>();
        String[] flds;
        try (Scanner scan = new Scanner(new File(file))) {

            // 1. Get all registry records to List<TblUserBooksReg>
            while (scan.hasNext()) {
                flds = scan.nextLine().split(",");
                if (!flds[0].trim().equals("USER_ID")) {
                    userBooksRegistries.add(
                            new TblUserBooksRegistry(Integer.parseInt(flds[0].trim()), Integer.parseInt(flds[1].trim()))
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userBooksRegistries;
    }


    //   @Override
    public Map<Integer, List<Integer>> getRegistryMapByUsers() {
        Map<Integer, List<Integer>> tempRegistry = new HashMap<>();
        List<TblUserBooksRegistry> userBooksRegistries = getRegistryRecords();
        List<Integer> innerBooksList = new ArrayList<>();

        userBooksRegistries.sort((i1, i2) -> i2.compareTo(i1));

        // 2. Put all together...
        int prevUserId = 0;
        if (userBooksRegistries.size() != 0) {
            prevUserId = userBooksRegistries.get(0).getUserId();
        } else {
            return tempRegistry;
        }
        int forCount = userBooksRegistries.size(),
                count = 1;

        for (TblUserBooksRegistry r : userBooksRegistries) {
            if (prevUserId == r.getUserId()) {
                innerBooksList.add(r.getBookIsbn());
                if (count == forCount) {
                    tempRegistry.put(prevUserId, innerBooksList);
                }
            } else {
                tempRegistry.put(prevUserId, innerBooksList);
                innerBooksList = new ArrayList<>();
                innerBooksList.add(r.getBookIsbn());
            }
            prevUserId = r.getUserId();
            count++;
        }

        return tempRegistry;
    }


    @Override
    public List<TblBook> getTakenBooksByIsbn(int isbn) {
        List<TblBook> takenBooks = new ArrayList<>();

        List<TblUserBooksRegistry> registryList = getRegistryRecords();
        Map<TblUserBooksRegistry, Long> map = registryList.stream()
                .filter(i -> i.getBookIsbn() == isbn)
                .collect(
                        Collectors.groupingBy(
                                i -> i, Collectors.counting()
                        )
                );

        for (Map.Entry<TblUserBooksRegistry, Long> m : map.entrySet()) {
            takenBooks.add(bookService.getBookById(String.valueOf(m.getKey().getBookIsbn())));
        }

        System.out.println(map);
        System.out.println(registryList);
        System.out.println(takenBooks);

        return takenBooks;
    }

    @Override
    public List<TblBook> getTakenBooksByUser(int user_id) {
        return null;
    }

    @Override
    public TblUser getUserByTakenBook(int book_id) {
        return null;
    }


    @Override
    public void registerBook(TblUser user, TblBook book) {
        List<TblUserBooksRegistry> registryRecords = getRegistryRecords();
        registryRecords.add(new TblUserBooksRegistry(user.getId(), book.getISBN()));

        putRegistry(registryRecords);
    }

    @Override
    public void putRegistry(List<TblUserBooksRegistry> registryRecords) {

        try (FileWriter fw = new FileWriter(new File(file))) {

            System.out.println("....................... RECORDING HERE ....................................");
            fw.write("USER_ID,BOOK_ID\n");

            for (TblUserBooksRegistry r : registryRecords) {
                fw.write(r.getUserId() + "," + r.getBookIsbn() + "\n");
                System.out.println("------------------------- inside recording map -------------------------");
            }
            fw.flush();

            System.out.println("Taken books: " + registryRecords + "\n\n\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void unregisterBook(TblUser user, TblBook book) {
        List<TblUserBooksRegistry> registryRecords = getRegistryRecords();

        int count = 0;
        for (TblUserBooksRegistry r : registryRecords) {
            if (r.getUserId() == user.getId() && r.getBookIsbn() == book.getISBN()) {
                registryRecords.remove(count);
                break;
            }
            count++;
        }

        putRegistry(registryRecords);
    }


    public static void main(String[] args) {
//      Map<Integer, Integer> m1 = new HashMap<>();
//      Map<Integer, List<Integer>> m2 = new HashMap<>();
//      List<Integer> l1 = new ArrayList<>();
//
//      l1.add(1);
//      l1.add(2);
//      l1.add(3);
//
//      m1.put(1,1);
//      m2.put(2, l1);
//
//      System.out.println( m1 );
//      System.out.println( m2 );


//      getInstance().getRegistry() ;

//      getInstance().getTakenBooksByIsbn(17);

    }

}