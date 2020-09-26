package com.goruslan.socialgeeking.repository;

import com.goruslan.socialgeeking.domain.Post;
import com.goruslan.socialgeeking.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 *  JpaRepository extends PagingAndSortingRepository which in turn extends CrudRepository.
 *
 * Their main functions are:
 *      - CrudRepository mainly provides CRUD functions.
 *      - PagingAndSortingRepository provides methods to do pagination and sorting records.
 *      - JpaRepository provides some JPA-related methods such as flushing the persistence
 *        context and deleting records in a batch.
 *
 * JpaRepository will have all the functions of CrudRepository and PagingAndSortingRepository.
 * So if you don't need the repository to have the functions provided by
 * JpaRepository and PagingAndSortingRepository use CrudRepository.
 */


public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByPrivacy(String privacy);
    List<Post> findAllByPrivacyAndUser(String privacy, User user);

    @Query("SELECT p FROM Post p WHERE p.privacy = ?1 and p.user <> ?2")
    List<Post> findAllByPrivacyAndNotUser(String privacy, User user);

    List<Post> findAllByUser(User user);
}
