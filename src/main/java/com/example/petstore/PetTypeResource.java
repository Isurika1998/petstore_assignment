package com.example.petstore;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/v1/petTypes")
@Produces("application/json")
public class PetTypeResource {
    List<PetType> petTypes = new ArrayList<PetType>();

    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "All Pet Types", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "PetType"))) })
    @GET
    public Response getPetTypes() {

        PetType petType1 = new PetType();
        petType1.setPetTypeId(1);
        petType1.setPetTypeName("Cat");

        PetType petType2 = new PetType();
        petType2.setPetTypeId(2);
        petType2.setPetTypeName("Dog");

        PetType petType3 = new PetType();
        petType3.setPetTypeId(3);
        petType3.setPetTypeName("Bird");

        petTypes.add(petType1);
        petTypes.add(petType2);
        petTypes.add(petType3);
        return Response.ok(petTypes).build();
    }

    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "PetType for id", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "PetType"))),
            @APIResponse(responseCode = "404", description = "No PetType found for the id.") })
    @GET
    @Path("{petTypeId}")
    public Response getPet(@PathParam("petTypeId") int petTypeId) {
        if (petTypeId < 0) {
            return Response.status(Status.NOT_FOUND).build();
        }
        PetType petType = new PetType();
        for (PetType type: petTypes) {
            if(type.getPetTypeId() == petTypeId){
                petType = type;
            }
        }

        return Response.ok(petType).build();

    }

    @APIResponses(value = {@APIResponse(responseCode = "200", description = "Successfully added pet type")})
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPetTypes(PetType newPetType) {
        petTypes.add(newPetType);
        return Response.ok(petTypes).build();
    }

    @APIResponses(value = {@APIResponse(responseCode = "200", description = "Successfully updated pet type")})
    @PUT
    @Path("{petTypeId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePetTypes(@PathParam("petTypeId") int petTypeId, PetType changedPetType) {
        int i=0;
        for (PetType type: petTypes) {
            if(type.getPetTypeId() == petTypeId){
                petTypes.set(i,changedPetType);
            }
            i++;
        }
        return Response.ok(petTypes).build();
    }


}
