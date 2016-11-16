package edu.cursor.library.service;

import edu.cursor.library.model.TblBook;
import edu.cursor.library.model.TblUser;
import edu.cursor.library.model.TblUserBooksRegistry;

import java.util.List;


public interface UserBooksRegistryService {

   void registerBook(TblUser user, TblBook book);
   void unregisterBook(TblUser user, TblBook book);

   public List<TblBook> getTakenBooksByIsbn(int book_id);
   List<TblBook> getTakenBooksByUserId(int user_id );

}