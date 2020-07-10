package ru.volnenko.se.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */

@Entity
@Table(name = "T_TASK")
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Task implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private String id = UUID.randomUUID().toString();

    private String projectId;

    private String name = "";

    private Date dateBegin;

    private Date dateEnd;

    @ManyToOne(optional = false)
    @JoinColumn(name = "domain_id")
    private Domain domain;

    public void test() {
        System.out.println("HELLO");
    }

}
