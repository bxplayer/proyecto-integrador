package com.digitalmedia.photo.interfaces;


import com.digitalmedia.photo.dto.PhotoDTO;

import java.util.List;

public interface IPhotoService {
    List<PhotoDTO> getPhotosByActivity(Long activityId);
    PhotoDTO getPhotoById(Long id);
    PhotoDTO savePhoto(PhotoDTO photoDTO);
    PhotoDTO updatePhoto(Long id, PhotoDTO photoDTO);
    void deletePhoto(Long id);
}
