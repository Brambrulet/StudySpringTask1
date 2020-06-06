package ru.volnenko.se.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * @author Denis Volnenko
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Domain {

    private List<Project> projects = new ArrayList<>();

    private List<Task> tasks = new ArrayList<>();

}
