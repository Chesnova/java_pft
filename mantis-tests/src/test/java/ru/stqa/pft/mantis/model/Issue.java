package ru.stqa.pft.mantis.model;

public class Issue {
  private int id;
  private String summary;
  private String status;
  private String resolution;
  private String description;
  private Project project;

  public String getStatus() {
    return status;
  }

  public Issue  setStatus(String status) {
    this.status = status;
    return this;
  }

  public String getResolution() {
    return resolution;
  }

  public Issue  withResolution(String resolution) {
    this.resolution = resolution;
    return this;
  }



  public int getId() {
    return id;
  }

  public Issue withId(int id) {
    this.id = id;
    return this;
  }

  public String getSummary() {
    return summary;
  }

  public Issue withSummary(String summary) {
    this.summary = summary;
    return this;
 }

  public String getDescription() {
    return description;
  }

  public Issue withDescription(String description) {
    this.description = description;
    return this;
  }

  public Project getProject() {
    return project;
  }

  public Issue withProject(Project project) {
    this.project = project;
    return this;
  }
  @Override
  public String toString() { //выводит отчете ид теста, название, статус открыта/закрыта/решена/, Решение, описание
    return "Issue{" +
            "id=" + id +
            ", summary='" + summary + '\'' +
            ", status='" + status + '\'' +
            ", resolution='" + resolution + '\'' +
            ", description='" + description + '\'' +
            '}';
  }
}
