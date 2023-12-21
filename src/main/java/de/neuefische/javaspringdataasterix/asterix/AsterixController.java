package de.neuefische.javaspringdataasterix.asterix;

import org.springframework.data.mongodb.core.MongoTemplate;
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

    @GetMapping("/api/asterix/characters")
    public List<Character> getAllCharacters() {
        return characterRepository.findAll();
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

    @GetMapping("/api/asterix/characters/average-age")
    public Double getAverageAge(@RequestParam(required = false) String profession) {
        return characterRepository.findAll()
                .stream()
                .filter(character -> profession == null || profession.equals(character.profession()))
                .mapToDouble(Character::age)
                .average()
                .orElse(0);
    }
}
