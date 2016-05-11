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
    assertEquals("This recipe sucks.", myReview.getReview());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Review.all().size(), 0);
  }

  @Test
  public void save_savesIntoDatabaseAndCreatesId_true() {
    Review myReview = new Review("This recipe sucks.");
    myReview.save();
    Review savedReview = Review.all().get(0);
    assertTrue(Review.all().get(0).equals(myReview));
    assertEquals(myReview.getId(), savedReview.getId());
  }

  @Test
  public void find_findReviewInDatabase_true() {
    Review myReview = new Review("This recipe sucks.");
    myReview.save();
    Review savedReview = Review.find(myReview.getId());
    assertTrue(myReview.equals(savedReview));
  }
}
