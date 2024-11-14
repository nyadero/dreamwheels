package com.dreamwheels.dreamwheels.likes.serviceimpl;

import com.dreamwheels.dreamwheels.auth.repository.AuthRepository;
import com.dreamwheels.dreamwheels.configuration.responses.Data;
import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import com.dreamwheels.dreamwheels.configuration.responses.ResponseType;
import com.dreamwheels.dreamwheels.garage.entity.Garage;
import com.dreamwheels.dreamwheels.garage.repository.GarageRepository;
import com.dreamwheels.dreamwheels.likes.entity.Like;
import com.dreamwheels.dreamwheels.likes.enums.LikeAction;
import com.dreamwheels.dreamwheels.likes.repository.LikeRepository;
import com.dreamwheels.dreamwheels.likes.service.LikeService;
import com.dreamwheels.dreamwheels.users.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private GarageRepository garageRepository;

    @Override
    public ResponseEntity<GarageApiResponse<LikeAction>> likeGarage(String garageId) {
        Garage garage = garageRepository.findById(garageId).orElseThrow(() -> new EntityNotFoundException("Garage not found"));
        // find if like exists
        Like like = likeRepository.findByGarageIdAndUserId(garageId, authenticatedUser().getId());
        if (like == null){
            // like garage
            Like like1 = Like.builder()
                    .user(authenticatedUser())
                    .garage(garage)
                    .build();
            likeRepository.save(like1);
            return new ResponseEntity<>(new GarageApiResponse<>(
                    new Data<>(LikeAction.Like),
                            "Garage liked",
                    ResponseType.SUCCESS
            ), HttpStatus.CREATED);
        }else{
            // dislike garage
            likeRepository.delete(like);
            return new ResponseEntity<>(new GarageApiResponse<>(
                    new Data<>(LikeAction.Dislike),
                    "Garage disliked",
                    ResponseType.SUCCESS
            ), HttpStatus.CREATED);
        }
    }

    private User authenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            // Return null if there's no authenticated user
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        } else {
            return null;
        }
    }
}
