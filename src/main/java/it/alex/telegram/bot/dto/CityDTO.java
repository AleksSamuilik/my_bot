package it.alex.telegram.bot.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CityDTO {

    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String description;
}
