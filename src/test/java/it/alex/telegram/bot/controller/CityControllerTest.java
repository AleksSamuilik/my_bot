package it.alex.telegram.bot.controller;

import it.alex.telegram.bot.entity.City;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log
public class CityControllerTest extends AbstractControllerTest {
    private final static String DEFAULT_CITY = "Brest";
    private final static String DEFAULT_DESCRIPTION = "Visiting the Brest fortress";

    @Test
    @SneakyThrows
    public void testAddNewCity() {
        deleteBrest();
        mockMvc.perform(post("/cities/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"name\": \"" + DEFAULT_CITY + "\",\n" +
                        "    \"description\": \"" + DEFAULT_DESCRIPTION + "\"\n" +
                        "}"))
                .andExpect(status().isCreated());
    }

    @Test
    @SneakyThrows
    public void testGetCityId() {
        mockMvc.perform(get("/cities/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "\"id\":1,\n" +
                        " \"name\":\"Minsk\",\n" +
                        "\"description\":\"Visit the national library in the shape of a diamond\"\n" +
                        "}"));
    }

    @Test
    @SneakyThrows
    public void testGetAllCity() {
        deleteBrest();
        mockMvc.perform(get("/cities"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "   {\n" +
                        "      \"id\":1,\n" +
                        "      \"name\":\"Minsk\",\n" +
                        "      \"description\":\"Visit the national library in the shape of a diamond\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "      \"id\":2,\n" +
                        "      \"name\":\"Tallinn\",\n" +
                        "      \"description\":\"Take a walk around Toompea Castle and visit the Kumu Museum. And leave the Tallinn TV Tower for another life\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "      \"id\":3,\n" +
                        "      \"name\":\"Pinsk\",\n" +
                        "      \"description\":\"Go to Butrimovich’s palace. Then visit one of the oldest museums in the Republic of Belarus. Museum of Belorussian Polesie. Well, if you decide to swim in Pina, then go to Golden Sands.\"\n" +
                        "   }\n" +
                        "]"
                ));
    }

    @Test
    @SneakyThrows
    public void testUpdateCityData() {
        final Long cityId;
        Optional<City> city = cityRepository.findByName(DEFAULT_CITY);
        if (city.isEmpty()) {
            cityId = createBrest().getId();
        } else {
            cityId = city.get().getId();
        }
        log.info("City ID " + cityId.toString());
        mockMvc.perform(put("/cities/" + cityId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"description\": \"Update description\"\n" +
                        "}"))
                .andExpect(status().isOk());
    }


    @Test
    @SneakyThrows
    public void testDelCity() {
        final Long cityId;
        Optional<City> city = cityRepository.findByName(DEFAULT_CITY);
        if (city.isEmpty()) {
            cityId = createBrest().getId();
        } else {
            cityId = city.get().getId();
        }
        mockMvc.perform(delete("/cities/" + cityId))
                .andExpect(status().isOk());
    }

    private void deleteBrest() {
        final Optional<City> city = cityRepository.findByName(DEFAULT_CITY);
        if (city.isPresent()) {
            cityRepository.delete(city.get());
        }
    }

    private City createBrest() {
        final City city = new City();
        city.setName(DEFAULT_CITY);
        city.setDescription(DEFAULT_DESCRIPTION);
        return cityRepository.save(city);
    }

}
