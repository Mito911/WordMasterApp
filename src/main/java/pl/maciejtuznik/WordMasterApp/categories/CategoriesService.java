package pl.maciejtuznik.WordMasterApp.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriesService {

    @Autowired
    CategoriesRepository categoriesRepository;

    public Iterable<Categories> findAllCategories() {
        return categoriesRepository.findAll();
    }

    public Optional<Categories> findCategoriesById(Integer id) {
        return categoriesRepository.findById(id);
    }

    public Optional<Categories> findCategoryById(Integer id) {
        return categoriesRepository.findById(id);
    }

    public Optional<Categories> updateCategories(Integer id, Categories updateCategories) {
        return categoriesRepository.findById(id)
                .map(existing -> {
                    existing.setName(updateCategories.getName());
                    existing.setWords(updateCategories.getWords());
                    return categoriesRepository.save(existing);
                });
    }

    public Optional<Categories> pratiallyUpdateCategories(Integer id, Categories updateCategories) {
        return categoriesRepository.findById(id)
                .map(existing -> {
                    if (updateCategories.getName() != null) {
                        existing.setName(updateCategories.getName());
                    }
                    if (updateCategories.getWords() != null) {
                        existing.setWords(updateCategories.getWords());
                    }
                    return categoriesRepository.save(existing);
                });
    }

    public Categories saveCategories(Categories categories) {
        return categoriesRepository.save(categories);
    }

    public boolean deleteCategories(Integer id) {
        if (categoriesRepository.existsById(id)) {
            categoriesRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}

