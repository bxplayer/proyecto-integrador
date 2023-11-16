package com.integrador.evently.photo.service;

import com.integrador.evently.providers.dto.ProviderDTO;
import com.integrador.evently.providers.model.Provider;
import com.integrador.evently.providers.service.ProviderService;
import com.integrador.evently.photo.dto.PhotoDTO;
import com.integrador.evently.photo.interfaces.IPhotoService;
import com.integrador.evently.photo.model.Photo;
import com.integrador.evently.photo.repository.PhotoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PhotoService implements IPhotoService {

    private final PhotoRepository photoRepository;
    private final ProviderService providerService;
    private final ModelMapper modelMapper;

    public PhotoService(ModelMapper modelMapper, PhotoRepository photoRepository, ProviderService providerService) {
        this.photoRepository = photoRepository;
        this.providerService = providerService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PhotoDTO> getPhotosByActivity(Long activityId) {
        /*List<Photo> photosByActivity = photoRepository.findByPhotos_Activity_Id(activityId);
        return photosByActivity.stream()
                .map(photo -> modelMapper.map(photo, PhotoDTO.class))
                .collect(Collectors.toList());*/
        return null;
    }

    @Override
    public PhotoDTO getPhotoById(Long id) {
        Photo photo = photoRepository.findById(id).orElse(null);
        return (photo != null) ? modelMapper.map(photo, PhotoDTO.class) : null;
    }

    @Override
    public PhotoDTO savePhoto(PhotoDTO photoDTO) {
        Photo photo = modelMapper.map(photoDTO, Photo.class);
        ProviderDTO providerDTO = providerService.getProviderById(photoDTO.getActivityId());
        if (providerDTO != null) {
            Provider provider = modelMapper.map(providerDTO, Provider.class);
            //photo.setActivity(activity);
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
