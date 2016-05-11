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

  public void addRecipe(Recipe recipe) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO recipes_users (recipe_id, user_id) VALUES (:recipe_id, :user_id)";
      con.createQuery(sql)
      .addParameter("recipe_id", recipe.getId())
      .addParameter("user_id", this.getId())
      .executeUpdate();
    }
  }

//was SELECT user_id, but changed to recipe_id
  public List<Recipe> getRecipe() {
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "SELECT recipe_id FROM recipes_users WHERE user_id = :user_id";
      List<Integer> recipeIds = con.createQuery(joinQuery)
        .addParameter("user_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Recipe> recipes = new ArrayList<Recipe>();

      for (Integer recipeId : recipeIds) {
        String recipeQuery = "SELECT * FROM recipes WHERE id = :recipeId";
        Recipe recipe = con.createQuery(recipeQuery)
          .addParameter("recipeId", recipeId)
          .executeAndFetchFirst(Recipe.class);
        recipes.add(recipe);
      }
      return recipes;
    }
  }

  public void addReview(Review review) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO users_reviews (user_id, review_id) VALUES (:user_id, :review_id)";
      con.createQuery(sql)
      .addParameter("user_id", this.getId())
      .addParameter("review_id", review.getId())
      .executeUpdate();
    }
  }

  public List<Review> getReview() {
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "SELECT review_id FROM users_reviews WHERE user_id = :user_id";
      List<Integer> reviewIds = con.createQuery(joinQuery)
        .addParameter("user_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Review> reviews = new ArrayList<Review>();

      for (Integer reviewId : reviewIds) {
        String reviewQuery = "SELECT * FROM reviews WHERE id = :reviewId";
        Review review = con.createQuery(reviewQuery)
          .addParameter("reviewId", reviewId)
          .executeAndFetchFirst(Review.class);
        reviews.add(review);
      }
      return reviews;
    }
  }

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
