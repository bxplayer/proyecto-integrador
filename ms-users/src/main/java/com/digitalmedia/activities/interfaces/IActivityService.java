package com.digitalmedia.activities.interfaces;

import com.digitalmedia.activities.dto.ActivityDTO;

import java.util.List;

public interface IActivityService {
    List<ActivityDTO> getAll();
    ActivityDTO getActivityById(Long id);
    ActivityDTO saveActivity(ActivityDTO activityDTO);
    ActivityDTO updateActivity(Long id, ActivityDTO activityDTO);
    void deleteActivity(Long id);
}
