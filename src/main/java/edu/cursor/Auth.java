package edu.cursor;

import java.util.*;

public class Auth {

   Credential cred = Credential.getInstance();

   private boolean userIsLogged = false;
//   private int loggedUser;
   private User loggedUser;
   private List<User> userList = new ArrayList<>();


   public boolean getUserIsLogged() {
      return userIsLogged;
   }

   public User getLoggedUser() {
      return loggedUser;
   }

   public List<User> getUserList() {
      return userList;
   }

   public void setUserIsLogged(boolean usrIsLogged) {
      userIsLogged = usrIsLogged;
   }

   public void setLoggedUser(User loggedUsr) {
      loggedUser = loggedUsr;
   }


   private static Auth instance ;

   private Auth() { }

   public static Auth getInstance(){
      if( instance == null ){
         synchronized (Auth.class){
            // Double check
            if (instance == null) {
               instance = new Auth() ;
            }
         }
      }
      return instance ;
   }


   // 1. USERS LIST. Olesya
   public List<User> createUserList() {
      userList.add(new User(1, "Peter", "Griffin", "peter@gmail.com", 982545785, "Quahog, Spoon st., 34", "2016-11-25", UserStates.USER));
      userList.add(new User(2, "Lois", "Griffin", "lois@gmail.com", 735458787, "Quahog, Spoon st.,34", "2014-05-08", UserStates.USER));
      userList.add(
          new User(3, "Homer", "Simpson", "hommy@gmail.com", 598741547, "Springfield, Evergreeen st.,45", "2016-04-13", UserStates.USER));
      userList.add(
          new User(4, "Eric", "Cartman", "eric@gmail.com", 857845175, "South Park, Jew st., 1845", "2014-25-06", UserStates.USER));
      userList.add(
          new User(5, "Leopold", "Stoch", "butters@gmail.com", 547845145, "South Park, Raisins st., 34", "2013-18-07", UserStates.USER));
      userList.add(new User(6, "Yuzer", "Yuzerenko", "m@m.c", 987654321, "Address in a bit blaabcdefghijklmn", "2016-01-01", UserStates.USER));
      userList.add(new User(7, "Alex", "Ogiyenko", "a@mail.com", 111111119, "Underground Ave 189/4a", "2010-01-01", UserStates.ADMIN));
      return userList;
   }

   public List<User> addUser( int id, String firstName, String lastName, String email, int mobileNo, String address, String dateOfRegistration, String pass ) {
      userList.add( new User(id, firstName, lastName, email, mobileNo, address, dateOfRegistration, UserStates.USER) ) ;
      cred.addCred( id, pass ) ;
      return userList;
   }

      // ------------------
   // 1 - REGISTER USER

   public void registerUser() {
      Scanner scan = new Scanner(System.in);
      String desiredMail;
      boolean loginAllowed = false;

      // LOGIN
      outer: do {
         System.out.println("Please enter your MAIL as a login: ");
         desiredMail = scan.nextLine().trim();
         // X
         if (desiredMail.equalsIgnoreCase("x"))
            break;
         if (validateMail(desiredMail)) {

            if (!loginAvailable(desiredMail)) {
               loginAllowed = false;
            } else {

               // MAIL ok.
               // Now PASS
               boolean passCorrect = false;
               boolean passEqual = false;
               String pass1;
               System.out.print("OKAY NOW! Enter your password. ");
               do {
                  System.out.println("Password must be at least 4 chars:");
                  pass1 = scan.nextLine().trim();
                  // X
                  if (pass1.equalsIgnoreCase("x"))
                     break outer;
                  if (validatePass(pass1))
                     passCorrect = true;
               } while (!passCorrect);
               do {
                  System.out.println("Repeat pass ones more:");
                  String pass2 = scan.nextLine().trim();

                  // X
                  if (pass2.equalsIgnoreCase("x"))
                     break outer;
                  if (pass1.equals(pass2)) {
                     passEqual = true;
                  } else {
                     System.out.println("Your passwords doesn't match.");
                  }
               } while (!passEqual);

               // PASS ok. Now Name
               boolean correctName = false;
               String desiredName;
               do {
                  System.out.print("Pls give your name: ");
                  desiredName = scan.nextLine().trim();
                  // X
                  if (desiredName.equalsIgnoreCase("x"))
                     break outer;
                  if (validateName(desiredName))
                     correctName = true;
               } while (!correctName);

               // Name ok. Now name2
               boolean correctName2 = false;
               String desiredName2;
               do {
                  System.out.print("Pls provide your second name: ");
                  desiredName2 = scan.nextLine().trim();
                  // X
                  if (desiredName2.equalsIgnoreCase("x"))
                     break outer;
                  if (validateName(desiredName2))
                     correctName2 = true;
               } while (!correctName2);

               // name2 ok. Now MOBILE
               boolean correctMobile = false;
               int desiredMobile;
               do {
                  System.out.print("Your mobile # (9 numbers): ");
                  desiredMobile = scan.nextInt();
                  // X
                  if ( Integer.toString(desiredMobile).equalsIgnoreCase("x"))
                     break outer;
                  if (validateMobile(desiredMobile))
                     correctMobile = true;
               } while (!correctMobile);

               // MOBILE ok. Now ADDRESS
               boolean correctAddr = false;
               String desiredAddr;
               do {
                  System.out.print("Provide your address (10 chars min): ");
                  desiredAddr = scan.nextLine().trim();
                  // X
                  if ( desiredAddr.equalsIgnoreCase("x"))
                     break outer;
                  if ( desiredAddr.length() > 10 )
                     correctAddr = true;
               } while (!correctAddr);

               loginAllowed = true;

               int newId = getNewUserId() ;
               addUser( newId, desiredName, desiredName2, desiredMail, desiredMobile, desiredAddr, new Date().toString(), pass1 ) ;
               System.out.println("Now login using your credentials");
            }

         } else {
            System.out.println("Not allowed");
         }
      } while (!loginAllowed);
   }

   public int getNewUserId(){
      Iterator<User> itr = getUserList().iterator();
      int currentId = 1 ;
      while (itr.hasNext()) {
         currentId = itr.next().getId();
      }
      return currentId + 1;
   }

   // 2 - LOGIN <VALIDATE USER>
   public void validateUser() {

      Scanner scan = new Scanner(System.in);
      String userLogin, userPassword, verifiedLog = "";
      boolean is = false;

      System.out.println("Enter your login:");
      // LOGIN
      do {
         userLogin = scan.nextLine().trim();
         if (userLogin.equals("")) {
            System.out.print("Enter smth: ");
         } else {
            is = userExists(userLogin);
            if (is)
               verifiedLog = userLogin;
         }
         if (!is) {
            System.out.println("No such login (3)");
         }
      } while (!is);

      // PASS
      System.out.println("Enter your pass:");
      do {
         userPassword = scan.nextLine().trim();
         if (userPassword.equals("")) {
            System.out.print("Enter smth: ");
         } else {
            is = passIsCorrect(verifiedLog, userPassword);
            if (is)
               logIn( getUserId(userLogin) );
         }
         if (!is) {
            System.out.println("Wrong password (3)");
         }
      } while (!is);
   }

   public boolean validateLogin(String val) {
      // http://stackoverflow.com/questions/12018245/regular-expression-to-validate-username
      return val.matches("^(?=.{4,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$");
   }

   public boolean validatePass(String val) {
      return (val.length() >= 4);
   }

   public boolean validateMail(String val) {
      return val.matches("^(.+)@(.+)\\.(.+){1,3}$");
   }

   public boolean validateName(String val) {
      return val.matches("^[a-zA-Z0-9_-]{2,16}$");
   }

   public boolean validateMobile( int val ){
      return Integer.toString(val).matches("^(\\d{9})$");
   }

   public boolean loginAvailable(String desiredLogin) {
      boolean loginAvailable = false;
      Iterator<User> iterator = getUserList().iterator();
      while (iterator.hasNext()) {
         User user = iterator.next();
         if (user.getEmail().equals(desiredLogin)) {
            loginAvailable = false;
            System.out.println("Login NOT available.");
            break;
         } else {
            loginAvailable = true;
         }
      }
      return loginAvailable;
   }

   public User getUser(String login) {
      Iterator<User> iterator = getUserList().iterator();
      while (iterator.hasNext()) {
         User user = iterator.next();
         if (user.getEmail().equals(login)) {
            return user;
         }
      }
      return iterator.next();
   }
   public User getUser(int userId) {
      Iterator<User> iterator = getUserList().iterator();
      while (iterator.hasNext()) {
         User user = iterator.next();
         if (user.getId() == userId) {
            return user;
         }
      }
      return iterator.next();
   }

   public int getUserId(String login) {
      Iterator<User> iterator = getUserList().iterator();
      while (iterator.hasNext()) {
         User user = iterator.next();
         if (user.getEmail().equals(login)) {
            return user.getId();
         }
      }
      return 0;
   }

   public boolean userExists(String login) {
      Iterator<User> iterator = getUserList().iterator();
      while (iterator.hasNext()) {
         User user = iterator.next();
         if (user.getEmail().equals(login)) {
            return true;
         }
      }
      return false;
   }

   public boolean passIsCorrect(String login, String pass) {
      Iterator<User> iterator = getUserList().iterator();
      while (iterator.hasNext()) {
         User user = iterator.next();
         if ( user.getEmail().equals(login) ) {
            if( cred.getPass(getUserId(user.getEmail())).equals(pass) ){
               return true;
            }
         }
      }
      return false;
   }

   public void logIn(int login) {
      setLoggedUser( getUser(login) );
      setUserIsLogged(true);
   }

   public void logOut() {
      setLoggedUser(null);
      setUserIsLogged(false);
   }
}