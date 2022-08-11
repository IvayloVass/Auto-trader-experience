package bg.softuni.autoTraderExperience.web;

import bg.softuni.autoTraderExperience.models.views.CommentSearchModel;
import bg.softuni.autoTraderExperience.services.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    private static final String COMMENT_CONTROLLER_PREFIX = "/comment/all";
    private static final String COMMENT_SEARCH_PREFIX = "/comment/search";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;


    @Test
    void testShouldReturnValidStatusViewNameAndModel() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(COMMENT_CONTROLLER_PREFIX))
                .andExpect(status().isOk())
                .andExpect(view().name("comments"))
                .andExpect(model().attributeExists("comments"));

    }

    @Test
    void testSearchShouldReturnValidStatusAndNameAndModel() throws Exception {
        mockMvc.perform((MockMvcRequestBuilders.get(COMMENT_SEARCH_PREFIX)))
                .andExpect(status().isOk())
                .andExpect(view().name("comment-search"))
                .andExpect(model().attributeExists("commentSearchModel"));

        CommentSearchModel commentSearchModel = new CommentSearchModel();
        commentSearchModel.setCity("Sofia");
        commentSearchModel.setTitle("Moskvich");
        commentSearchModel.setTraderName("Putin");

        when(commentService.searchComment(commentSearchModel))
                .thenReturn(List.of(new CommentSearchModel("Mockvich", "Aleko 8",
                        "https://cdni.russiatoday.com/rbthmedia/images/2017.11/original/5a0014ff15e9f9386c66d384.jpg"
                        , "Putin", "Sofia", "Miziq 21")));

        mockMvc.perform(MockMvcRequestBuilders.get(COMMENT_SEARCH_PREFIX))
                .andExpect(status().isOk())
                .andExpect(view().name("comment-search"));

    }

}
