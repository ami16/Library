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
// (lesia) public User(String name, String login, String email, String password
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

//   @Override
//   public boolean equals(Object obj) {
//      if (this == obj)return true;
//      if (obj == null)return false;
//      if (getClass() != obj.getClass())return false;
//      User other = (User) obj;
//      if (email == null) {
//         if (other.email != null)
//            return false;
//      } else if (!email.equals(other.email))
//         return false;
//      if (login == null) {
//         if (other.login != null)
//            return false;
//      } else if (!login.equals(other.login))
//         return false;
//      if (name == null) {
//         if (other.name != null)
//            return false;
//      } else if (!name.equals(other.name))
//         return false;
//      if (password == null) {
//         if (other.password != null)
//            return false;
//      } else if (!password.equals(other.password))
//         return false;
//      return true;
//   }

   @Override
   public int hashCode() {
      int result = getLogin().hashCode();
      result = 31 * result + password.hashCode();
      result = 31 * result + getEmail().hashCode();
      result = 31 * result + getName().hashCode();
      result = 31 * result + getRole();
      return result;
   }

//   @Override
//   public int hashCode() {
//      final int prime = 31;
//      int result = 1;
//      result = prime * result + ((email == null) ? 0 : email.hashCode());
//      result = prime * result + ((login == null) ? 0 : login.hashCode());
//      result = prime * result + ((name == null) ? 0 : name.hashCode());
//      result = prime * result + ((password == null) ? 0 : password.hashCode());
//      return result;
//   }

   @Override
   public String toString() {
//      return "User [name=" + name + ", login=" + login + ", email=" + email + ", password=" + password + ", role:" + role + "]\n";
      return "User [login: " + login + ",\t\tpassword: " + password + ",\t\temail: " + email + ",\t\tname: " + name + ",\t\trole: " + role + "]\n";
   }
}