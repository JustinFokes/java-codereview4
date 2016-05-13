import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class BandTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Recipe_instantiatesCorrectly_true() {
    Band myBand = new Band("Fugazi");
    assertEquals(true, myBand instanceof Band); 
  }

  @Test
  public void methods_getsAllInfo_true() {
    Band myBand = new Band("Fugazi");
    assertEquals("Fugazi", myBand.getName());
  }
}
