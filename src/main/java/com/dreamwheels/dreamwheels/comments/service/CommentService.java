package com.dreamwheels.dreamwheels.comments.service;

import com.dreamwheels.dreamwheels.comments.dtos.CommentDto;
import com.dreamwheels.dreamwheels.comments.models.CommentModel;
import com.dreamwheels.dreamwheels.configuration.responses.CustomPageResponse;

public interface CommentService {
    CommentDto addGarageComment(String garageId, CommentModel commentDto);

    CustomPageResponse<CommentDto> garageComments(String garageId, int pageNumber);

    void deleteComment(String commentId);

    CommentDto replyToComment(String commentId, CommentModel commentDto);
}
