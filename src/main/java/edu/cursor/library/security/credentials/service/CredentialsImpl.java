package edu.cursor.library.security.credentials.service;

import edu.cursor.library.user.entity.TblUser;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CredentialsImpl implements Credentials {

   private static String projPath = System.getProperty("user.dir"),
       dbPath = "/src/main/resources/",
       fileName = "credentialsList.csv",
       file = projPath + dbPath + fileName ;
   private Map<Integer, String> credentialsList = new HashMap<>();

   private static CredentialsImpl instance ;
   private CredentialsImpl() {}
   public static CredentialsImpl getInstance(){
      if( instance == null ){
         synchronized (CredentialsImpl.class){
            // Double check
            if (instance == null) {
               instance = new CredentialsImpl() ;
            }
         }
      }
      return instance ;
   }

   @Override
   public String getPassword(TblUser user){
      Map<Integer, String> credentialsList = getCredentialsList();

      Map<Integer, String> result = credentialsList.entrySet()
          .stream()
          .filter(i -> i.getKey()== user.getId() )
          .collect(Collectors.toMap( i -> i.getKey(), i -> i.getValue() ));
      return result.get( user.getId() );
   }

   @Override
   public Map<Integer, String> getCredentialsList() {
      credentialsList.clear();
      try(Scanner scan = new Scanner(new File(file))){
         String[] fields;
         while(scan.hasNext()){
            fields = scan.nextLine().split(",");
            if(fields[0].equals("ID")) continue;
            credentialsList.put(Integer.parseInt(fields[0]), fields[1]);
         }
      }catch(Exception e){
         e.printStackTrace();
      }
      return credentialsList;
   }

   @Override
   public void addCredentials ( int id, String pass ) {
//      getCredentialsList(file);
      getCredentialsList();
      credentialsList.put(id, pass) ;
      try(FileWriter fw = new FileWriter(file)){
         fw.write("ID,pass\n");
         for(Map.Entry<Integer, String> c : credentialsList.entrySet()){
            fw.write(c.getKey() + "," + c.getValue() + "\n");
         }
         fw.flush();
      }catch(Exception e){
         e.printStackTrace();
      }
   }

   public static void main(String[] args) {
      CredentialsImpl cred = CredentialsImpl.getInstance();
      cred.addCredentials(10, "bobik");
      System.out.println( cred.credentialsList );
   }
}