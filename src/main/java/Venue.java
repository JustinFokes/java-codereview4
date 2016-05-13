import java.util.List;
import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Venue {
  private int id;
  private String name;
  private String phone;
  private String location;

  public Venue(String name, String phone, String location) {
    this.name = name;
    this.phone = phone;
    this.location = location;

  }

  public String getName() {
    return name;
  }

   public String getPhone() {
    return phone;
  }

   public String getLocation() {
    return location;
  }

  public int getId() {
    return id;
  }

  public static List<Venue> all() {
    String sql = "SELECT * FROM venues";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Venue.class);
    }
  }

  @Override
  public boolean equals(Object otherVenue) {
    if (!(otherVenue instanceof Venue)) {
      return false;
    } else {
      Venue newVenue = (Venue) otherVenue;
      return this.getName().equals(newVenue.getName()) &&
             this.getId() == newVenue.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO venues(name, phone, location) VALUES (:name, :phone, :location)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .addParameter("phone", this.name)
      .addParameter("location", this.name)
      .executeUpdate()
      .getKey();
    }
  }

  public static Venue find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM venues WHERE id=:id";
      Venue venue = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Venue.class);
      return venue;
    }
  }

  public void addBand(Band band) {
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO bands_venues(band_id, venue_id) VALUES (:band_id, :venue_id)";
      con.createQuery(sql)
      .addParameter("band_id", band.getId())
      .addParameter("venue_id", this.id)
      .executeUpdate();
    }
  }

  public List<Band> getBand() {
    try(Connection con = DB.sql2o.open()){
      String joinQuery = "SELECT band_id FROM bands_venues WHERE venue_id = :venue_id";
      List<Integer> bandIds = con.createQuery(joinQuery)
        .addParameter("venue_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Band> bands = new ArrayList<Band>();

      for (Integer bandId : bandIds) {
        String bandQuery = "SELECT * FROM bands WHERE id = :bandId";
        Band band = con.createQuery(bandQuery)
          .addParameter("bandId", bandId)
          .executeAndFetchFirst(Band.class);
          bands.add(band);
        }
      return bands;
    }
  }
}
