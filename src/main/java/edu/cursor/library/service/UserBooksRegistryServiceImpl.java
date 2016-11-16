package edu.cursor.library.service;

import edu.cursor.library.infrastructure.persistance.MySqlUserBooksRegistryDao;
import edu.cursor.library.model.TblBook;
import edu.cursor.library.model.TblUser;
import edu.cursor.library.model.TblUserBooksRegistry;

import java.util.*;


public class UserBooksRegistryServiceImpl implements UserBooksRegistryService {

   private BookServiceImpl bookService = BookServiceImpl.getInstance();
   private MySqlUserBooksRegistryDao registryDao = new MySqlUserBooksRegistryDao();

   public UserBooksRegistryServiceImpl() { }


   public Map<Integer, List<Integer>> getRegistryMapByUsers() {
      Map<Integer, List<Integer>> tempRegistry = new HashMap<>();

      List<TblUserBooksRegistry> userBooksRegistries = registryDao.findAll();
      List<Integer> innerBooksList = new ArrayList<>();

      userBooksRegistries.sort( (i1, i2) -> i2.compareTo(i1) );

      // 2. Put all together...
      int prevUserId = 0;
      if(userBooksRegistries.size() != 0){
         prevUserId = userBooksRegistries.get(0).getUserId();
      } else {
         return tempRegistry;
      }
      int forCount = userBooksRegistries.size();
      int count = 1;


      for( TblUserBooksRegistry r : userBooksRegistries ){
         if( prevUserId == r.getUserId() ){
            innerBooksList.add( r.getBookIsbn() );
            if( forCount == count ){
               tempRegistry.put( prevUserId, innerBooksList );
            }
         }
         else {
            tempRegistry.put( prevUserId, innerBooksList );
            innerBooksList = new ArrayList<>();
            innerBooksList.add( r.getBookIsbn() );
            if( forCount == count ){
               tempRegistry.put( r.getUserId(), innerBooksList );
            }
         }
         prevUserId = r.getUserId();
         count++ ;
      }


      return tempRegistry;
   }


   @Override
   public List<TblBook> getTakenBooksByIsbn(int isbn) {
      return registryDao.findTakenBooksByIsbn(isbn);
   }

   public int getTakenBooksCountByIsbn( int isbn ){
      return getTakenBooksByIsbn( isbn ).size();
   }

   @Override
   public List<TblBook> getTakenBooksByUserId(int user_id) {
      return registryDao.findTakenBooksByUserId( user_id );
   }



   @Override
   public void registerBook(TblUser user, TblBook book) {
      List<TblUserBooksRegistry> registryRecords = registryDao.findAll();
      registryRecords.add( new TblUserBooksRegistry( user.getId(), book.getISBN() ) ) ;
      registryDao.saveRegistry( user, book ) ;
   }


   @Override
   public void unregisterBook(TblUser user, TblBook book) {
      registryDao.deleteEntry( user, book );
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

//      System.out.println( new UserBooksRegistryServiceImpl().getTakenBooksByIsbn(14) );
//      System.out.println( new UserBooksRegistryServiceImpl().getTakenBooksByUserId(10) );

   }

}