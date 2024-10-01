package pl.maciejtuznik.WordMasterApp.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    CategoriesService categoriesService;

    @GetMapping
    public ResponseEntity<Iterable<Categories>> getAllCategories() {
        Iterable<Categories> allCategories = categoriesService.findAllCategories();
        System.out.println("Fetched categories: " + allCategories);
        return ResponseEntity.ok(allCategories);
    }


    @PostMapping
    public ResponseEntity<Categories> createCategories(@RequestBody Categories categories) {
        if (categories.getName() == null || categories.getName().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        Categories savedCategory = categoriesService.saveCategories(categories);
        return ResponseEntity.ok(savedCategory);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategories(@PathVariable Integer id) {
        boolean deleted = categoriesService.deleteCategories(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Categories>> findCategories(@PathVariable Integer id) {
        Optional<Categories> categories = categoriesService.findCategoriesById(id);
        return ResponseEntity.ok(categories);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Categories> partiallyUpdateCategories(@PathVariable Integer id, @RequestBody Categories categories) {
        return categoriesService.pratiallyUpdateCategories(id, categories)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
