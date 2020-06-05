package ru.volnenko.se.command.data.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.volnenko.se.api.service.IDomainService;
import ru.volnenko.se.command.AbstractLoadCommand;

/**
 * @author Denis Volnenko
 */
@Service
@Setter(onMethod=@__({@Autowired}))
@Getter
public final class DataJsonLoadCommand extends AbstractLoadCommand {
    private IDomainService domainService;

    @Override
    public String command() {
        return "data-json-load";
    }

    @Override
    public String description() {
        return "Load Domain from JSON.";
    }

    @Override
    protected ObjectMapper newObjectMapper() {
        return new ObjectMapper();
    }

    @Override
    protected String message() {
        return "[LOAD JSON DATA]";
    }
}
