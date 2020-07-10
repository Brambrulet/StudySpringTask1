package ru.volnenko.se.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */

@Entity
@Table(name = "T_DOMAIN")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Domain {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private List<Project> projects = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id")
    private List<Task> tasks = new ArrayList<>();

}
