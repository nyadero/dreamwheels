package com.dreamwheels.dreamwheels.likes.event;

import com.dreamwheels.dreamwheels.likes.entity.Like;
import com.dreamwheels.dreamwheels.likes.enums.LikeAction;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class LikeEvent extends ApplicationEvent {
    @Autowired
    private Like like;
    @Autowired
    private LikeAction likeAction;

    public LikeEvent(Like like, LikeAction likeAction) {
        super(like);
        this.like = like;
        this.likeAction = likeAction;
    }

}
