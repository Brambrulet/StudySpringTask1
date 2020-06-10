package ru.volnenko.se.command.data.bin;

import java.io.File;
import java.nio.file.Files;
import org.springframework.stereotype.Component;
import ru.volnenko.se.api.component.AsyncAbstractCommand;
import ru.volnenko.se.constant.DataConstant;

/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */
@Component("data-bin-clear")
public final class DataBinaryClearCommand implements AsyncAbstractCommand {

    @Override
    public String description() {
        return "Remove binary data.";
    }

    @Override
    public void execute() throws Exception {
        final File file = new File(DataConstant.FILE_BINARY);
        Files.deleteIfExists(file.toPath());
    }

}
