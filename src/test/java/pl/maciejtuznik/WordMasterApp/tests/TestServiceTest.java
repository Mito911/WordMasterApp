package pl.maciejtuznik.WordMasterApp.tests;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.maciejtuznik.WordMasterApp.word.Words;
import pl.maciejtuznik.WordMasterApp.word.WordsRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TestServiceTest {

    @Mock
    private WordsRepository wordsRepository;

    @InjectMocks
    private TestService testService;

    public TestServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getRandomWords_ShouldReturnRandomWords() {
        Words word = new Words("Cat", "Kot", null);
        when(wordsRepository.findRandomWords(1)).thenReturn(Collections.singletonList(word));

        List<Words> result = testService.getRandomWords(1);

        assertEquals(1, result.size());
        assertEquals("Cat", result.get(0).getOriginalWord());
    }

    @Test
    public void getWordsFromLastDay_ShouldReturnWordsFromLastDay() {
        Words word = new Words("Dog", "Pies", null);
        when(wordsRepository.findWordsFromLastDay()).thenReturn(Collections.singletonList(word));

        List<Words> result = testService.getWordsFromLastDay();

        assertEquals(1, result.size());
        assertEquals("Dog", result.get(0).getOriginalWord());
    }
}
