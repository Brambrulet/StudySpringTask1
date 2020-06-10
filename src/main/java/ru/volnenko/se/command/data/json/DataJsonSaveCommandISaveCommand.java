package ru.volnenko.se.command.data.json;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.volnenko.se.api.service.IDomainService;
import ru.volnenko.se.api.command.ISaveCommand;
import ru.volnenko.se.constant.DataConstant;

/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */
@Component("data-json-save")
@Setter(onMethod_=@Autowired)
@Getter
public final class DataJsonSaveCommandISaveCommand extends ISaveCommand {

    private IDomainService domainService;

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
