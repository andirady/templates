package com.github.andirady.testing.javatotypescript;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Stream;

import com.github.andirady.testing.javatotypescript.dto.PersonDto;

public class ListBackedPersonRepository extends PersonRepository {

    private Set<PersonDto> people = new CopyOnWriteArraySet<>();

    public boolean addPerson(PersonDto person) {
        var maxId = people.stream().mapToInt(PersonDto::getId).max().orElse(0);
        var copy = new PersonDto();
        copy.setId(maxId + 1);
        copy.setName(person.getName());
        copy.setAge(person.getAge());
        var rc = people.add(copy);
        if (rc) {
            person.setId(copy.getId());
        }

        return rc;
    }

    public Stream<PersonDto> streamPeople(String nameFilter) {
        var stream = people.stream();
        if (nameFilter != null && !nameFilter.isBlank()) {
            stream.filter(p -> p.getName().startsWith(nameFilter));
        }
        return stream;
    }

    public Optional<PersonDto> findPersonById(int id) {
        if (id < 1) {
            return Optional.empty();
        }
        return people.stream().filter(p -> p.getId() == id).findFirst();
    }
}
