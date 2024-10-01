package pl.maciejtuznik.WordMasterApp.word;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.maciejtuznik.WordMasterApp.categories.Categories;
import pl.maciejtuznik.WordMasterApp.categories.CategoriesService;

import java.util.List;
import java.util.Optional;

@Service
public class WordsService {

    @Autowired
    private WordsRepository wordsRepository;

    @Autowired
    private CategoriesService categoriesService;

    public Optional<Categories> findCategoryById(Integer id) {
        return categoriesService.findCategoryById(id);
    }

    public Words saveWords(Words words, Integer categoryId) {
        Optional<Categories> category = categoriesService.findCategoryById(categoryId);
        if (category.isPresent()) {
            words.setCategory(category.get());
            System.out.println("Saving word: " + words.getOriginalWord() + " to category: " + category.get().getName());
            return wordsRepository.save(words);
        } else {
            throw new IllegalArgumentException("Category not found");
        }
    }

    public List<Words> findAllWords() {
        return wordsRepository.findAll();
    }

    public Optional<Words> findWords(Integer id) {
        return wordsRepository.findById(id);
    }

    public List<Words> findWordsByCategory(Integer categoryId) {
        return wordsRepository.findByCategoryId(categoryId);
    }

    public Optional<Words> partiallyUpdateWords(Integer id, Words updateWords) {
        return wordsRepository.findById(id)
                .map(existing -> {
                    if (updateWords.getOriginalWord() != null) {
                        existing.setOriginalWord(updateWords.getOriginalWord());
                    }
                    if (updateWords.getTranslatedWord() != null) {
                        existing.setTranslatedWord(updateWords.getTranslatedWord());
                    }
                    return wordsRepository.save(existing);
                });
    }

    public boolean deleteWords(Integer id) {
        if (wordsRepository.existsById(id)) {
            wordsRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}



