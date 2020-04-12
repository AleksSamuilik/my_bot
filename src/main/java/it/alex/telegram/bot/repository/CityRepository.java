package it.alex.telegram.bot.repository;

import it.alex.telegram.bot.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository  extends JpaRepository<City,Long> {
    Optional<City> findByName(String name);
}
