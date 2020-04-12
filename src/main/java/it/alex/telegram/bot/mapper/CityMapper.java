package it.alex.telegram.bot.mapper;

import it.alex.telegram.bot.dto.CityDTO;
import it.alex.telegram.bot.entity.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {

    City sourceToDestination(CityDTO source);

    CityDTO destinationToSource(City destination);
}
