package de.neuefische.javaspringdataasterix.asterix;

import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class AsterixController {

    private final CharacterRepository characterRepository;

    public AsterixController(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @GetMapping("/api/asterix/characters1")
    public List<Character> getAllCharactersWithStream(@RequestParam(required = false) String name,
                                            @RequestParam(required = false) Integer age,
                                            @RequestParam(required = false) String profession) {

        return characterRepository.findAll().stream()
                .filter(character -> name == null || character.name().equals(name))
                .filter(character -> age == null || character.age().equals(age))
                .filter(character -> profession == null || character.profession().equals(profession))
                .toList();
    }

    @GetMapping("/api/asterix/characters2")
    public List<Character> getAllCharactersWithRepository(@RequestParam(required = false) String name,
                                            @RequestParam(required = false) Integer age,
                                            @RequestParam(required = false) String profession) {
        if (name == null && age == null && profession == null) {
            return characterRepository.findAll();
        }
        else if (name != null && age == null && profession == null) {
            return characterRepository.findCharactersByName(name);
        }
        else if (name == null && age != null && profession == null) {
            return characterRepository.findCharactersByAge(age);
        }
        else if (name != null && age != null && profession == null) {
            return characterRepository.findCharactersByNameAndAge(name, age);
        }
        else if (name == null && age == null) {
            return characterRepository.findCharactersByProfession(profession);
        }
        else if (name != null && age == null) {
            return characterRepository.findCharactersByNameAndProfession(name, profession);
        }
        else if (name == null) {
            return characterRepository.findCharactersByAgeAndProfession(age, profession);
        }
        else {
            return characterRepository.findCharactersByNameAndAgeAndProfession(name, age, profession);
        }
    }

    @GetMapping("/api/asterix/characters3")
    public List<Character> getAllCharactersWithExample(@RequestParam(required = false) String name,
                                            @RequestParam(required = false) Integer age,
                                            @RequestParam(required = false) String profession) {

        return characterRepository.findAll(Example.of(new Character(null, name, age, profession)));
    }

    @PostMapping("/api/asterix/characters")
    @ResponseStatus(HttpStatus.CREATED)
    public Character postCharacter(@RequestBody Character character) {
        return characterRepository.save(character);
    }

    @PutMapping("/api/asterix/characters/{id}")
    public Character putCharacter(@PathVariable String id, @RequestBody Character character) {
        if (characterRepository.existsById(id)) {
            return characterRepository.save(character);
        }
        throw new NoSuchElementException("Cant update Character with id: " + id + ", Character does not exist.");
    }

    @DeleteMapping("/api/asterix/characters/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCharacter(@PathVariable String id) {
        if (characterRepository.existsById(id)) {
            characterRepository.deleteById(id);
        }
        throw new NoSuchElementException("Character with id: " + id + " does not exist.");
    }
}
