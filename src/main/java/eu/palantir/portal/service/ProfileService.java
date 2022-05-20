package eu.palantir.portal.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import eu.palantir.portal.dto.ProfileDto;
import eu.palantir.portal.dto.mappers.AuthMapper;
import eu.palantir.portal.exceptions.InternalServerErrorException;
import eu.palantir.portal.model.User;
import eu.palantir.portal.service.auth.AuthService;

import java.io.IOException;

@ApplicationScoped
public class ProfileService {

    @Inject
    AuthService authService;

    @Inject
    AuthMapper authMapper;

    @Transactional
    public ProfileDto getProfile(Long userId) {
        return authMapper.toProfileDto(User.findById(userId));
    }

    @Transactional
    public void updateAvatar(Long userId, String avatar) {
        try {
            User user = User.findById(userId);
            authService.updateUserAttribute(user.getUsername(), "avatar", avatar);
        } catch (IOException ex) {
            throw new InternalServerErrorException();
        }
    }

    @Transactional
    public void updateTimezone(Long userId, String timezone) {
        try {
            User user = User.findById(userId);
            authService.updateUserAttribute(user.getUsername(), "timezone", timezone);
        } catch (IOException ex) {
            throw new InternalServerErrorException();
        }
    }

    @Transactional
    public void updateDateFormat(Long userId, String dateFormat) {
        try {
            User user = User.findById(userId);
            authService.updateUserAttribute(user.getUsername(), "dateformat", dateFormat);
        } catch (IOException ex) {
            throw new InternalServerErrorException();
        }
    }

}
