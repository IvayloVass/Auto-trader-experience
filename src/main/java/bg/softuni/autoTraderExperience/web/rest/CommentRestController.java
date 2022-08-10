package bg.softuni.autoTraderExperience.web.rest;

import bg.softuni.autoTraderExperience.models.dtos.CommentDeletionDto;
import bg.softuni.autoTraderExperience.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comment/api")
public class CommentRestController {

    private final CommentService commentService;

    @Autowired
    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<CommentDeletionDto> deleteComment(@PathVariable("id") Long id) {
        return ResponseEntity.ok(commentService.deleteComment(id));
    }
}
