package com.dreamwheels.dreamwheels.garage.repository;

import com.dreamwheels.dreamwheels.garage.entity.Garage;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GarageRepository extends PagingAndSortingRepository<Garage, String> {
    Optional<Garage> findById(String id);

    Garage save(Garage wheelie);

    void deleteById(String id);
}
