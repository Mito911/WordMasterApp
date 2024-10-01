package pl.maciejtuznik.WordMasterApp.word;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.maciejtuznik.WordMasterApp.categories.Categories;
import pl.maciejtuznik.WordMasterApp.categories.CategoriesService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WordsServiceTest {

    @Mock
    private WordsRepository wordsRepository;

    @Mock
    private CategoriesService categoriesService;

    @InjectMocks
    private WordsService wordsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveWords_ShouldSaveWord() {
        Categories category = new Categories();
        category.setId(1);
        category.setName("Animals");

        Words word = new Words("Cat", "Kot", category);

        when(categoriesService.findCategoryById(1)).thenReturn(Optional.of(category));
        when(wordsRepository.save(word)).thenReturn(word);

        Words savedWord = wordsService.saveWords(word, 1);

        assertNotNull(savedWord);
        assertEquals("Cat", savedWord.getOriginalWord());
        verify(wordsRepository, times(1)).save(word);
    }

    @Test
    public void findWords_ShouldReturnWord_WhenExists() {
        Words word = new Words("Dog", "Pies", null);
        word.setId(1);

        when(wordsRepository.findById(1)).thenReturn(Optional.of(word));

        Optional<Words> foundWord = wordsService.findWords(1);

        assertTrue(foundWord.isPresent());
        assertEquals("Dog", foundWord.get().getOriginalWord());
    }

    @Test
    public void deleteWords_ShouldDeleteWord_WhenExists() {
        when(wordsRepository.existsById(1)).thenReturn(true);

        boolean isDeleted = wordsService.deleteWords(1);

        assertTrue(isDeleted);
        verify(wordsRepository, times(1)).deleteById(1);
    }

    @Test
    public void findWordsByCategory_ShouldReturnWordsList() {
        when(wordsRepository.findByCategoryId(1)).thenReturn(List.of(new Words("Fish", "Ryba", null)));

        List<Words> wordsList = wordsService.findWordsByCategory(1);

        assertNotNull(wordsList);
        assertFalse(wordsList.isEmpty());
        assertEquals("Fish", wordsList.get(0).getOriginalWord());
    }
}
