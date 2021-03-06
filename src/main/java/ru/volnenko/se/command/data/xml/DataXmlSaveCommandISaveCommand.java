package ru.volnenko.se.command.data.xml;

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
@Component("data-xml-save")
@Setter(onMethod_=@Autowired)
@Getter
public final class DataXmlSaveCommandISaveCommand extends ISaveCommand {

    private IDomainService domainService;

    @Override
    public String description() {
        return "Save Domain to XML.";
    }

    @Override
    protected String message() {
        return "[DATA XML SAVE]";
    }

    @Override
    protected String getFileName() {
        return DataConstant.FILE_XML;
    }
}
