package eu.ubitech.palantir.portal.dto.mappers;

import org.mapstruct.Mapper;

import eu.ubitech.palantir.portal.dto.EnumDto;
import eu.ubitech.palantir.portal.model.Gender;

@Mapper(componentModel = "cdi")
public interface EnumMapper {

  default EnumDto toDto(Gender source) {
    if (source == null) {
      return null;
    }
    return new EnumDto(source.name(), source.getDescription());
  }

  default Gender toGender(EnumDto enumDto) {
    if (enumDto == null || enumDto.getId() == null) {
      return null;
    }
    return Gender.valueOf(enumDto.getId());
  }

}
