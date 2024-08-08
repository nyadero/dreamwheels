package com.dreamwheels.dreamwheels.comments.repository;

import com.dreamwheels.dreamwheels.comments.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, String> {
    Comment save(Comment comment);

    Optional<Comment> findByIdAndUserId(String commentId, String id);

    void delete(Comment comment);

    Optional<Comment> findById(String commentId);

    Page<Comment> findAllByParentIsNull(PageRequest pageRequest);

    void deleteById(String id);
}
