package eu.palantir.portal.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import eu.palantir.portal.dto.events.ActionDto;
import eu.palantir.portal.dto.message.ActionNotification;
import eu.palantir.portal.model.Action;

import org.mapstruct.InjectionStrategy;

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ActionMapper {

    ActionDto toActionDto(Action action);

    @Mappings({
            @Mapping(target = "targetIPs", source = "onIps"),
            @Mapping(target = "name", source = "actionName"),
            @Mapping(target = "description", source = "actionDescription"),
    })
    Action toAction(ActionNotification actionNotification);
}
