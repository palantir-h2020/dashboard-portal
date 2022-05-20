package eu.palantir.portal.dto.mappers;

import org.keycloak.representations.AccessTokenResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import eu.palantir.portal.dto.AuthToken;
import eu.palantir.portal.dto.ProfileDto;
import eu.palantir.portal.model.User;

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AuthMapper {

    AuthToken toToken(AccessTokenResponse accessTokenResponse);

    ProfileDto toProfileDto(User user);

}
