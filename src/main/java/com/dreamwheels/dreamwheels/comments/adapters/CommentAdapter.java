package com.dreamwheels.dreamwheels.comments.adapters;

import com.dreamwheels.dreamwheels.comments.dtos.CommentDto;
import com.dreamwheels.dreamwheels.comments.entity.Comment;
import com.dreamwheels.dreamwheels.configuration.adapters.EntityAdapter;
import org.springframework.stereotype.Component;

@Component
public class CommentAdapter implements EntityAdapter<Comment,CommentDto> {
    @Override
    public CommentDto toBusiness(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .user(comment.getUser())
                .parent(comment.getParent() != null ? buildParent(comment.getParent()) : null)
                .replies(comment.getReplies().stream().map(comment1 -> toBusiness(comment)).toList())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }

    private CommentDto buildParent(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
