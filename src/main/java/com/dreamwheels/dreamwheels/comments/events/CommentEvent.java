package com.dreamwheels.dreamwheels.comments.events;

import com.dreamwheels.dreamwheels.comments.entity.Comment;
import com.dreamwheels.dreamwheels.comments.enums.CommentAction;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class CommentEvent extends ApplicationEvent {
    private Comment comment;
    private CommentAction commentAction;

    public CommentEvent(Comment comment, CommentAction commentAction) {
        super(comment);
        this.comment = comment;
        this.commentAction = commentAction;
    }
}
