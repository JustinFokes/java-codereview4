import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class RecipeTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Recipe_instantiatesCorrectly_true() {
    Recipe myRecipe = new Recipe("Fun Chicken", "This is a list of ingredients",  "These are the instructions");
    assertEquals(true, myRecipe instanceof Recipe);
  }

  @Test
  public void methods_getsAllInfo_true() {
    Recipe myRecipe = new Recipe("Fun Chicken", "This is a list of ingredients",  "These are the instructions");
    assertEquals("Fun Chicken", myRecipe.getName());
    assertEquals("This is a list of ingredients", myRecipe.getIngredients());
    assertEquals("These are the instructions", myRecipe.getInstructions());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Recipe.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIRecipesAretheSame() {
    Recipe myRecipeOne = new Recipe("Fun Chicken", "This is a list of ingredients",  "These are the instructions");
    Recipe myRecipeTwo = new Recipe("Fun Chicken", "This is a list of ingredients",  "These are the instructions");
    assertTrue(myRecipeOne.equals(myRecipeTwo));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Recipe myRecipe = new Recipe("Fun Chicken", "This is a list of ingredients",  "These are the instructions");
    myRecipe.save();
    assertTrue(Recipe.all().get(0).equals(myRecipe));
  }

  @Test
  public void save_assignsIdToObject() {
    Recipe myRecipe = new Recipe("Fun Chicken", "This is a list of ingredients",  "These are the instructions");
    myRecipe.save();
    Recipe savedRecipe = Recipe.all().get(0);
    assertEquals(myRecipe.getId(), savedRecipe.getId());
  }

  @Test
  public void find_findRecipeInDatabase_true() {
    Recipe myRecipe = new Recipe("Fun Chicken", "This is a list of ingredients",  "These are the instructions");
    myRecipe.save();
    Recipe savedRecipe = Recipe.find(myRecipe.getId());
    assertTrue(myRecipe.equals(savedRecipe));
  }

}
