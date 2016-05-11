import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class CategoryTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Category_instantiatesCorrectly_true() {
    Category myCategory = new Category("Chicken");
    assertEquals(true, myCategory instanceof Category);
  }

  @Test
  public void methods_getsAllInfo_true() {
    Category myCategory = new Category("Chicken");
    assertEquals("Chicken", myCategory.getDescription());
  }
}
