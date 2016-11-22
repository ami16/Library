package edu.cursor.library.model;

import org.springframework.data.annotation.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Table(name = "credentials")
public class TblCredentials {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id")
   private int id;
   @Column(name = "pass")
   private String pass ;

   public TblCredentials() {
   }

   public TblCredentials(int id, String pass) {
      this.id = id;
      this.pass = pass;
   }

   public int getId() {
      return id;
   }

   public String getPass() {
      return pass;
   }

   public void setId(int id) {
      this.id = id;
   }

   public void setPass(String pass) {
      this.pass = pass;
   }
}