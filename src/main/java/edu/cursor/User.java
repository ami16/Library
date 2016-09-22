package edu.cursor;

public class User {

   private String login;
   private String password;
   private String email ;
   private String name ;
   private int role ;

   public String getLogin() { return login; }
   public String getPass() { return password; }
   public String getEmail() { return email; }
   public String getName() { return name; }
   public int getRole() { return role; }

   public void setLogin(String login) { this.login = login; }
   public void setPass(String pass) { this.password = pass; }
   public void setEmail(String email) { this.email = email; }
   public void setName(String name) { this.name = name; }
   public void setRole(int role) { this.role = role; }

   public User(String login, String pass, String email, String name, int role) {
      this.login = login;
      this.password = pass;
      this.email = email;
      this.name = name;
      this.role = role;
   }



   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof User)) return false;

      User user = (User) o;

      if (getRole() != user.getRole()) return false;
      if (!getLogin().equals(user.getLogin())) return false;
      if (!password.equals(user.password)) return false;
      if (!getEmail().equals(user.getEmail())) return false;
      return getName().equals(user.getName());

   }

   @Override
   public int hashCode() {
      int result = getLogin().hashCode();
      result = 31 * result + password.hashCode();
      result = 31 * result + getEmail().hashCode();
      result = 31 * result + getName().hashCode();
      result = 31 * result + getRole();
      return result;
   }
}