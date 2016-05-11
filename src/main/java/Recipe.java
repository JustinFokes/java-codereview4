import java.util.List;
import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Recipe {
  private int id;
  private String name;
  private String ingredients;
  private String instructions;

  public Recipe(String name, String ingredients, String instructions) {
    this.name = name;
    this.ingredients = ingredients;
    this.instructions = instructions;
  }

  public String getName() {
    return name;
  }

  public String getIngredients() {
    return ingredients;
  }

  public String getInstructions() {
    return  instructions;
  }

  public int getId() {
    return id;
  }

  public static List<Recipe> all() {
    String sql = "SELECT id, name FROM users";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Recipe.class);
    }
  }

  @Override
  public boolean equals(Object otherRecipe) {
    if (!(otherRecipe instanceof Recipe)) {
      return false;
    } else {
      Recipe newRecipe = (Recipe) otherRecipe;
      return this.getName().equals(newRecipe.getName()) &&
             this.getId() == newRecipe.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO recipes(name, ingredients, instructions) VALUES (:name, :ingredients, :instructions)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("ingredients", this.ingredients)
        .addParameter("instructions", this.instructions)
        .executeUpdate()
        .getKey();
    }
  }

  public static Recipe find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM recipes where id=:id";
      Recipe category = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Recipe.class);
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

  // public void update(String newRecipe) {
  //   try(Connection con = DB.sql2o.open()){
  //     String sql = "UPDATE recipes SET name = :name WHERE id = :id";
  //     con.createQuery(sql)
  //     .addParameter("name", newRecipe)
  //     .addParameter("id", this.id)
  //     .executeUpdate();
  //   }
  // }
}
