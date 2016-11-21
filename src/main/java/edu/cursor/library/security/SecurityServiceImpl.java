package edu.cursor.library.security;

import edu.cursor.library.infrastructure.persistence.MySqlCredentialsDao;
import edu.cursor.library.infrastructure.persistence.MySqlUserDao;
import edu.cursor.library.model.TblUser;
import java.util.Iterator;
import java.util.List;

import edu.cursor.library.service.UserServiceImpl;


public class SecurityServiceImpl implements SecurityService {

//   private CredentialsImpl cred = CredentialsImpl.getInstance();
   private MySqlCredentialsDao cred = new MySqlCredentialsDao();
   private UserServiceImpl userService = UserServiceImpl.getInstance();
   private static List<TblUser> tempUserList = null ;
//   private CSVFileUserDao userDao = new CSVFileUserDao();
   private MySqlUserDao userDao = new MySqlUserDao();


   private SecurityServiceImpl() {}
   private static SecurityServiceImpl instance;
   public static SecurityServiceImpl getInstance(){
      if( instance == null ){
         synchronized (SecurityServiceImpl.class){
            // Double check
            if (instance == null) {
               instance = new SecurityServiceImpl() ;
            }
         }
      }
      return instance;
   }



   @Override
   public boolean validateLogin(String val) {
      return val.matches("^(?=.{4,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$");
   }

   @Override
   public boolean validatePass(String val) {
      return (val.length() >= 4);
   }

   @Override
   public boolean validateMail(String val) {
      return val.matches("^(.+)@(.+)\\.(.+){1,3}$");
   }

   @Override
   public boolean validateName(String val) {
      return val.matches("^[a-zA-Z0-9_-]{2,16}$");
   }

   @Override
   public boolean validateMobile(String val) {
      try{
         int intPhone = Integer.parseInt(val);
         return intPhone != 0 && intPhone >= 100000000 && val.matches("^(\\d{9})$");
      }catch (Exception e){
         return false;
      }
   }

   public boolean validateUserId(String val) {
      return val.matches("^(\\d{1,9})$");
   }

   @Override
   public boolean loginAvailable(String desiredLogin, boolean showMessage) {
      boolean is = false;
//      tempUserList = userService.getUserList() ;
      tempUserList = userDao.findAll();

      long loginOccurrences = tempUserList.stream().filter(i -> i.getEMail().equalsIgnoreCase(desiredLogin)).count();
      if(loginOccurrences > 0){
         if(showMessage)
            System.out.println("Login NOT available.");
         is = false ;
      } else {
         is = true;
      }

      return is;
   }

   @Override
   public int getNewUserId(){
//      tempUserList = userService.getUserList() ;
      tempUserList = userDao.findAll();

      Iterator<TblUser> itr = tempUserList.iterator();
      int currentId = 1 ;
      while (itr.hasNext()) {
         currentId = itr.next().getId();
      }
      return currentId + 1;
   }


   @Override
   public boolean userExists(String login) {
      return !loginAvailable(login, false);
   }

   @Override
   public boolean userExists(int userId) {
//      TblUser user = UserServiceImpl.getInstance().getUser( userId );
      TblUser user = userDao.findById( userId );
      try{
         String login = user.getEMail();
         return !loginAvailable(login, false);
      } catch (Exception e){
         return false;
      }
   }

   @Override
   public boolean passIsCorrect(String login, String pass) {
      int id ;
//      tempUserList = userService.getUserList() ;
      tempUserList = userDao.findAll();
      Iterator<TblUser> itr = tempUserList.iterator();
      while(itr.hasNext()){
         TblUser user = itr.next() ;

         // Find User by id
         if( user.getEMail().equals(login) ){
            id = user.getId();

//            Map<Integer, String> credentials = cred.getCredentialsList();
//            for( Map.Entry<Integer, String> c : credentials.entrySet()){
//               // Find Pass of user
//               if(c.getKey()==id && c.getValue().equals(pass)){
//                  return true;
//               }
//            }
            String userPassSaved = cred.getPassword(login) ;
            if( userPassSaved.equals(pass) ){
               return true;
            }

         }
      }
      return false;
   }

   @Override
   public TblUser getUser(int id) {
//      tempUserList = userService.getUserList();
      tempUserList = userDao.findAll();

      for( TblUser user : tempUserList ){
         if(user.getId() == id){
            return user ;
         }
      }
      return null;
   }

   @Override
   public TblUser getUser(String mail) {
//      tempUserList = userService.getUserList();
      tempUserList = userDao.findAll();

      for( TblUser user : tempUserList ){
         if(user.getEMail().equals( mail)){
            return user ;
         }
      }
      return null;
   }


   public boolean readYesNo( String val ){
      return val.equalsIgnoreCase("y");
   }

   //   public static void main(String[] args) {
//      SecurityServiceImpl service = new SecurityServiceImpl();
//      System.out.println(service.getUser(1));
//   }
}