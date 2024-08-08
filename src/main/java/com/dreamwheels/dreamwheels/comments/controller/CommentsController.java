package com.dreamwheels.dreamwheels.comments.controller;

import com.dreamwheels.dreamwheels.comments.dto.CommentDto;
import com.dreamwheels.dreamwheels.comments.entity.Comment;
import com.dreamwheels.dreamwheels.comments.service.CommentService;
import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentsController {
    @Autowired
    private CommentService commentService;

    // add garage comment
    @PreAuthorize("isAuthenticated")
    @PostMapping("/{garageId}")
    public ResponseEntity<GarageApiResponse<Comment>> addGarageComment(
           @PathVariable("garageId") String garageId,
           @RequestBody CommentDto commentDto
    ){
        return commentService.addGarageComment(garageId, commentDto);
    }

    // fetch garage comments
    @GetMapping("/{garageId}")
    public ResponseEntity<GarageApiResponse<Page<Comment>>> garageComments(
            @PathVariable("garageId") String garageId,
            @RequestParam(name = "page", defaultValue = "0") int pageNumber
    ){
        return commentService.garageComments(garageId, pageNumber);
    }

    // delete garage comments
    @PreAuthorize("isAuthenticated")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<GarageApiResponse<Void>> deleteComment(
            @PathVariable("commentId") String commentId
    ){
       return commentService.deleteComment(commentId);
    }

    // reply to comment
    @PreAuthorize("isAuthenticated")
    @PostMapping("/{commentId}/reply")
    public ResponseEntity<GarageApiResponse<Comment>> replyToComment(
            @PathVariable("commentId") String commentId,
            @RequestBody CommentDto commentDto
    ){
        return commentService.replyToComment(commentId, commentDto);
    }
}
