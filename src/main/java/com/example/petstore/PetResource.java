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

@Path("/v1/pets")
@Produces("application/json")
public class PetResource {
	public List<Pet> pets = new ArrayList<Pet>();

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "All Pets", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@GET
	public Response getPets() {

		Pet pet1 = new Pet();
		pet1.setPetId(1);
		pet1.setPetAge(3);
		pet1.setPetName("Boola");
		pet1.setPetType("Dog");

		Pet pet2 = new Pet();
		pet2.setPetId(2);
		pet2.setPetAge(4);
		pet2.setPetName("Sudda");
		pet2.setPetType("Cat");

		Pet pet3 = new Pet();
		pet3.setPetId(3);
		pet3.setPetAge(2);
		pet3.setPetName("Peththappu");
		pet3.setPetType("Bird");

		pets.add(pet1);
		pets.add(pet2);
		pets.add(pet3);
		return Response.ok(pets).build();
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet for id", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the id.") })
	@GET
	@Path("{petId}")
	public Response getPet(@PathParam("petId") int petId) {
		if (petId < 0) {
			return Response.status(Status.NOT_FOUND).build();
		}

		Pet pet = new Pet();
		for (Pet p: pets) {
			if(p.getPetId() == petId){
				pet = p;
			}
		}
		return Response.ok(pet).build();
		
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet for name", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for given name.") })
	@GET
	@Path("/name/{petName}")
	public Response getPetByName(@PathParam("petName") String petName) {

		Pet pet = new Pet();
		for (Pet p: pets) {
			if(p.getPetName() == petName){
				pet = p;
			}
		}
		return Response.ok(pet).build();

	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet for age", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for given age.") })
	@GET
	@Path("/name/{petAge}")
	public Response getPetByAge(@PathParam("petAge") int petAge) {

		Pet pet = new Pet();
		for (Pet p: pets) {
			if(p.getPetAge() == petAge){
				pet = p;
			}
		}
		return Response.ok(pet).build();

	}

	@APIResponses(value = {@APIResponse(responseCode = "200", description = "Successfully added pet")})
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addPet(Pet newPet) {
		pets.add(newPet);
		return Response.ok(pets).build();
	}

	@APIResponses(value = {@APIResponse(responseCode = "200", description = "Successfully updated pet")})
	@PUT
	@Path("{petId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatePet(@PathParam("petId") int petId, Pet changedPet) {
		int i=0;
		for (Pet pet: pets) {
			if(pet.getPetId() == petId){
				pets.set(i,changedPet);
			}
			i++;
		}
		return Response.ok(pets).build();
	}
}
