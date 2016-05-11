import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/food_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteUsersQuery = "DELETE FROM users *;";
      String deleteCategoriesQuery = "DELETE FROM categories *;";
      String deleteRecipesQuery = "DELETE FROM recipes *;";
      String deleteReviewsQuery = "DELETE FROM reviews *;";
      //String deleteCategoriesTasksQuery = "DELETE FROM recipes_users *;";
      //String deleteCategoriesTasksQuery = "DELETE FROM reviews_users *;";
      //String deleteCategoriesTasksQuery = "DELETE FROM categories_recipes *;";


      con.createQuery(deleteUsersQuery).executeUpdate();
      con.createQuery(deleteCategoriesQuery).executeUpdate();
      con.createQuery(deleteRecipesQuery).executeUpdate();
      con.createQuery(deleteReviewsQuery).executeUpdate();
      // con.createQuery(deleteRecipesUsersQuery).executeUpdate();
      // con.createQuery(deleteReviewsUsersQuery).executeUpdate();
      // con.createQuery(deleteCategoriesRecipesQuery).executeUpdate();
    }
  }
}
