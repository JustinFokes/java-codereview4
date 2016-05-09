import org.sql2o.*;
import org.junit.*;
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.junit.Assert.*;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Get Off The Couch!");
  }

  @Test
  public void categoryIsCreatedTest() {
    goTo("http://localhost:4567/");
    click("a", withText("View Category List"));
    fill("#newCategory").with("Household chores");
    submit(".btn");
    assertThat(pageSource()).contains("Household chores");
  }

  @Test
  public void taskIsCreatedTest() {
    goTo("http://localhost:4567/");
    click("a", withText("View Tasks List"));
    fill("#description").with("Mow the lawn");
    submit(".btn");
    assertThat(pageSource()).contains("Mow the lawn");
  }

  @Test
  public void categoryShowPageDisplaysName() {
    Category testCategory = new Category("Household chores");
    testCategory.save();
    String url = String.format("http://localhost:4567/categories/%d", testCategory.getId());
    goTo(url);
    assertThat(pageSource()).contains("Household chores");
  }

  @Test
  public void taskShowPageDisplaysDescription() {
    Task testTask = new Task("Mow the lawn");
    testTask.save();
    String url = String.format("http://localhost:4567/tasks/%d", testTask.getId());
    goTo(url);
    assertThat(pageSource()).contains("Mow the lawn");
  }

  @Test
  public void taskIsAddedToCategory() {
    Category testCategory = new Category("Household chores");
    testCategory.save();
    Task testTask = new Task("Mow the lawn");
    testTask.save();
    String url = String.format("http://localhost:4567/categories/%d", testCategory.getId());
    goTo(url);
    fillSelect("#task_id").withText("Mow the lawn");
    submit(".btn");
    assertThat(pageSource()).contains("Mow the lawn");
  }

  @Test
  public void categoryIsAddedToTask() {
    Category testCategory = new Category("Household chores");
    testCategory.save();
    Task testTask = new Task("Mow the lawn");
    testTask.save();
    String url = String.format("http://localhost:4567/tasks/%d", testTask.getId());
    goTo(url);
    fillSelect("#category_id").withText("Household chores");
    submit(".btn");
    assertThat(pageSource()).contains("Household chores");
  }

  @Test
  public void categoryIsDeleted() {
    Category testCategory = new Category("Household chores");
    testCategory.save();
    String url = String.format("http://localhost:4567/categories/%d", testCategory.getId());
    goTo(url);
    click("a", withText("Delete this category"));
    assertThat(pageSource()).contains("Success! Your chore is no more.");
  }

  @Test
  public void taskIsDeleted() {
    Task newTask = new Task("Mow the lawn");
    newTask.save();
    String url = String.format("http://localhost:4567/tasks/%d", newTask.getId());
    goTo(url);
    click("a", withText("Delete this Task"));
    assertThat(pageSource()).contains("Success! Your chore is no more.");
  }

  @Test
  public void taskIsUpdated() {
    Task newTask = new Task("Mow the lawn");
    newTask.save();
    String url = String.format("http://localhost:4567/tasks/%d", newTask.getId());
    goTo(url);
    click("a", withText("Update this task"))
    assertThat(pageSource()).contains("Success! Your task/category has been updated.");

  }

  // @Test
  // public void categoryIsUpdated() {
  //
  // }
}
