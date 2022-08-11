package bg.softuni.autoTraderExperience.services;

import bg.softuni.autoTraderExperience.models.binding.CommentBindingModel;
import bg.softuni.autoTraderExperience.models.dtos.CommentDeletionDto;
import bg.softuni.autoTraderExperience.models.dtos.CommentDto;
import bg.softuni.autoTraderExperience.models.entities.AutoTrader;
import bg.softuni.autoTraderExperience.models.entities.Comment;
import bg.softuni.autoTraderExperience.models.entities.Location;
import bg.softuni.autoTraderExperience.models.entities.User;
import bg.softuni.autoTraderExperience.models.views.CommentSearchModel;
import bg.softuni.autoTraderExperience.models.views.CommentViewModel;
import bg.softuni.autoTraderExperience.repositoris.CommentRepository;
import bg.softuni.autoTraderExperience.repositoris.CommentSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;


@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserDetailsService userDetailsService,
                          UserService userService, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public void createComment(CommentBindingModel commentBindingModel, Principal principal) {
        String email = principal.getName();
        User user = userService.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("This user with email: " + email + " is not found!"));
        Comment comment = modelMapper.map(commentBindingModel, Comment.class);
        comment.setCommentCreated(LocalDate.now());

        Location location = new Location();
        location.setAddress(commentBindingModel.getAddress());
        location.setCity(commentBindingModel.getCity());

        AutoTrader trader = new AutoTrader();
        trader.setLocation(location);
        trader.setTraderName(commentBindingModel.getTraderName());

        comment.setAutoTrader(trader);
        comment.setUser(user);
        commentRepository.save(comment);

    }

    public List<CommentViewModel> getComments() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentViewModel> cvm = comments
                .stream()
                .map(c -> modelMapper.map(c, CommentViewModel.class)).toList();
        for (int i = 0; i < cvm.size(); i++) {
            cvm.get(i).setAddress(comments.get(i).getAutoTrader().getLocation().getAddress());
            cvm.get(i).setCity(comments.get(i).getAutoTrader().getLocation().getCity());
        }
        return cvm;
    }

    public CommentDeletionDto deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).get();
        CommentDeletionDto commentDeletionDto = modelMapper.map(comment, CommentDeletionDto.class);
        commentDeletionDto.setCity(comment.getAutoTrader().getLocation().getCity());
        commentDeletionDto.setAddress(comment.getAutoTrader().getLocation().getAddress());
        commentRepository.delete(comment);
        return commentDeletionDto;
    }

    public List<CommentSearchModel> searchComment(CommentSearchModel commentSearchModel) {
        return commentRepository.findAll(new CommentSpecification(commentSearchModel))
                .stream()
                .map(this::map).toList();

    }

    private CommentSearchModel map(Comment comment) {
        return modelMapper.map(comment, CommentSearchModel.class)
                .setTraderName(comment.getAutoTrader().getTraderName())
                .setCity(comment.getAutoTrader().getLocation().getCity())
                .setAddress(comment.getAutoTrader().getLocation().getAddress());

    }

    public List<CommentDto> getAllComments() {
        return commentRepository.findAll()
                .stream()
                .map(c -> modelMapper.map(c, CommentDto.class))
                .toList();

    }
}
