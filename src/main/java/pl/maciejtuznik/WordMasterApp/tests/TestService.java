package pl.maciejtuznik.WordMasterApp.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.maciejtuznik.WordMasterApp.word.Words;
import pl.maciejtuznik.WordMasterApp.word.WordsRepository;

import java.util.List;

@Service
public class TestService {
    @Autowired
    WordsRepository wordsRepository;


    public List<Words> getRandomWords(int count){
        return wordsRepository.findRandomWords(count);
    }

    public List<Words> getWordsFromLastDay(){
        return wordsRepository.findWordsFromLastDay();
    }

    public List<Words> getWordsFromLastWeek(){
        return wordsRepository.findWordsFromLastWeek();
    }

    public List<Words> getWordsFromLastMonth(){
        return wordsRepository.findWordsFromLastMonth();
    }

    public List<Words> getWordsByCategory(Integer categoryId) {
        return wordsRepository.findByCategoryId(categoryId);
    }
}