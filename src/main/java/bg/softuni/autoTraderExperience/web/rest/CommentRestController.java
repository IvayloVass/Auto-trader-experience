package bg.softuni.autoTraderExperience.web.rest;

import bg.softuni.autoTraderExperience.models.dtos.CommentDeletionDto;
import bg.softuni.autoTraderExperience.models.dtos.CommentDto;
import bg.softuni.autoTraderExperience.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/all")
    public ResponseEntity<List<CommentDto>> getAllComments() {
        return ResponseEntity.ok(commentService.getAllComments());
    }
}
