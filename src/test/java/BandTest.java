import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class BandTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Recipe_instantiatesCorrectly_true() {
    Band myBand = new Band("Fugazi", "Punk", "DC");
    assertEquals(true, myBand instanceof Band); 
  }

  @Test
  public void methods_getsAllInfo_true() {
    Band myBand = new Band("Fugazi", "Punk", "DC");
    assertEquals("Fugazi", myBand.getName());
    assertEquals("Punk", myBand.getGenre());
    assertEquals("DC", myBand.getHomeTown());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Band.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIRecipesAretheSame() {
    Band myBand = new Band("Fugazi", "Punk", "DC");
    Band myBandTwo = new Band("Fugazi", "Punk", "DC");
    assertTrue(myBand.equals(myBandTwo));
  }

  @Test
  public void save_bandIsSavedWithAnId_true() {
    Band myBand = new Band("Fugazi", "Punk", "DC");
    myBand.save();
    Band savedBand = Band.all().get(0);
    assertEquals(myBand.getId(), savedBand.getId());
  }

  @Test
  public void find_bandIsFoundWithId_true() {
    Band myBand = new Band("Fugazi", "Punk", "DC");
    myBand.save();
    Band savedBand = Band.find(myBand.getId());
    assertTrue(myBand.equals(savedBand));
  }

  @Test
  public void addVenue_addsVenueToBand_true() {
    Band myBand = new Band("Fugazi", "Punk", "DC");
    myBand.save();
    Venue newVenue = new Venue("The Crystal Ball-Room", "253-448-1364", "NorthEast Portland");
    newVenue.save();
    myBand.addVenue(newVenue);
    Venue savedVenue = myBand.getVenue().get(0);
    assertTrue(newVenue.equals(savedVenue));
  }

  @Test
  public void delete_deletesBandFromDatabase_true() {
    Band myBand = new Band("Fugazi", "Punk", "DC");
    myBand.save();
    Venue newVenue = new Venue("The Crystal Ball-Room", "253-448-1364", "NorthEast Portland");
    myBand.delete();
    assertEquals(0, Band.all().size());
  }

  @Test
  public void update_updatesTaskDescription_true() {
    Band myBand = new Band("Fugazi", "Punk", "DC");
    myBand.save();
    myBand.update("Jim", "Pop", "Seattle");
    assertEquals("Jill", Band.find(myBand.getId()).getName());
  }

}
