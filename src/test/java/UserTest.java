import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class UserTest {
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
    assertEquals("Justin", myUser.getName());
  }


    @Test
    public void all_emptyAtFirst() {
      assertEquals(User.all().size(), 0);
    }

    @Test
    public void equals_returnsTrueIUsersAretheSame() {
      User myUserOne = new User("Justin");
      User myUserTwo = new User("Justin");
      assertTrue(myUserOne.equals(myUserTwo));
    }

    @Test
    public void save_savesIntoDatabase_true() {
      User myUser = new User("Justin");
      myUser.save();
      assertTrue(User.all().get(0).equals(myUser));
    }

    @Test
    public void save_assignsIdToObject() {
      User myUser = new User("Justin");
      myUser.save();
      User savedUser = User.all().get(0);
      assertEquals(myUser.getId(), savedUser.getId());
    }

    @Test
    public void find_findUserInDatabase_true() {
      User myUser = new User("Justin");
      myUser.save();
      User savedUser = User.find(myUser.getId());
      assertTrue(myUser.equals(savedUser));
    }

    @Test
    public void addRecipe_recipesAddedToRecipesUsersTable_true() {
      User myUser = new User("Perry");
      myUser.save();
      Recipe myRecipe = new Recipe("Fun Chicken", "Add Chicken.", "Cook Chicken", "Chicken");
      myRecipe.save();
      myUser.addRecipe(myRecipe);
      Recipe savedRecipe = myUser.getRecipe().get(0);
      assertTrue(myRecipe.equals(savedRecipe));
    }

    @Test
    public void addReview_reviewAddedToRecipe_true() {
      Review myReview = new Review("This recipe sucks.");
      myReview.save();
      Recipe myRecipe = new Recipe("Fun Chicken", "This is a list of ingredients",  "These are the instructions", "Spanglish");
      myRecipe.save();
      myReview.addRecipe(myRecipe);
      Recipe savedRecipe = myReview.getRecipes().get(0);
      assertTrue(myRecipe.equals(savedRecipe));
    }

    @Test
    public void delete_userDeletedFromUsersTableAndUsersReviewsTableAndRecipesUsersTable_true() {
      User myUser = new User("Perry");
      myUser.save();
      Review myReview = new Review("This recipe sucks.");
      Recipe myRecipe = new Recipe("Fun Chicken", "This is a list of ingredients",  "These are the instructions", "Spanglish");
      myRecipe.save();
      myUser.addReview(myReview);
      myUser.addRecipe(myRecipe);
      myUser.delete();
      assertEquals(0, User.all().size());
    }

}
