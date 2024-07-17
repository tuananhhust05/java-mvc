package com.epay.ewallet.service.post.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "employee")
public class Employee {
    
  @Id
  private String id;

  private String name;

  private String title;

  private int age;

  public Employee() {
  }

  public Employee(String id, String name, String title, int age) {
    this.id = id;
    this.name = name;
    this.title = title;
    this.age = age;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  @Override
  public String toString() {
    return "Employee [id=" + id + ", name=" + name + ", title=" + title + ", age=" + age + "]";
  }

}