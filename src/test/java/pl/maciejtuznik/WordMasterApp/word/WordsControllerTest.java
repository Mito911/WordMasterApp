package pl.maciejtuznik.WordMasterApp.word;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.maciejtuznik.WordMasterApp.categories.Categories;
import pl.maciejtuznik.WordMasterApp.categories.CategoriesService;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
public class WordsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WordsService wordsService;

    @MockBean
    private CategoriesService categoriesService;

    @Test
    public void getAllWords_ShouldReturnWordsList() throws Exception {
        when(wordsService.findAllWords()).thenReturn(List.of(new Words("Cat", "Kot", null)));

        mockMvc.perform(get("/words"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].originalWord").value("Cat"));
    }


    @Test
    public void getWordsByCategory_ShouldReturnWordsList() throws Exception {
        when(wordsService.findWordsByCategory(1)).thenReturn(List.of(new Words("Dog", "Pies", null)));

        mockMvc.perform(get("/words/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].originalWord").value("Dog"));
    }

    @Test
    public void deleteWord_ShouldReturnNoContent_WhenWordExists() throws Exception {
        when(wordsService.deleteWords(1)).thenReturn(true);

        mockMvc.perform(delete("/words/1"))
                .andExpect(status().isNoContent());
    }


}
