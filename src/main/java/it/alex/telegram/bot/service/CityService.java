package it.alex.telegram.bot.service;

import it.alex.telegram.bot.dto.CityDTO;
import it.alex.telegram.bot.dto.UpdateCityDataDTO;
import it.alex.telegram.bot.entity.City;
import it.alex.telegram.bot.exception.BotCityNotFoundException;
import it.alex.telegram.bot.mapper.CityMapper;
import it.alex.telegram.bot.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityMapper cityMapper;

    public String getCityDescription(String cityName) {
        Optional<City> city = cityRepository.findByName(cityName);
        if (city.isPresent()) {
            return city.get().getName() + " -> " + city.get().getDescription();
        }
        return "No such city was found";
    }

    @Transactional
    public void addCity(CityDTO newCityRequest) {
        City city = cityMapper.sourceToDestination(newCityRequest);
        cityRepository.save(city);
    }

    public CityDTO getCity(Long cityId) throws BotCityNotFoundException {
        Optional<City> city = cityRepository.findById(cityId);
        if (city.isPresent()) {
            return cityMapper.destinationToSource(city.get());
        }
        throw new BotCityNotFoundException("No such city was found");
    }

    @Transactional
    public void updateCity(Long cityId, UpdateCityDataDTO updateRequest) throws BotCityNotFoundException {
        City city = cityRepository.findById(cityId).orElseThrow(() -> new BotCityNotFoundException("City not found"));
        city.setDescription(updateRequest.getDescription());
        cityRepository.save(city);
    }


    public List<CityDTO> getAllCity() {
        return cityRepository.findAll().stream().map(cityMapper::destinationToSource).collect(Collectors.toList());
    }

    public void delCity(Long cityId) throws BotCityNotFoundException {
        City city = cityRepository.findById(cityId).orElseThrow(() -> new BotCityNotFoundException("City not found"));
        deleteCity(city);
    }

    @Transactional
    public void deleteCity(City city) {
        cityRepository.delete(city);
    }
}
