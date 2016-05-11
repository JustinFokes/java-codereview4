import java.util.List;
import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Review {
  private int id;
  private String user_review;

  public Review(String user_review) {
    this.user_review = user_review;

  }

  public String getReview() {
    return user_review;
  }

  public int getId() {
    return id;
  }

  public static List<Review> all() {
    String sql = "SELECT id, user_review FROM reviews";
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
      return this.getReview().equals(newReview.getReview()) &&
             this.getId() == newReview.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO reviews(user_review) VALUES (:user_review)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("user_review", this.user_review)
        .executeUpdate()
        .getKey();
    }
  }

  public static Review find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM reviews WHERE id=:id";
      Review review = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Review.class);
      return review;
    }
  }

  public void addRecipe(Recipe recipe) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO recipes_reviews (recipe_id, review_id) VALUES (:recipe_id, :review_id)";
      con.createQuery(sql)
      .addParameter("recipe_id", recipe.getId())
      .addParameter("review_id", this.getId())
      .executeUpdate();
    }
  }

  public void addUser(User user) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO users_reviews (user_id, review_id) VALUES (:user_id, :review_id)";
      con.createQuery(sql)
      .addParameter("review_id", this.getId())
      .addParameter("user_id", user.getId())
      .executeUpdate();
    }
  }

  public List<Recipe> getRecipes() {
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "SELECT recipe_id FROM recipes_reviews WHERE review_id = :review_id";
      List<Integer> recipeIds = con.createQuery(joinQuery)
        .addParameter("review_id", this.getId())
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


  public List<User> getUser() {
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "SELECT user_id FROM users_reviews WHERE review_id = :review_id";
      List<Integer> userIds = con.createQuery(joinQuery)
        .addParameter("review_id", this.getId())
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
    String deleteQuery = "DELETE FROM reviews WHERE id = :id";
      con.createQuery(deleteQuery)
        .addParameter("id", this.getId())
        .executeUpdate();

    String joinDeleteQuery = "DELETE FROM users_reviews WHERE review_id = :review_id";
      con.createQuery(joinDeleteQuery)
        .addParameter("review_id", this.getId())
        .executeUpdate();

    String joinDeleteQueryTwo = "DELETE FROM recipes_reviews WHERE review_id = :review_id";
      con.createQuery(joinDeleteQueryTwo)
      .addParameter("review_id", this.getId())
      .executeUpdate();
    }
  }

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
