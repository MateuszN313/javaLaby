import database.DatabaseConnection;
import music.ListenerAccount;
import org.junit.jupiter.api.Test;

import javax.naming.AuthenticationException;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

public class AccountTest {
    @Test
    void loggingTest(){
        DatabaseConnection.connect("accounts.db");
        try {
            ListenerAccount account1 = new ListenerAccount(12,"wiesiek");
            ListenerAccount account2 = ListenerAccount.Persistence.authenticate("wiesiek", "haslo57");
            assertEquals(account1.getId(), account2.getId());
            assertEquals(account1.getUsername(), account2.getUsername());
        }catch (AuthenticationException e){
            throw new RuntimeException(e);
        }
        DatabaseConnection.disconnect();
    }
}
