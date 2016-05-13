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
      model.put("bands", Band.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
     
    get("/bands/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/band_form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/venues/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/venue_form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/band/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String bandName = request.queryParams("name");
      Band newBand = new Band(bandName);
      newBand.save();
      model.put("band", newBand);
      model.put("bands", Band.all());
      response.redirect("/bands/" + newBand.getId());
      return null;
      });

    post("/venue/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String venueName = request.queryParams("name");
      Venue newVenue = new Venue(venueName);
      newVenue.save();
      model.put("venue", newVenue);
      model.put("venues", Venue.all());
      response.redirect("/venues/" + newVenue.getId());
      return null;
      });

    get("/bands", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/bands.vtl");
      model.put("bands", Band.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/venues", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/venues.vtl");
      model.put("venues", Venue.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/band/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Band newBand = Band.find(Integer.parseInt(request.params(":id")));
      model.put("thisBand", newBand);
      model.put("bands", Band.all());
      model.put("template", "templates/band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/venue/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/venues.vtl");
      model.put("venues", Venue.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());



  }
}
