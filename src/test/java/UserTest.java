import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class userTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void User_instantiatesCorrectly_true() {
    User myUser = new User("Justin");
    assertEquals(true, myUser instanceof User);
  }

  @Test
  public void methods_getsAllInfo_true() {
    User myUser = new User("Justin");
    assertEquals("Justin", myUser.getName())
  }
}
