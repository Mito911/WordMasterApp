package pl.maciejtuznik.WordMasterApp.word;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.maciejtuznik.WordMasterApp.categories.Categories;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/words")
public class WordsController {

    @Autowired
    WordsService wordsService;

    @GetMapping
    public ResponseEntity<List<Words>> getAllWords() {
        List<Words> allWords = wordsService.findAllWords();
        return ResponseEntity.ok(allWords);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Words>> findWords(@PathVariable Integer id) {
        Optional<Words> words = wordsService.findWords(id);
        return ResponseEntity.ok(words);
    }

    @PostMapping
    public ResponseEntity<Words> createWord(@RequestBody Words word, @RequestParam Integer categoryId) {
        Optional<Categories> category = wordsService.findCategoryById(categoryId);

        if (category.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Words savedWord = wordsService.saveWords(word, categoryId);
        return ResponseEntity.ok(savedWord);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Words> partiallyUpdateWords(@PathVariable Integer id, @RequestBody Words words) {
        return wordsService.partiallyUpdateWords(id, words)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWord(@PathVariable Integer id) {
        boolean deleted = wordsService.deleteWords(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Words>> getWordsByCategory(@PathVariable Integer categoryId) {
        List<Words> wordsByCategory = wordsService.findWordsByCategory(categoryId);
        return ResponseEntity.ok(wordsByCategory);
    }
}

