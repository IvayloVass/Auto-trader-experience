package bg.softuni.autoTraderExperience.web;

import bg.softuni.autoTraderExperience.models.entities.AutoTrader;
import bg.softuni.autoTraderExperience.models.entities.Comment;
import bg.softuni.autoTraderExperience.models.entities.Location;
import bg.softuni.autoTraderExperience.models.entities.User;
import bg.softuni.autoTraderExperience.models.views.CommentSearchModel;
import bg.softuni.autoTraderExperience.services.CommentService;
import bg.softuni.autoTraderExperience.web.utils.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    private static final String COMMENT_CONTROLLER_URL = "/comment/all";
    private static final String COMMENT_SEARCH_URL = "/comment/search";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestUtil testUtil;

    @MockBean
    private CommentService commentService;

    private Comment testComment;


    @Test
    void testShouldReturnValidStatusViewNameAndModel() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(COMMENT_CONTROLLER_URL))
                .andExpect(status().isOk())
                .andExpect(view().name("comments"))
                .andExpect(model().attributeExists("comments"));

    }

    @Test
    void testSearchShouldReturnValidStatusAndNameAndModel() throws Exception {
        mockMvc.perform((MockMvcRequestBuilders.get(COMMENT_SEARCH_URL)))
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

        mockMvc.perform(MockMvcRequestBuilders.get(COMMENT_SEARCH_URL))
                .andExpect(status().isOk())
                .andExpect(view().name("comment-search"));

    }

    @BeforeEach
    void setUp() {
        testUtil.initRoles();
        Location location = testUtil.createLocation();
        AutoTrader trader = testUtil.createAutoTrader(location);
        User adinUser = testUtil.initAdminUser("test@example.com");
        testComment = testUtil.createComment(adinUser, trader);
    }

    @Test
    @WithMockUser(
            username = "test@example.com",
            roles = {"ADMIN", "USER"}
    )
    void testDeleteByAdmin() throws Exception {
        mockMvc.perform(post("/comment/{id}/delete", testComment.getId()).
                        with(csrf())
                ).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/comment/all"));
    }

}
