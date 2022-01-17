package eu.palantir.portal.dto.mappers;

import eu.palantir.portal.dto.SignUpDto;
import eu.palantir.portal.model.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    User toUser(SignUpDto signUpDto);

}
