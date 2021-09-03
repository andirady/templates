package com.github.andirady.testing.javatotypescript;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.github.andirady.testing.javatotypescript.dto.*;

@Path("people")
@Produces("application/json")
public class PersonResource {

    private static final Logger LOGGER = Logger.getLogger(PersonResource.class.getName());

    @GET
    @Path("/{id}")
    public PersonDto getPersonById(@PathParam("id") int id) {
        return PersonRepository.getInstance().findPersonById(id).orElse(null);
    }

    @GET
    @Path("/")
    public List<PersonDto> listPeople(@QueryParam("name_filter") String nameFilter) {
        return PersonRepository.getInstance().streamPeople(nameFilter).collect(Collectors.toList());
    }

    @POST
    @Path("/")
    @Consumes("application/json")
    public boolean addPerson(PersonDto person) {
        if (person.getId() > 0 || Objects.toString(person.getName(), "").isBlank() || person.getAge() < 1) {
            return false;
        }
        var rc = PersonRepository.getInstance().addPerson(person);
        if (rc) {
            LOGGER.info("Created new person with id " + person.getId());
        }

        return rc;
    }

}
