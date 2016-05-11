import java.util.List;
import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Review {
  private int id;
  private String userReview;

  public Review(String userReview) {
    this.userReview = userReview;

  }

  public String getReview() {
    return userReview;
  }

  public int getId() {
    return id;
  }

  public static List<Review> all() {
    String sql = "SELECT id, name FROM reviews";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Review.class);
    }
  }

  @Override
  public boolean equals(Object otherReview) {
    if (!(otherReview instanceof Review)) {
      return false;
    } else {
      Review newReview = (Review) otherReview;
      return this.getName().equals(newReview.getName()) &&
             this.getId() == newReview.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO reviews(userReview) VALUES (:userReview)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Review find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users where id=:id";
      Review review = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Review.class);
      return review;
    }
  }

  // public void addReview(Review recipe) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "INSERT INTO categories_tasks (category_id, task_id) VALUES (:category_id, :task_id)";
  //     con.createQuery(sql)
  //     .addParameter("category_id", this.getId())
  //     .addParameter("task_id", task.getId())
  //     .executeUpdate();
  //   }
  // }

  // public List<Task> getTasks() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String joinQuery = "SELECT task_id FROM categories_tasks WHERE category_id = :category_id";
  //     List<Integer> taskIds = con.createQuery(joinQuery)
  //       .addParameter("category_id", this.getId())
  //       .executeAndFetch(Integer.class);
  //
  //     List<Task> tasks = new ArrayList<Task>();
  //
  //     for (Integer taskId : taskIds) {
  //       String taskQuery = "SELECT * FROM tasks WHERE id = :taskId";
  //       Task task = con.createQuery(taskQuery)
  //         .addParameter("taskId", taskId)
  //         .executeAndFetchFirst(Task.class);
  //       tasks.add(task);
  //     }
  //     return tasks;
  //   }
  // }

  // public void delete() {
  // try(Connection con = DB.sql2o.open()) {
  //   String deleteQuery = "DELETE FROM users WHERE id = :id;";
  //     con.createQuery(deleteQuery)
  //       .addParameter("id", this.getId())
  //       .executeUpdate();
  //
  //   String joinDeleteQuery = "DELETE FROM categories_tasks WHERE category_id = :category_id";
  //     con.createQuery(joinDeleteQuery)
  //       .addParameter("category_id", this.getId())
  //       .executeUpdate();
  //   }
  // }

  public void update(String newReview) {
    try(Connection con = DB.sql2o.open()){
      String sql = "UPDATE reviews SET userReview = :name WHERE id = :id";
      con.createQuery(sql)
      .addParameter("userReview", newReview)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }
}
