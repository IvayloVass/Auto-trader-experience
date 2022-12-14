package bg.softuni.autoTraderExperience.web;

import bg.softuni.autoTraderExperience.models.binding.CommentBindingModel;
import bg.softuni.autoTraderExperience.models.views.CommentSearchModel;
import bg.softuni.autoTraderExperience.models.views.CommentViewModel;
import bg.softuni.autoTraderExperience.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @ModelAttribute(name = "commentBindingModel")
    public CommentBindingModel commentBindingModel() {
        return new CommentBindingModel();
    }

    @GetMapping("/add")
    public String addComment() {
        return "comment-add";
    }

    @PostMapping("/add")
    public String addComment(@Valid CommentBindingModel commentBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Principal principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("commentBindingModel", commentBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.commentBindingModel"
                            , bindingResult);
            return "redirect:/comment/add";
        }

        commentService.createComment(commentBindingModel, principal);
        return "redirect:/comment/all";
    }

    @GetMapping("/all")
    public String allComments(Model model) {
        List<CommentViewModel> comments = commentService.getComments();
        model.addAttribute("comments", comments);
        return "comments";
    }

    @GetMapping("/search")
    public String searchComment(@Valid CommentSearchModel commentSearchModel,
                                BindingResult bindingResult,
                                Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("commentSearchModel", commentSearchModel);
            model.addAttribute("org.springframework.validation.BindingResult.commentSearchModel", bindingResult);
            return "comment-search";
        }

        if (!model.containsAttribute("commentSearchModel")) {
            model.addAttribute("commentSearchModel", commentSearchModel);
        }

        if (!commentSearchModel.isEmpty()) {
            model.addAttribute("comments", commentService.searchComment(commentSearchModel));
        }
        return "comment-search";
    }

    @PostMapping("/{id}/delete")
    public String deleteComment(@PathVariable("id") Long id) {
        commentService.deleteComment(id);
        return "redirect:/comment/all";
    }

}
