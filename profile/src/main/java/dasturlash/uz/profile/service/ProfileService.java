package dasturlash.uz.profile.service;

import dasturlash.uz.profile.dto.*;
import dasturlash.uz.profile.dto.card.CardDTO;
import dasturlash.uz.profile.dto.transaction.NotificationDTO;
import dasturlash.uz.profile.entity.ProfileEntity;
import dasturlash.uz.profile.enums.ProfileRoleEnum;
import dasturlash.uz.profile.enums.ProfileStatus;
import dasturlash.uz.profile.exceptions.AppBadException;
import dasturlash.uz.profile.repository.ProfileRepository;
import dasturlash.uz.profile.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileRoleService profileRoleService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ProfileDTO create(ProfileDTO profile) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneNumberAndVisibleIsTrue(profile.getPhoneNumber());
        if (optional.isPresent()) {
            throw new AppBadException("User exists");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(profile.getName());
        entity.setSurname(profile.getSurname());

        entity.setPassword(bCryptPasswordEncoder.encode(profile.getPassword()));
        entity.setPhoneNumber(profile.getPhoneNumber());
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setVisible(Boolean.TRUE);
        profileRepository.save(entity); // save
        // role_save
        profileRoleService.merge(entity.getId(), profile.getRoleList());
        // result
        ProfileDTO response = toDTO(entity);
        return response;
    }

    public Boolean create2(ProfileRegistrationDTO profile) { // bu api auth-service dan chaqiriladi.
        Optional<ProfileEntity> optional = profileRepository.findByPhoneNumberAndVisibleIsTrue(profile.getPhoneNumber());
        if (optional.isPresent()) {
            throw new AppBadException("User exists");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setId(profile.getId());
        entity.setName(profile.getName());
        entity.setSurname(profile.getSurname());

        entity.setPassword(bCryptPasswordEncoder.encode(profile.getPassword()));
        entity.setPhoneNumber(profile.getPhoneNumber());
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setVisible(Boolean.TRUE);
        profileRepository.save(entity); // save
        // role_save
        profileRoleService.merge(entity.getId(), profile.getRoleList());
        // result
        return true;
    }


    public ProfileDTO update(String id, ProfileUpdateDTO dto) {
        ProfileEntity entity = get(id);
        // check username exists
        Optional<ProfileEntity> usernameOptional = profileRepository.findByPhoneNumberAndVisibleIsTrue(dto.getUsername());
        if (usernameOptional.isPresent() && !usernameOptional.get().getId().equals(id)) {
            throw new AppBadException("Username exists");
        }
        //
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhoneNumber(dto.getUsername());
        profileRepository.save(entity); // update
        // role_save
        profileRoleService.merge(entity.getId(), dto.getRoleList());
        // result
        ProfileDTO response = toDTO(entity);
        response.setRoleList(dto.getRoleList());
        return response;
    }

    public ProfileDTO getById(String id) {
        ProfileEntity profile = get(id);
        ProfileDTO dto = toDTO(profile);
        dto.setRoleList(profileRoleService.getByProfileId(id));
        return dto;
    }

    public ProfileDTO updateDetail(String currentProfileId, ProfileUpdateDetailDTO dto) {
        ProfileEntity entity = get(currentProfileId);
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        profileRepository.save(entity); // update
        return toDTO(entity);
    }

    public PageImpl<ProfileDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<ProfileEntity> result = profileRepository.findAllWithRoles(pageable);

        List<ProfileDTO> dtoList = new LinkedList<>();
        for (ProfileEntity profile : result.getContent()) {
            dtoList.add(toDTO(profile));
        }
        return new PageImpl<>(dtoList, pageable, result.getTotalElements());
    }

    public Boolean delete(String id) {
        ProfileEntity entity = get(id);
        entity.setVisible(true);
        profileRepository.save(entity); // or write a JPA query to update only visible
        return true;
    }

    public Boolean updatePassword(String currentProfileId, ProfileUpdatePasswordDTO dto) {
        ProfileEntity profile = get(currentProfileId);
        if (!bCryptPasswordEncoder.matches(dto.getCurrentPassword(), profile.getPassword())) {
            throw new AppBadException("Wrong password");
        }
        profile.setPassword(bCryptPasswordEncoder.encode(dto.getNewPassword()));
        profileRepository.save(profile);
        return true;
    }

    public void setStatusByUsername(ProfileStatus status, String username) {
        profileRepository.setStatusByPhoneNumber(status, username);
    }

    // Buni to'liq keyinroq qilamiz. Attach mavzusida
    public Boolean updatePhoto(String currentProfileId, ProfileUpdatePhotoDTO dto) {
        ProfileEntity profile = get(currentProfileId);
        profile.setPhotoId(dto.getPhotoId());
        profileRepository.save(profile);
        return true;
    }

    public ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public ProfileDTO toDTO(Object[] mapper) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId((String) mapper[0]);
        dto.setName((String) mapper[1]);
        dto.setSurname((String) mapper[2]);
        dto.setPhoneNumber((String) mapper[3]);
        if (mapper[4] != null) {
            dto.setStatus(ProfileStatus.valueOf((String) mapper[4]));
        }
        dto.setCreatedDate(MapperUtil.localDateTime(mapper[5]));
        dto.setRoleList(ProfileRoleEnum.values((String[]) mapper[6]));
        return dto;
    }

    public ProfileEntity get(String id) {
        return profileRepository.findByIdAndVisibleIsTrue(id).orElseThrow(() -> {
            throw new AppBadException("Profile not found");
        });
    }

    public ProfileDetailDTO getDetail(String id) {

        ProfileEntity entity = get(id);
        ProfileDetailDTO dto = new ProfileDetailDTO();
        // set detail
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setPhoneNumber(entity.getPhoneNumber());
        // set card list

        // set notification list

        return dto;
    }
}
