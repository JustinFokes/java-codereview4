import java.util.List;
import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Recipe {
  private int id;
  private String name;
  private String ingredients;
  private String instructions;
  private String category;

  public Recipe(String name, String ingredients, String instructions, String category) {
    this.name = name;
    this.ingredients = ingredients;
    this.instructions = instructions;
    this.category = category;
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

  public String getCategory() {
    return category;
  }

  public int getId() {
    return id;
  }

  public static List<Recipe> all() {
    String sql = "SELECT id, name, ingredients, instructions, category FROM recipes";
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
      String sql = "INSERT INTO recipes(name, ingredients, instructions, category) VALUES (:name, :ingredients, :instructions, :category)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("ingredients", this.ingredients)
        .addParameter("instructions", this.instructions)
        .addParameter("category", this.category)
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

  public void addReview(Review review) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO recipes_reviews (recipe_id, review_id) VALUES (:recipe_id, :review_id)";
      con.createQuery(sql)
      .addParameter("recipe_id", this.getId())
      .addParameter("review_id", review.getId())
      .executeUpdate();
    }
  }

  public void addUser(User user) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO recipes_users (recipe_id, user_id) VALUES (:recipe_id, :user_id)";
      con.createQuery(sql)
      .addParameter("recipe_id", this.getId())
      .addParameter("user_id", user.getId())
      .executeUpdate();
    }
  }


  public List<Review> getReviews() {
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "SELECT review_id FROM recipes_reviews WHERE recipe_id = :recipe_id";
      List<Integer> reviewIds = con.createQuery(joinQuery)
        .addParameter("recipe_id", this.getId())
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

  public List<User> getUsers() {
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "SELECT user_id FROM recipes_users WHERE recipe_id = :recipe_id";
      List<Integer>  userIds = con.createQuery(joinQuery)
        .addParameter("recipe_id", this.getId())
        .executeAndFetch(Integer.class);

      List<User> users = new ArrayList<User>();

      for (Integer userId : userIds) {
        String userQuery = "SELECT * FROM users WHERE id = :userId";
        User user = con.createQuery(userQuery)
          .addParameter("userId", userId)
          .executeAndFetchFirst(User.class);
        users.add(user);
      }
      return users;
    }
  }

  public void delete() {
  try(Connection con = DB.sql2o.open()) {
    String deleteQuery = "DELETE FROM recipes WHERE id = :id";
      con.createQuery(deleteQuery)
        .addParameter("id", this.getId())
        .executeUpdate();

    String joinDeleteQuery = "DELETE FROM recipes_users WHERE recipe_id = :recipe_id";
      con.createQuery(joinDeleteQuery)
        .addParameter("recipe_id", this.getId())
        .executeUpdate();

    String joinDeleteQueryTwo = "DELETE FROM recipes_reviews WHERE recipe_id = :recipe_id";
      con.createQuery(joinDeleteQueryTwo)
      .addParameter("recipe_id", this.getId())
      .executeUpdate();
    }
  }
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
