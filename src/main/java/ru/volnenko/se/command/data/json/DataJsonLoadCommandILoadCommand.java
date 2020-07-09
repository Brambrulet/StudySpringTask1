package ru.volnenko.se.command.data.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.volnenko.se.api.service.IDomainService;
import ru.volnenko.se.api.command.ILoadCommand;

/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */
@Component("data-json-load")
@Setter(onMethod_=@Autowired)
@Getter
public final class DataJsonLoadCommandILoadCommand extends ILoadCommand {

    private IDomainService domainService;

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
