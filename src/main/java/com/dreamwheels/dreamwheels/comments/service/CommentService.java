package com.dreamwheels.dreamwheels.comments.service;

import com.dreamwheels.dreamwheels.comments.dto.CommentDto;
import com.dreamwheels.dreamwheels.comments.entity.Comment;
import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<GarageApiResponse<Comment>> addGarageComment(String garageId, CommentDto commentDto);

    ResponseEntity<GarageApiResponse<Page<Comment>>> garageComments(String garageId, int pageNumber);

    ResponseEntity<GarageApiResponse<Void>> deleteComment(String commentId);

    ResponseEntity<GarageApiResponse<Comment>> replyToComment(String commentId, CommentDto commentDto);
}
