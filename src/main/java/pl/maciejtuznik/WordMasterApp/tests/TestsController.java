package pl.maciejtuznik.WordMasterApp.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.maciejtuznik.WordMasterApp.word.Words;

import java.util.List;

@RestController
@RequestMapping("/tests")
public class TestsController {
    @Autowired
    private TestService testService;

    @GetMapping("/random")
    public List<Words> getRandomWords(@RequestParam int count) {
        return testService.getRandomWords(count);
    }

    @GetMapping("/from-last-day")
    public List<Words> getWordsFromLastDay() {
        return testService.getWordsFromLastDay();
    }

    @GetMapping("/from-last-week")
    public List<Words> getWordsFromLastWeek() {
        return testService.getWordsFromLastWeek();
    }

    @GetMapping("/from-last-month")
    public List<Words> getWordsFromLastMonth() {
        return testService.getWordsFromLastMonth();
    }

    @GetMapping("/category/{categoryId}")
    public List<Words> getTestByCategory(@PathVariable Integer categoryId) {
        return testService.getWordsByCategory(categoryId);
    }
}

