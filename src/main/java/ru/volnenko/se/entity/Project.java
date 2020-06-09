package ru.volnenko.se.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import lombok.Data;

/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Project implements Serializable {

    private String id = UUID.randomUUID().toString();

    private String name = "";

    private Date dateBegin;

    private Date dateEnd;

    private Date created = new Date();

    public void test() {
        System.out.println("HELLO");
    }

}
