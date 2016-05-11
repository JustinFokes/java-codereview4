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
}
