import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class RecipeTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Recipe_instantiatesCorrectly_true() {
    Recipe myRecipe = new Recipe("Fun Chicken", "This is a list of ingredients",  "These are the instructions", "Spanglish");
    assertEquals(true, myRecipe instanceof Recipe);
  }

  @Test
  public void methods_getsAllInfo_true() {
    Recipe myRecipe = new Recipe("Fun Chicken", "This is a list of ingredients",  "These are the instructions", "Spanglish");
    assertEquals("Fun Chicken", myRecipe.getName());
    assertEquals("This is a list of ingredients", myRecipe.getIngredients());
    assertEquals("These are the instructions", myRecipe.getInstructions());
    assertEquals("Spanglish", myRecipe.getCategory());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Recipe.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIRecipesAretheSame() {
    Recipe myRecipeOne = new Recipe("Fun Chicken", "This is a list of ingredients",  "These are the instructions", "Spanglish");
    Recipe myRecipeTwo = new Recipe("Fun Chicken", "This is a list of ingredients",  "These are the instructions", "Spanglish");
    assertTrue(myRecipeOne.equals(myRecipeTwo));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Recipe myRecipe = new Recipe("Fun Chicken", "This is a list of ingredients",  "These are the instructions", "Spanglish");
    myRecipe.save();
    assertTrue(Recipe.all().get(0).equals(myRecipe));
  }

  @Test
  public void save_assignsIdToObject() {
    Recipe myRecipe = new Recipe("Fun Chicken", "This is a list of ingredients",  "These are the instructions", "Spanglish");
    myRecipe.save();
    Recipe savedRecipe = Recipe.all().get(0);
    assertEquals(myRecipe.getId(), savedRecipe.getId());
  }

  @Test
  public void find_findRecipeInDatabase_true() {
    Recipe myRecipe = new Recipe("Fun Chicken", "This is a list of ingredients",  "These are the instructions", "Spanglish");
    myRecipe.save();
    Recipe savedRecipe = Recipe.find(myRecipe.getId());
    assertTrue(myRecipe.equals(savedRecipe));
  }

  @Test
  public void addReview_reviewAddedToRecipe_true() {
    Recipe myRecipe = new Recipe("Fun Chicken", "This is a list of ingredients",  "These are the instructions", "Spanglish");
    myRecipe.save();
    Review myReview = new Review("This sucks");
    myReview.save();
    myRecipe.addReview(myReview);
    Review savedReview = myRecipe.getReviews().get(0);
    assertTrue(myReview.equals(savedReview));
  }

  @Test
  public void addUser_userAddedToRecipe_true() {
    Recipe myRecipe = new Recipe("Fun Chicken", "This is a list of ingredients",  "These are the instructions", "Spanglish");
    myRecipe.save();
    User myUser = new User("Perry");
    myUser.save();
    myRecipe.addUser(myUser);
    User savedUser = myRecipe.getUsers().get(0);
    assertTrue(myUser.equals(savedUser));
  }

  @Test
  public void delete_recipeDeletedFromAllTables_true() {
    Review myReview = new Review("This recipe sucks.");
    myReview.save();
    User myUser = new User("Perry");
    myUser.save();
    Recipe myRecipe = new Recipe("Fun Chicken", "This is a list of ingredients",  "These are the instructions", "Spanglish");
    myRecipe.save();
    myRecipe.addUser(myUser);
    myRecipe.addReview(myReview);
    myRecipe.delete();
    assertEquals(0, Recipe.all().size());
  }

}
