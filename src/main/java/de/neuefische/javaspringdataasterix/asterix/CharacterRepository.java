package de.neuefische.javaspringdataasterix.asterix;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CharacterRepository extends MongoRepository<Character, String> {

    List<Character> findCharactersByNameAndAgeAndProfession(String name, Integer age, String profession);
    List<Character> findCharactersByNameAndAge(String name, Integer age);
    List<Character> findCharactersByNameAndProfession(String name, String profession);
    List<Character> findCharactersByAgeAndProfession(Integer age, String profession);
    List<Character> findCharactersByAge(Integer age);
    List<Character> findCharactersByName(String name);
    List<Character> findCharactersByProfession(String profession);
}
