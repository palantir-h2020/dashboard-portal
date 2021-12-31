package eu.ubitech.palantir.portal.dto.mappers;

import org.mapstruct.Mapper;

import eu.ubitech.palantir.portal.dto.PersonDto;
import eu.ubitech.palantir.portal.model.Person;

import java.util.List;

@Mapper(componentModel = "cdi", uses = { EnumMapper.class })
public interface PersonMapper {

  PersonDto toPersonDto(Person person);

  Person toPerson(PersonDto personDto);

  List<PersonDto> toPersonDto(List<Person> persons);

}
