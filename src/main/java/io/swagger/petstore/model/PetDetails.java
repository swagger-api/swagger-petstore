package io.swagger.petstore.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "PetDetails")
@Schema(
        $schema = "https://json-schema.org/draft/2020-12/schema",
        $vocabulary = "https://spec.openapis.org/oas/3.1/schema-base",
        $id = "/api/v31/components/schemas/petdetails",
        type = "object"
)
public class PetDetails {
    private Long id;
    private Category category;
    private String name;
    private List<String> photoUrls = new ArrayList<>();
    private Tag tag = new Tag();
    private String status;

    @XmlElement(name = "id")
    @Schema(examples = {"10"}, $anchor = "pet_details_id")
    public long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Schema(description = "PetDetails Category", ref = "/api/v31/components/schemas/category")
    public Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    @Schema(ref = "/api/v31/components/schemas/tag")
    public Tag getTag() {
        return tag;
    }

    public void setTag(final Tag tag) {
        this.tag = tag;
    }

}
