package ru.volnenko.se.command.data.json;

import java.io.File;
import java.nio.file.Files;
import org.springframework.stereotype.Component;
import ru.volnenko.se.api.component.AsyncAbstractCommand;
import ru.volnenko.se.constant.DataConstant;

/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */
@Component("data-json-clear")
public final class DataJsonClearCommand implements AsyncAbstractCommand {

    @Override
    public String description() {
        return "Remove JSON file.";
    }

    @Override
    public void execute() throws Exception {
        final File file = new File(DataConstant.FILE_JSON);
        Files.deleteIfExists(file.toPath());
    }

}
