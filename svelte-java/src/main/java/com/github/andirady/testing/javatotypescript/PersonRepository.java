package com.github.andirady.testing.javatotypescript;

import java.util.Optional;
import java.util.ServiceLoader;
import java.util.stream.Stream;

import com.github.andirady.testing.javatotypescript.dto.PersonDto;

public abstract class PersonRepository {

    private static PersonRepository instance;

    public static synchronized PersonRepository getInstance() {
        if (instance == null) {
            instance = ServiceLoader.load(PersonRepository.class).findFirst().orElseThrow(
                    () -> new IllegalStateException("No service provider for " + PersonRepository.class.getName()));
        }

        return instance;
    }

    public abstract boolean addPerson(PersonDto person);

    public abstract Stream<PersonDto> streamPeople(String nameFilter);

    public abstract Optional<PersonDto> findPersonById(int id);
}
