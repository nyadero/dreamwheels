package com.dreamwheels.dreamwheels.comments.serviceimpl;

import com.dreamwheels.dreamwheels.auth.repository.AuthRepository;
import com.dreamwheels.dreamwheels.comments.adapters.CommentAdapter;
import com.dreamwheels.dreamwheels.comments.dtos.CommentDto;
import com.dreamwheels.dreamwheels.comments.models.CommentModel;
import com.dreamwheels.dreamwheels.comments.entity.Comment;
import com.dreamwheels.dreamwheels.comments.enums.CommentAction;
import com.dreamwheels.dreamwheels.comments.events.CommentEvent;
import com.dreamwheels.dreamwheels.comments.repository.CommentRepository;
import com.dreamwheels.dreamwheels.comments.service.CommentService;
import com.dreamwheels.dreamwheels.configuration.adapters.CustomPageAdapter;
import com.dreamwheels.dreamwheels.configuration.exceptions.EntityNotFoundException;
import com.dreamwheels.dreamwheels.configuration.responses.CustomPageResponse;
import com.dreamwheels.dreamwheels.garage.entity.Garage;
import com.dreamwheels.dreamwheels.garage.repository.GarageRepository;
import com.dreamwheels.dreamwheels.users.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    public static final int PAGE_SIZE = 20;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private GarageRepository garageRepository;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private CommentAdapter commentAdapter;

    @Autowired
    private CustomPageAdapter<CommentDto, CommentDto> customPageAdapter;

    @Override
    public CommentDto addGarageComment(String garageId, CommentModel commentDto) {
        Garage garage = garageRepository.findById(garageId).orElseThrow(() -> new EntityNotFoundException("Garage not found"));
        Comment comment = Comment.builder()
                .comment(commentDto.getComment())
                .parent(null)
                .garage(garage)
                .user(authenticatedUser())
                .build();
        Comment saved = commentRepository.save(comment);
        applicationEventPublisher.publishEvent(new CommentEvent(comment, CommentAction.Add));
        return commentAdapter.toBusiness(saved);
    }

    @Override
    public CustomPageResponse<CommentDto> garageComments(String garageId, int pageNumber) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest pageRequest = PageRequest.of(pageNumber, PAGE_SIZE, sort);
        Page<CommentDto> comments = commentRepository.findAllByParentIsNull(pageRequest).map(comment -> commentAdapter.toBusiness(comment));
        return customPageAdapter.toBusiness(comments);
    }

    @Override
    @Transactional
    public void deleteComment(String commentId) {
        Comment comment = commentRepository.findByIdAndUserId(commentId, authenticatedUser().getId()).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        System.out.println(comment.getComment());
        commentRepository.deleteById(comment.getId());
    }

    @Override
    public CommentDto replyToComment(String commentId, CommentModel commentDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        Comment comment1 = Comment.builder()
                .comment(commentDto.getComment())
                .parent(comment)
                .user(authenticatedUser())
                .build();
       return commentAdapter.toBusiness(commentRepository.save(comment1));
    }

    private User authenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            // Return null if there's no authenticated user
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        } else {
            return null;
        }
    }

}
