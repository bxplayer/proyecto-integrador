package com.digitalmedia.photo.service;


import com.digitalmedia.activities.dto.ActivityDTO;
import com.digitalmedia.activities.model.Activity;
import com.digitalmedia.activities.service.ActivityService;
import com.digitalmedia.photo.dto.PhotoDTO;
import com.digitalmedia.photo.interfaces.IPhotoService;
import com.digitalmedia.photo.model.Photo;
import com.digitalmedia.photo.repository.PhotoRepository;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class PhotoService implements IPhotoService {

    private final PhotoRepository photoRepository;
    private final ActivityService activityService;
    private final ModelMapper modelMapper;

    public PhotoService(ModelMapper modelMapper, PhotoRepository photoRepository, ActivityService activityService) {
        this.photoRepository = photoRepository;
        this.activityService = activityService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PhotoDTO> getPhotosByActivity(Long activityId) {
        List<Photo> photosByActivity = photoRepository.findByPhotos_Activity_Id(activityId);
        return photosByActivity.stream()
                .map(photo -> modelMapper.map(photo, PhotoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PhotoDTO getPhotoById(Long id) {
        Photo photo = photoRepository.findById(id).orElse(null);
        return (photo != null) ? modelMapper.map(photo, PhotoDTO.class) : null;
    }

    @Override
    public PhotoDTO savePhoto(PhotoDTO photoDTO) {
        Photo photo = modelMapper.map(photoDTO, Photo.class);
        ActivityDTO activityDTO = activityService.getActivityById(photoDTO.getActivityId());
        if (activityDTO != null) {
            Activity activity = modelMapper.map(activityDTO, Activity.class);
            photo.setActivity(activity);
        }

        photo = photoRepository.save(photo);
        return modelMapper.map(photo, PhotoDTO.class);
    }

    @Override
    public PhotoDTO updatePhoto(Long id, PhotoDTO photoDTO) {
        Photo existingPhoto = photoRepository.findById(id).orElse(null);

        if (existingPhoto != null) {
            existingPhoto.setUrl(photoDTO.getUrl());
            existingPhoto.setMain(photoDTO.isMain());

            Photo updatedPhoto = photoRepository.save(existingPhoto);
            return modelMapper.map(updatedPhoto, PhotoDTO.class);
        }
        return null;
    }

    @Override
    public void deletePhoto(Long id) {
        photoRepository.deleteById(id);
    }
}
