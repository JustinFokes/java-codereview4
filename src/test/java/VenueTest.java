import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class ReviewTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Review_instantiatesCorrectly_true() {
    Venue newVenue = new Venue("Crystal Ball-Room")
    assertEquals(true, myVenue instanceof Venue);
  }

  @Test
  public void methods_getsAllInfo_true() {
    Venue newVenue = new Venue("Crystal Ball-Room")
    assertEquals("Crystal Ball-Room", myVenue.getName());
  }
}
