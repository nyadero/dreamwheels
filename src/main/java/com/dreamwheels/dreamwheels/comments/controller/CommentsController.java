package com.dreamwheels.dreamwheels.comments.controller;

import com.dreamwheels.dreamwheels.comments.dtos.CommentDto;
import com.dreamwheels.dreamwheels.comments.models.CommentModel;
import com.dreamwheels.dreamwheels.comments.entity.Comment;
import com.dreamwheels.dreamwheels.comments.service.CommentService;
import com.dreamwheels.dreamwheels.configuration.responses.CustomPageResponse;
import com.dreamwheels.dreamwheels.configuration.responses.Data;
import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import com.dreamwheels.dreamwheels.configuration.responses.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<GarageApiResponse<CommentDto>> addGarageComment(
           @PathVariable("garageId") String garageId,
           @RequestBody CommentModel commentDto
    ){
        CommentDto comment = commentService.addGarageComment(garageId, commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new GarageApiResponse<>(new Data<>(comment), "Comment added", ResponseType.SUCCESS)
        );
    }

    // fetch garage comments
    @GetMapping("/{garageId}")
    public ResponseEntity<GarageApiResponse<CustomPageResponse<CommentDto>>> garageComments(
            @PathVariable("garageId") String garageId,
            @RequestParam(name = "page", defaultValue = "0") int pageNumber
    ){
        CustomPageResponse<CommentDto> comments = commentService.garageComments(garageId, pageNumber);
        return ResponseEntity.status(HttpStatus.OK).body(
                new GarageApiResponse<>(new Data<>(comments), "Comments retrieved", ResponseType.SUCCESS)
        );
    }

    // delete garage comments
    @PreAuthorize("isAuthenticated")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<GarageApiResponse<Void>> deleteComment(
            @PathVariable("commentId") String commentId
    ){
       commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new GarageApiResponse<>(null, "Comment deleted", ResponseType.SUCCESS)
        );
    }

    // reply to comment
    @PreAuthorize("isAuthenticated")
    @PostMapping("/{commentId}/reply")
    public ResponseEntity<GarageApiResponse<CommentDto>> replyToComment(
            @PathVariable("commentId") String commentId,
            @RequestBody CommentModel commentDto
    ){
        CommentDto comment = commentService.replyToComment(commentId, commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new GarageApiResponse<>(new Data<>(comment), "Reply added", ResponseType.SUCCESS)
        );
    }
}
