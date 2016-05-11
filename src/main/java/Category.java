import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Category {
  private int id;
  private String description;

  public Category(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public int getId() {
    return id;
  }

  public static List<Category> all() {
    String sql = "SELECT id, description FROM categories";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Category.class);
    }
  }

  @Override
  public boolean equals(Object otherCategory){
    if (!(otherCategory instanceof Category)) {
      return false;
    } else {
      Category newCategory = (Category) otherCategory;
      return this.getDescription().equals(newCategory.getDescription()) &&
             this.getId() == newCategory.getId();
    }
  }


  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO categories(description) VALUES (:description)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("description", this.description)
        .executeUpdate()
        .getKey();
    }
  }

  public static Category find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM categories where id=:id";
      Category categories = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Category.class);
      return categories;
    }
  }
//
//   public void update(String newDescription) {
//   try(Connection con = DB.sql2o.open()) {
//     String sql = "UPDATE tasks SET description = :description WHERE id = :id";
//     con.createQuery(sql)
//       .addParameter("description", newDescription)
//       .addParameter("id", this.id)
//       .executeUpdate();
//     }
//   }
//
//   public void addCategory(Category category) {
//     try(Connection con = DB.sql2o.open()) {
//       String sql = "INSERT INTO categories_tasks (category_id, task_id) VALUES (:category_id, :task_id)";
//       con.createQuery(sql)
//       .addParameter("category_id", category.getId())
//       .addParameter("task_id", this.getId())
//       .executeUpdate();
//     }
//   }
//
//   public List<Category> getCategory() {
//   try(Connection con = DB.sql2o.open()){
//     String joinQuery = "SELECT category_id FROM categories_tasks WHERE task_id = :task_id";
//     List<Integer> categoryIds = con.createQuery(joinQuery)
//       .addParameter("task_id", this.getId())
//       .executeAndFetch(Integer.class);
//
//     List<Category> categories = new ArrayList<Category>();
//
//     for (Integer categoryId : categoryIds) {
//       String taskQuery = "SELECT * FROM categories WHERE id = :categoryId";
//       Category category = con.createQuery(taskQuery)
//         .addParameter("categoryId", categoryId)
//         .executeAndFetchFirst(Category.class);
//       categories.add(category);
//     }
//     return categories;
//     }
//   }
//
//   public void delete() {
//     try(Connection con = DB.sql2o.open()) {
//     String deleteQuery = "DELETE FROM tasks WHERE id = :id;";
//     con.createQuery(deleteQuery)
//       .addParameter("id", this.id)
//       .executeUpdate();
//
//     String joinDeleteQuery = "DELETE FROM categories_tasks WHERE task_id = :taskId";
//       con.createQuery(joinDeleteQuery)
//         .addParameter("taskId", this.getId())
//         .executeUpdate();
//     }
//  }
}
