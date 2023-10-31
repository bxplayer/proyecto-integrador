package com.digitalmedia.photo.repository;

import com.digitalmedia.photo.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByPhotos_Activity_Id(Long activityId);
}