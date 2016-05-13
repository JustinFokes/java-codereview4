import java.util.List;
import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Band {
  private int id;
  private String name;
  private String genre;
  private String homeTown;

  public Band(String name, String genre, String homeTown) {
    this.name = name;
    this.genre = genre;
    this.homeTown = homeTown;
  }

  public String getName() {
    return name;
  }

  public String getGenre() {
    return genre;
  }

  public String getHomeTown() {
    return homeTown;
  }

  public int getId() {
    return id;
  }

  public static List<Band> all() {
    String sql = "SELECT * FROM bands";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Band.class);
    }
  }

  @Override
  public boolean equals(Object otherBand) {
    if (!(otherBand instanceof Band)) {
      return false;
    } else {
      Band newBand = (Band) otherBand;
      return this.getName().equals(newBand.getName()) &&
             this.getId() == newBand.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands(name, genre, homeTown) VALUES (:name, :genre, :homeTown)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .addParameter("genre", this.genre)
      .addParameter("homeTown", this.homeTown)
      .executeUpdate()
      .getKey();
    }
  }

  public static Band find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM bands WHERE id=:id";
      Band band = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Band.class);
      return band;
    }
  }

  public void addVenue(Venue venue) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands_venues (band_id, venue_id) VALUES (:band_id, :venue_id)";
      con.createQuery(sql)
      .addParameter("band_id", this.getId())
      .addParameter("venue_id", venue.getId())
      .executeUpdate();
    }
  }

  public List<Venue> getVenue() {
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "SELECT venue_id FROM bands_venues WHERE band_id = :band_id";
      List<Integer> venueIds = con.createQuery(joinQuery)
        .addParameter("band_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Venue> venues = new ArrayList<Venue>();

      for (Integer venueId : venueIds) {
        String venueQuery = "SELECT * FROM venues WHERE id = :venueId";
        Venue venue = con.createQuery(venueQuery)
          .addParameter("venueId", venueId)
          .executeAndFetchFirst(Venue.class);
          venues.add(venue);
        }
      return venues;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM bands WHERE id = :id";
        con.createQuery(deleteQuery)
          .addParameter("id", this.getId())
          .executeUpdate();
      }
    }
  public void update(String name, String genre, String homeTown) {
    try(Connection con = DB.sql2o.open()){
      String sql = "UPDATE bands SET name = :name WHERE id = :id";
      con.createQuery(sql)
      .addParameter("name", name)
      .addParameter("id", this.id)
      .executeUpdate();

      String sql1 = "UPDATE bands SET genre = :genre WHERE id = :id";
      con.createQuery(sql1)
      .addParameter("genre", genre)
      .addParameter("id", this.id)
      .executeUpdate();

      String sql2 = "UPDATE bands SET homeTown = :homeTown WHERE id = :id";
      con.createQuery(sql2)
      .addParameter("homeTown", homeTown)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }
}
