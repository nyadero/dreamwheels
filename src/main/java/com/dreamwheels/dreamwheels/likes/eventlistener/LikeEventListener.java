package com.dreamwheels.dreamwheels.likes.eventlistener;

import com.dreamwheels.dreamwheels.garage.entity.Garage;
import com.dreamwheels.dreamwheels.garage.repository.GarageRepository;
import com.dreamwheels.dreamwheels.likes.enums.LikeAction;
import com.dreamwheels.dreamwheels.likes.event.LikeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class LikeEventListener implements ApplicationListener<LikeEvent> {

    @Autowired
    private GarageRepository garageRepository;

    @Override
    public void onApplicationEvent(LikeEvent event) {
        Garage garage = event.getLike().getGarage();
        if (event.getLikeAction().equals(LikeAction.Like)){
            garage.setLikesCount(garage.getLikesCount() + 1);
        }
        if (event.getLikeAction().equals(LikeAction.Dislike)){
            garage.setLikesCount(garage.getLikesCount() - 1);
        }
        garageRepository.save(garage);
    }

    
}
