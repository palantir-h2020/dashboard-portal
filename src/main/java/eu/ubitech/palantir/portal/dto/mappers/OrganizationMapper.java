package eu.ubitech.palantir.portal.dto.mappers;

import org.mapstruct.Mapper;

import eu.ubitech.palantir.portal.dto.OrganizationDto;
import eu.ubitech.palantir.portal.model.Organization;

import java.util.List;

@Mapper(componentModel = "cdi", uses = { EnumMapper.class })
public interface OrganizationMapper {

  OrganizationDto toOrganizationDto(Organization organization);

  Organization toOrganization(OrganizationDto organizationDto);

  List<OrganizationDto> toOrganizationDto(List<Organization> organizations);

}
