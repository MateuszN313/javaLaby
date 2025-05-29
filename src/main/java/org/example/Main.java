package org.example;

import auth.Account;
import database.DatabaseConnection;
import music.ListenerAccount;
import org.mindrot.jbcrypt.BCrypt;

import javax.naming.AuthenticationException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {

        // Zad.1

//        DatabaseConnection db = new DatabaseConnection();
//
//        try {
//            // You can use any file name, here we use "test.db"
//            db.connect("test.db");
//            Connection conn = db.getConnection();
//
//            Statement stmt = conn.createStatement();
//
//            // 1. Create a table
//            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, name TEXT)");
//
//            // 2. Insert a row
//            stmt.executeUpdate("INSERT INTO users (name) VALUES ('Alice')");
//            stmt.executeUpdate("INSERT INTO users (name) VALUES ('Bob')");
//
//            // 3. Read data
//            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
//
//            // 4. Print data
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                System.out.println("User ID: " + id + ", Name: " + name);
//            }
//
//            rs.close();
//            stmt.close();
//            db.disconnect();
//
//        } catch (SQLException e) {
//            System.out.println("Database error: " + e.getMessage());
//        }

        //Zad. 2

//        DatabaseConnection.connect("accounts.db");
//        Account.Persistence.init();
//
//        // Register users
//        System.out.println("\n📥 Rejestracja:");
//        Account.Persistence.register("alice", "password123");
//        Account.Persistence.register("bob", "securePass");
//
//        // Authenticate
//        System.out.println("\n🔐 Testowanie logowania:");
//        try{
//            System.out.println("alice/password123: " + Account.Persistence.authenticate("alice", "password123")); // true
//            System.out.println("bob/wrongPass: " + Account.Persistence.authenticate("bob", "wrongPass")); // false
//        }catch(AuthenticationException e){
//            System.err.println(e.getMessage());
//        }

        // Fetch account by username
//        System.out.println("\n📄 Pobieranie konta:");
//        Account acc = accountManager.getAccount("alice");
//        if (acc != null) {
//            System.out.println("Znaleziono konto: id=" + acc.id() + ", username=" + acc.username());
//        }

        // Fetch account by ID
//        if (acc != null) {
//            Account accById = accountManager.getAccount(String.valueOf(acc.id()));
//            if (accById != null) {
//                System.out.println("Z konta ID: id=" + accById.id() + ", username=" + accById.username());
//            }
//        }

        DatabaseConnection.connect("accounts.db");
        try {
            ListenerAccount.Persistence.init();
            ListenerAccount.Persistence.register("wiesiek","haslo57");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        DatabaseConnection.disconnect();
    }
}