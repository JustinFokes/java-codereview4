import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class ReviewTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Review_instantiatesCorrectly_true() {
    Review myReview = new Review("This recipe sucks.");
    assertEquals(true, myReview instanceof Review);
  }

  @Test
  public void methods_getsAllInfo_true() {
    Review myReview = new Review("This recipe sucks.");
    assertEquals("This recipe sucks.", myReview.getDescription());
  }
}
