package ru.volnenko.se.command.data.xml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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
@Component("data-xml-load")
@Setter(onMethod_=@Autowired)
@Getter
public final class DataXmlLoadCommandILoadCommand extends ILoadCommand {

    private IDomainService domainService;

    @Override
    public String description() {
        return "Load Domain from XML.";
    }

    @Override
    protected ObjectMapper newObjectMapper() {
        return new XmlMapper();
    }

    @Override
    protected String message() {
        return "[LOAD XML DATA]";
    }
}
