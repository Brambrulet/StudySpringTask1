package ru.volnenko.se.command.data.xml;

import java.io.File;
import java.nio.file.Files;
import org.springframework.stereotype.Component;
import ru.volnenko.se.api.component.AsyncAbstractCommand;
import ru.volnenko.se.constant.DataConstant;

/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */
@Component("data-xml-clear")
public final class DataXmlClearCommand implements AsyncAbstractCommand {

    @Override
    public String description() {
        return "Remove XML file.";
    }

    @Override
    public void execute() throws Exception {
        final File file = new File(DataConstant.FILE_XML);
        Files.deleteIfExists(file.toPath());
    }

}
