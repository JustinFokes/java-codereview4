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

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Band.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIRecipesAretheSame() {
    Band myBand = new Band("Fugazi");
    Band myBandTwo = new Band("Fugazi");
    assertTrue(myBand.equals(myBandTwo));
  }

  @Test
  public void save_bandIsSavedWithAnId_true() {
    Band myBand = new Band("Fugazi");
    myBand.save();
    Band savedBand = Band.all().get(0);
    assertEquals(myBand.getId(), savedBand.getId());
  }

  @Test
  public void find_bandIsFoundWithId_true() {
    Band myBand = new Band("Fugazi");
    myBand.save();
    Band savedBand = Band.find(myBand.getId());
    assertTrue(myBand.equals(savedBand));
  }
}
