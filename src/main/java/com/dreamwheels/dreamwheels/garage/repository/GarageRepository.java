package com.dreamwheels.dreamwheels.garage.repository;

import com.dreamwheels.dreamwheels.garage.entity.Garage;
import com.dreamwheels.dreamwheels.garage.enums.GarageCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GarageRepository extends PagingAndSortingRepository<Garage, String> {
    Optional<Garage> findById(String id);

    Garage save(Garage wheelie);

    void deleteById(String id);

    Optional<Garage> findByIdAndUserId(String id, String userId);

    Page<Garage> findAllByCategory(GarageCategory category, PageRequest pageRequest);

    Page<Garage> findAll(Specification<Garage> vehiclesSpecification, Pageable pageable);
}
