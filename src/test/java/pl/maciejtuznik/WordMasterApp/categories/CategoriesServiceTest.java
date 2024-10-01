package pl.maciejtuznik.WordMasterApp.categories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CategoriesServiceTest {

    @Mock
    private CategoriesRepository categoriesRepository;

    @InjectMocks
    private CategoriesService categoriesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllCategories() {
        categoriesService.findAllCategories();
        verify(categoriesRepository, times(1)).findAll();
    }

    @Test
    void findCategoryById_ExistingCategory() {
        Categories category = new Categories();
        category.setId(1);
        category.setName("Test Category");

        when(categoriesRepository.findById(1)).thenReturn(Optional.of(category));

        Optional<Categories> foundCategory = categoriesService.findCategoryById(1);
        assertTrue(foundCategory.isPresent());
        assertEquals("Test Category", foundCategory.get().getName());
    }

    @Test
    void findCategoryById_NonExistingCategory() {
        when(categoriesRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Categories> foundCategory = categoriesService.findCategoryById(1);
        assertFalse(foundCategory.isPresent());
    }

    @Test
    void saveCategory() {
        Categories category = new Categories();
        category.setName("New Category");

        when(categoriesRepository.save(category)).thenReturn(category);

        Categories savedCategory = categoriesService.saveCategories(category);
        assertNotNull(savedCategory);
        assertEquals("New Category", savedCategory.getName());
    }

    @Test
    void deleteCategory_ExistingCategory() {
        when(categoriesRepository.existsById(1)).thenReturn(true);

        boolean result = categoriesService.deleteCategories(1);
        assertTrue(result);
        verify(categoriesRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteCategory_NonExistingCategory() {
        when(categoriesRepository.existsById(1)).thenReturn(false);

        boolean result = categoriesService.deleteCategories(1);
        assertFalse(result);
        verify(categoriesRepository, never()).deleteById(1);
    }
}

