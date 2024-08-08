package com.dreamwheels.dreamwheels.comments.serviceimpl;

import com.dreamwheels.dreamwheels.auth.repository.AuthRepository;
import com.dreamwheels.dreamwheels.comments.dto.CommentDto;
import com.dreamwheels.dreamwheels.comments.entity.Comment;
import com.dreamwheels.dreamwheels.comments.enums.CommentAction;
import com.dreamwheels.dreamwheels.comments.events.CommentEvent;
import com.dreamwheels.dreamwheels.comments.repository.CommentRepository;
import com.dreamwheels.dreamwheels.comments.service.CommentService;
import com.dreamwheels.dreamwheels.configuration.exceptions.EntityNotFoundException;
import com.dreamwheels.dreamwheels.configuration.responses.Data;
import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import com.dreamwheels.dreamwheels.configuration.responses.ResponseType;
import com.dreamwheels.dreamwheels.garage.entity.Garage;
import com.dreamwheels.dreamwheels.garage.repository.GarageRepository;
import com.dreamwheels.dreamwheels.users.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Override
    public ResponseEntity<GarageApiResponse<Comment>> addGarageComment(String garageId, CommentDto commentDto) {
        Garage garage = garageRepository.findById(garageId).orElseThrow(() -> new EntityNotFoundException("Garage not found"));
        Comment comment = Comment.builder()
                .comment(commentDto.getComment())
                .parent(null)
                .garage(garage)
                .user(authenticatedUser())
                .build();
        Comment saved = commentRepository.save(comment);
        applicationEventPublisher.publishEvent(new CommentEvent(comment, CommentAction.Add));
        return new ResponseEntity<>(new GarageApiResponse<>(new Data<>(saved), "Comment saved", ResponseType.SUCCESS), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<GarageApiResponse<Page<Comment>>> garageComments(String garageId, int pageNumber) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest pageRequest = PageRequest.of(pageNumber, PAGE_SIZE, sort);
        Page<Comment> comments = commentRepository.findAllByParentIsNull(pageRequest);
        return new ResponseEntity<>(new GarageApiResponse<>(
                new Data<>(comments),
                "Found " + comments.getTotalElements() + " comments",
                ResponseType.SUCCESS
        ), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<GarageApiResponse<Void>> deleteComment(String commentId) {
        Comment comment = commentRepository.findByIdAndUserId(commentId, authenticatedUser().getId()).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        System.out.println(comment.getComment());
        commentRepository.deleteById(comment.getId());
        deleteCommentAndReplies(comment);
        return new ResponseEntity<>(new GarageApiResponse<>(null, "Comment deleted", ResponseType.SUCCESS), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GarageApiResponse<Comment>> replyToComment(String commentId, CommentDto commentDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        Comment comment1 = Comment.builder()
                .comment(commentDto.getComment())
                .parent(comment)
                .user(authenticatedUser())
                .build();
        Comment saved = commentRepository.save(comment1);
        return new ResponseEntity<>(new GarageApiResponse<>(new Data<>(saved), "Comment reply has been saved", ResponseType.SUCCESS), HttpStatus.CREATED);
    }

    private User authenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authRepository.findByEmail(authentication.getName());
    }

    private void deleteCommentAndReplies(Comment comment) {
        if (comment.getReplies() != null && !comment.getReplies().isEmpty()) {
            for (Comment reply : comment.getReplies()) {
                deleteCommentAndReplies(reply);
            }
        }
        commentRepository.delete(comment);
    }
}
