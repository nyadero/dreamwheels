package com.dreamwheels.dreamwheels.comments.eventlistener;

import com.dreamwheels.dreamwheels.comments.enums.CommentAction;
import com.dreamwheels.dreamwheels.comments.events.CommentEvent;
import com.dreamwheels.dreamwheels.garage.entity.Garage;
import com.dreamwheels.dreamwheels.garage.repository.GarageRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommentEventListener implements ApplicationListener<CommentEvent> {
    @Autowired
    private GarageRepository garageRepository;

    @Override
    public void onApplicationEvent(CommentEvent event) {
        Garage garage = event.getComment().getGarage();
        if (event.getCommentAction().equals(CommentAction.Add)){
            garage.setCommentsCount(garage.getCommentsCount() + 1);
        }

        if (event.getCommentAction().equals(CommentAction.Delete)){
            garage.setCommentsCount(garage.getCommentsCount() + 1);
        }
        garageRepository.save(garage);
    }

}
