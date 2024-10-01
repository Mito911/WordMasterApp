package pl.maciejtuznik.WordMasterApp.word;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class WordsRepositoryTest {

    @Autowired
    private WordsRepository wordsRepository;

    @Test
    public void testFindRandomWords() {
        List<Words> randomWords = wordsRepository.findRandomWords(1);
        assertNotNull(randomWords);
        assertEquals(0, randomWords.size());
    }

    @Test
    public void testFindWordsFromLastDay() {
        List<Words> wordsFromLastDay = wordsRepository.findWordsFromLastDay();
        assertNotNull(wordsFromLastDay);
        assertEquals(0, wordsFromLastDay.size());
    }

    @Test
    public void testFindWordsFromLastWeek() {
        List<Words> wordsFromLastWeek = wordsRepository.findWordsFromLastWeek();
        assertNotNull(wordsFromLastWeek);
        assertEquals(0, wordsFromLastWeek.size());
    }

    @Test
    public void testFindWordsFromLastMonth() {
        List<Words> wordsFromLastMonth = wordsRepository.findWordsFromLastMonth();
        assertNotNull(wordsFromLastMonth);
        assertEquals(0, wordsFromLastMonth.size());
    }
}
