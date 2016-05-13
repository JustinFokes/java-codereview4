import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import static org.fluentlenium.core.filter.FilterConstructor.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();


  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Band Tracker Application");
  }

  @Test
  public void bandIsCreatedTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Add Bands"));
    fill("#name").with("Fugazi");
    fill("#genre").with("Punk");
    fill("#homeTown").with("Washington DC");
    submit(".button-primary");
    assertThat(pageSource()).contains("Add a Venue To This Band");
  }

  @Test
  public void venueIsCreatedTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Add Venues"));
    fill("#name").with("This");
    fill("#phone").with("111-222-3333");
    fill("#location").with("Portland");
    submit(".button-primary");
    assertThat(pageSource()).contains("Add a Band To This Venue");
  }

  @Test
  public void venueIsAddedToBand() {
    goTo("http://localhost:4567/");
    click("a", withText("Add Bands"));
    fill("#name").with("Fugazi");
    fill("#genre").with("Punk");
    fill("#homeTown").with("Washington DC");
    submit(".button-primary");
    click("a", withText("Home"));
    click("a", withText("Add Venues"));
    fill("#name").with("This");
    fill("#phone").with("111-222-3333");
    fill("#location").with("Portland");
    submit(".button-primary");
    click("a", withText("Home"));
    click("a", withText("View Venues"));
    click("a", withText("This"));
    fillSelect("addedBand").withText("Fugazi");
    click("a", withText("Add Band"));
    assertThat(pageSource()).contains("Fugazi");

  }
}