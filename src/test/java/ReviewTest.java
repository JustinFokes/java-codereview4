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

  @Test
  public void addUser_userAddedToReview_true() {
    User myUser = new User("Perry");
    myUser.save();
    Review myReview = new Review("This recipe sucks.");
    myReview.save();
    myReview.addUser(myUser);
    User savedUser = myReview.getUser().get(0);
    assertTrue(myUser.equals(savedUser));
  }

  @Test
  public void addRecipe_userAddedToRecipe_true() {
    Review myReview = new Review("This recipe sucks.");
    myReview.save();
    Recipe myRecipe = new Recipe("Fun Chicken", "This is a list of ingredients",  "These are the instructions", "Spanglish");
    myRecipe.save();
    myReview.addRecipe(myRecipe);
    Recipe savedRecipe = myReview.getRecipes().get(0);
    assertTrue(myRecipe.equals(savedRecipe));
  }



}
