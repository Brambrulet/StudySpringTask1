package ru.volnenko.se.command.data.json;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.volnenko.se.api.service.IDomainService;
import ru.volnenko.se.command.AbstractSaveCommand;
import ru.volnenko.se.constant.DataConstant;

/**
 * @author Denis Volnenko
 */
@Service
@Setter(onMethod=@__({@Autowired}))
@Getter
public final class DataJsonSaveCommand extends AbstractSaveCommand {
    private IDomainService domainService;

    @Override
    public String command() {
        return "data-json-save";
    }

    @Override
    public String description() {
        return "Save Domain to JSON.";
    }

    @Override
    protected String getFileName() {
        return DataConstant.FILE_JSON;
    }

    @Override
    protected String message() {
        return "[DATA JSON SAVE]";
    }
}
