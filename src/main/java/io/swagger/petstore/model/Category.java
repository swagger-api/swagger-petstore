package io.swagger.petstore.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Schema(
        description = "Category",
        $id = "/components/schemas/category"
)
@XmlRootElement(name = "Category")
public class Category {
  private long id;
  private String name;

  @XmlElement(name = "id")
  @Schema(example = "1")
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @XmlElement(name = "name")
  @Schema(example = "Dogs")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
