import java.util.List;
import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class User {
  private int id;
  private String name;

  public User(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public static List<User> all() {
    String sql = "SELECT id, name FROM users";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(User.class);
    }
  }

  @Override
  public boolean equals(Object otherUser) {
    if (!(otherUser instanceof User)) {
      return false;
    } else {
      User newUser = (User) otherUser;
      return this.getName().equals(newUser.getName()) &&
             this.getId() == newUser.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO users(name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static User find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users where id=:id";
      User category = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(User.class);
      return category;
    }
  }

  // public void addRecipe(Recipe recipe) {
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

  public void update(String newUser) {
    try(Connection con = DB.sql2o.open()){
      String sql = "UPDATE users SET name = :name WHERE id = :id";
      con.createQuery(sql)
      .addParameter("name", newUser)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }
}
