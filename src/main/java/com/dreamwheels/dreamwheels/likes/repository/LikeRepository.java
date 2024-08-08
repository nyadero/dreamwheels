package com.dreamwheels.dreamwheels.likes.repository;

import com.dreamwheels.dreamwheels.likes.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, String> {
    Like findByGarageIdAndUserId(String garageId, String id);
}
