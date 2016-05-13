import java.util.List;
import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Venue {
  private int id;
  private String name;

  public Venue(String user_review) {
    this.user_review = user_review;

  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }
}
