package com.example.freeforall.repositories;

import com.example.freeforall.entities.ForumEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IForumRepository extends JpaRepository<ForumEntity, UUID> {

    @Query(value = """
    SELECT * FROM forum
    WHERE category = ?1
    """, nativeQuery = true)
    Page<ForumEntity> findByCategory(Pageable pageable, String category);

    @Query(value = """
    SELECT * FROM forum
    ORDER BY (like_count + reply_count)
    DESC
    """, nativeQuery = true)
    Page<ForumEntity> findMostPopularPosts(Pageable pageable);

    @Query(value = """
    SELECT * FROM forum 
    ORDER BY created_at 
    DESC
    """, nativeQuery = true)
    Page<ForumEntity> findLatestPosts(Pageable pageable);
}
