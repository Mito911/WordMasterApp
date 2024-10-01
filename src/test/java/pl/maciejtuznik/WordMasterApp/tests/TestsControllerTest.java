package pl.maciejtuznik.WordMasterApp.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.maciejtuznik.WordMasterApp.word.Words;
import pl.maciejtuznik.WordMasterApp.word.WordsRepository;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
public class TestsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TestService testService;

    @Test
    public void getRandomWords_ShouldReturnWords() throws Exception {
        Words word = new Words("Cat", "Kot", null);
        when(testService.getRandomWords(1)).thenReturn(Collections.singletonList(word));

        mockMvc.perform(get("/tests/random?count=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].originalWord").value("Cat"));
    }

    @Test
    public void getWordsFromLastDay_ShouldReturnWords() throws Exception {
        Words word = new Words("Dog", "Pies", null);
        when(testService.getWordsFromLastDay()).thenReturn(Collections.singletonList(word));

        mockMvc.perform(get("/tests/from-last-day"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].originalWord").value("Dog"));
    }
}
