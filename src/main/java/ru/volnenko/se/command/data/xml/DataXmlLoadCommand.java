package ru.volnenko.se.command.data.xml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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
public final class DataXmlLoadCommand extends AbstractLoadCommand {
    private IDomainService domainService;

    @Override
    public String command() {
        return "data-xml-load";
    }

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
