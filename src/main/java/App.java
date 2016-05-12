import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/users/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/sign_up.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/user/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String userName = request.queryParams("name");
      User newUser = new User(userName);
      newUser.save();
      model.put("users", User.all());
      response.redirect("/user/" + newUser.getId());
      return null;
      });

    get("/user/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      User newUser = User.find(Integer.parseInt(request.params(":id")));
      model.put("thisUser", newUser);
      model.put("users", User.all());
      model.put("recipe", Recipe.all());
      model.put("template", "templates/user_home.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/users", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("users", User.all());
      model.put("template", "templates/users.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/user/:id/recipes/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      User newUser = User.find(Integer.parseInt(request.params(":id")));
      model.put("user", newUser);
      model.put("template", "templates/recipe_form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/user/:id/recipes", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      User newUser = User.find(Integer.parseInt(request.params(":id")));

      String recipeName = request.queryParams("name");
      String recipeIngredients = request.queryParams("ingredients");
      String recipeInstructions = request.queryParams("instructions");
      String recipeCategory = request.queryParams("categories");
      Recipe newRecipe = new Recipe(recipeName, recipeIngredients, recipeInstructions, recipeCategory);
      newRecipe.save();
      newUser.addRecipe(newRecipe);
      model.put("recipe", newRecipe);
      model.put("user", newUser);
      model.put("users", User.all());
      model.put("recipes", Recipe.all());
      response.redirect("/user/" + newUser.getId());
      return null;
      });

    get("/recipe/:rId", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Recipe recipe = Recipe.find(Integer.parseInt(request.params(":rId")));
      model.put("recipe", recipe);
      model.put("template", "templates/recipe.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
