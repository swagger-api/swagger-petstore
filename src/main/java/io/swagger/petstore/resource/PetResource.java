package io.swagger.petstore.resource;

import io.swagger.petstore.data.PetData;
import io.swagger.petstore.exception.InvalidIdException;
import io.swagger.petstore.model.Pet;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@Path("/pet")
@Produces({"application/json", "application/xml"})
public class PetResource {
    static PetData petData = new PetData();

    @GET
    @Path("/{petId}")
    @Operation(summary = "Find pet by it's identifier.",
            tags = {"pets"},
            description = "Returns a pet when 0 < ID <= 10.  ID > 10 or non-integers will simulate API error conditions.",
            security = {
                    @SecurityRequirement(name = "petstore_auth", scopes = {"write:pets", "read:pets"}),
                    @SecurityRequirement(name = "api_key", scopes = {})
            },
            responses = {
                    @ApiResponse(description = "The pet", content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Pet.class, description = "A Pet in JSON format")
                            ),
                            @Content(
                                    mediaType = "application/xml",
                                    schema = @Schema(implementation = Pet.class, description = "A Pet in XML format")
                            )
                    }), @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
                    @ApiResponse(responseCode = "404", description = "Pet not found")
            })
    public Response getPetById(
            @Parameter(
                    description = "ID of pet that needs to be fetched",
                    schema = @Schema(
                            type = "integer",
                            format = "int64",
                            description = "param ID of pet that needs to be fetched",
                            exclusiveMinimumValue = 1,
                            exclusiveMaximumValue = 10
                    ),
                    required = true)
            @PathParam("petId") Long petId) throws io.swagger.petstore.exception.NotFoundException, InvalidIdException {

        if (petId == null || petId < 1 || petId > 10) {
            throw new io.swagger.petstore.exception.InvalidIdException("Invalid ID supplied");
        }
        Pet pet = petData.getPetById(petId);
        if (null != pet) {
            return Response.ok().entity(pet).build();
        } else {
            throw new io.swagger.petstore.exception.NotFoundException("Pet not found");
        }
    }

    @POST
    @Operation(summary = "Add a new pet to the store.", description = "Add a new pet to the store.",
            tags = {"pet"},
            security = {
                    @SecurityRequirement(name = "petstore_auth", scopes = {"write:pets", "read:pets"})
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            useReturnTypeSchema = true,
                            content = {
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(
                                                    accessMode = Schema.AccessMode.READ_ONLY,
                                                    description = "A Pet in XML Format"
                                            )
                                    ),
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    accessMode = Schema.AccessMode.READ_ONLY,
                                                    description = "A Pet in JSON Format"
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(responseCode = "405", description = "Invalid input")})
    @Consumes({"application/json", "application/xml"})
    public Pet addPet(
            @RequestBody(
                    description = "Create a new pet in the store",
                    required = true,
                    useParameterTypeSchema = true,
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            accessMode = Schema.AccessMode.WRITE_ONLY,
                                            description = "A Pet in JSON Format",
                                            requiredProperties = {"id"}
                                    )
                            ),
                            @Content(
                                    mediaType = "application/xml",
                                    schema = @Schema(
                                            accessMode = Schema.AccessMode.WRITE_ONLY,
                                            description = "A Pet in XML Format",
                                            requiredProperties = {"id"}
                                    )
                            )
                    }
            ) Pet pet) {
        petData.addPet(pet);
        return pet;
    }

    @PUT
    @Operation(summary = "Update an existing pet.", description = "Update an existing pet by Id.",
            tags = {"pet"},
            security = {
                    @SecurityRequirement(name = "petstore_auth", scopes = {"write:pets", "read:pets"})
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            useReturnTypeSchema = true,
                            content = {
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(
                                                    accessMode = Schema.AccessMode.READ_ONLY,
                                                    description = "A Pet in XML Format"
                                            )
                                    ),
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    accessMode = Schema.AccessMode.READ_ONLY,
                                                    description = "A Pet in JSON Format"
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
                    @ApiResponse(responseCode = "404", description = "Pet not found"),
                    @ApiResponse(responseCode = "405", description = "Validation exception")})
    @Consumes({"application/json", "application/xml"})
    public Pet updatePet(
            @RequestBody(
                    description = "Pet object that needs to be updated in the store",
                    required = true,
                    useParameterTypeSchema = true,
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            accessMode = Schema.AccessMode.WRITE_ONLY,
                                            description = "A Pet in JSON Format",
                                            requiredProperties = {"id"}
                                    )
                            ),
                            @Content(
                                    mediaType = "application/xml",
                                    schema = @Schema(
                                            accessMode = Schema.AccessMode.WRITE_ONLY,
                                            description = "A Pet in XML Format",
                                            requiredProperties = {"id"}
                                    )
                            )
                    }
            ) Pet pet) {
        if (pet == null || pet.getId() == null) {
            throw new InvalidIdException("invalid pet ID");
        }
        try {
            petData.addPet(pet);
        } catch (Exception e) {
            System.err.println("ERROR " + e.getMessage());
            e.printStackTrace();
            throw new WebApplicationException("Internal error handling request", e);
        }
        return pet;
    }

/*  @GET
  @Path("/findByStatus")
  @Operation(summary = "Finds Pets by status",
          tags = {"pets"},
    description = "Multiple status values can be provided with comma seperated strings",
          responses = {
                  @ApiResponse(
                          content = @Content(mediaType = "application/json",
                                  schema = @Schema(implementation = Pet.class))),
                  @ApiResponse(
                          responseCode = "400", description = "Invalid status value"
                  )}
          )
  public Response findPetsByStatus(
      @Parameter(
              description = "Status values that need to be considered for filter",
              required = true,
              schema = @Schema(
                      allowableValues =  {"available","pending","sold"},
                      defaultValue = "available"
              )
      )
      @QueryParam("status") String status,
      @BeanParam QueryResultBean qr
){
    return Response.ok(petData.findPetByStatus(status)).build();
  }

  @GET
  @Path("/findByTags")
  @Operation(summary = "Finds Pets by tags",
          tags = {"pets"},
    description = "Muliple tags can be provided with comma seperated strings. Use tag1, tag2, tag3 for testing.",
    responses = {
            @ApiResponse(description = "Pets matching criteria",
              content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = Pet.class))
            ),
            @ApiResponse(description = "Invalid tag value", responseCode = "400")
    })
  @Deprecated
  public Response findPetsByTags(
      @Parameter(description = "Tags to filter by", required = true) @QueryParam("tags") String tags) {
    return Response.ok(petData.findPetByTags(tags)).build();
  }*/
}
