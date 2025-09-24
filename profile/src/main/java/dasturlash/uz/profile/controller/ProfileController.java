package dasturlash.uz.profile.controller;

import dasturlash.uz.profile.dto.*;
import dasturlash.uz.profile.service.ProfileService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
    @Autowired
    private ProfileService profileService;
    @Value("${accounts.message}")
    private String message;

    @PostMapping("")
    public ResponseEntity<ProfileDTO> create(@Valid @RequestBody ProfileDTO dto) {
        return ResponseEntity.ok(profileService.create(dto));
    }

    @PostMapping("/registration") // bu api auth-service dan chaqiriladi.
    public ResponseEntity<Boolean> create2(@Valid @RequestBody ProfileRegistrationDTO dto) {
        return ResponseEntity.ok(profileService.create2(dto));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProfileDTO> update(@PathVariable("id") String id,
                                             @Valid @RequestBody ProfileUpdateDTO dto) { // ADMIN
        return ResponseEntity.ok(profileService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> byId(@PathVariable("id") String id) { // ADMIN
        return ResponseEntity.ok(profileService.getById(id));
    }

    @PutMapping("/detail")
    public ResponseEntity<ProfileDTO> updateDetail(
            @RequestHeader("ProfileId") String currentProfileId,
            @Valid @RequestBody ProfileUpdateDetailDTO dto) { // ANY
        return ResponseEntity.ok(profileService.updateDetail(currentProfileId, dto));
    }

    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<ProfileDTO>> pagination(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(profileService.pagination(page - 1, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String id) {
        return ResponseEntity.ok(profileService.delete(id));
    }

    @PutMapping("/password")
    public ResponseEntity<Boolean> password(@RequestHeader("ProfileId") String currentProfileId,
                                            @Valid @RequestBody ProfileUpdatePasswordDTO dto) {
        return ResponseEntity.ok(profileService.updatePassword(currentProfileId, dto));
    }

    @GetMapping("/message")
    public String getMessage() {
        return message;
    }

    public String getMessageFallback(Throwable throwable) {
        return "Defult message mazgi";
    }


    @GetMapping("/{id}/detail")
    public ResponseEntity<ProfileDetailDTO> profileDetail(@PathVariable("id") String id) {
        return ResponseEntity.ok(profileService.getDetail(id));
    }
}
