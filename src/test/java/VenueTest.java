import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class VenueTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Review_instantiatesCorrectly_true() {
    Venue newVenue = new Venue("Crystal Ball-Room", "253-448-1364", "NorthEast Portland");
    assertEquals(true, newVenue instanceof Venue);
  }

  @Test
  public void methods_getsAllInfo_true() {
    Venue newVenue = new Venue("Crystal Ball-Room", "253-448-1364", "NorthEast Portland");
    assertEquals("Crystal Ball-Room", newVenue.getName());
    assertEquals("253-448-1364", newVenue.getPhone());
    assertEquals("NorthEast Portland", newVenue.getLocation());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Venue.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIRecipesAretheSame() {
    Venue newVenue = new Venue("Crystal Ball-Room", "253-448-1364", "NorthEast Portland");
    Venue newVenueTwo = new Venue("Crystal Ball-Room", "253-448-1364", "NorthEast Portland");
    assertTrue(newVenue.equals(newVenueTwo));
  }

  @Test
  public void save_VenueIsSavedWithAnId_int() {
    Venue newVenue = new Venue("Crystal Ball-Room", "253-448-1364", "NorthEast Portland");
    newVenue.save();
    Venue savedVenue = Venue.all().get(0);
    assertEquals(newVenue.getId(), savedVenue.getId());
  }

  @Test
  public void find_VenueIsFoundById_true() {
    Venue newVenue = new Venue("Crystal Ball-Room", "253-448-1364", "NorthEast Portland");
    newVenue.save();
    Venue savedVenue = Venue.find(newVenue.getId());
    assertTrue(newVenue.equals(savedVenue));

  }

  @Test
  public void addBand_addsBandToVenue_true() {
    Band myBand = new Band("Fugazi", "Punk", "DC");
    myBand.save();
    Venue newVenue = new Venue("Crystal Ball-Room", "253-448-1364", "NorthEast Portland");
    newVenue.save();
    newVenue.addBand(myBand);
    Band savedBand = newVenue.getBand().get(0);
    assertTrue(myBand.equals(savedBand));
  }
}
