package com.dreamwheels.dreamwheels.comments.service;

import com.dreamwheels.dreamwheels.comments.dto.CommentDto;
import com.dreamwheels.dreamwheels.comments.entity.Comment;
import org.springframework.data.domain.Page;

public interface CommentService {
    Comment addGarageComment(String garageId, CommentDto commentDto);

    Page<Comment> garageComments(String garageId, int pageNumber);

    void deleteComment(String commentId);

    Comment replyToComment(String commentId, CommentDto commentDto);
}
