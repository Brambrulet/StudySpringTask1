package ru.volnenko.se.api.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import ru.volnenko.se.api.service.IDomainService;
import ru.volnenko.se.entity.Domain;

/**
 * @author Shmelev Dmitry
 */
public abstract class AsyncAbstractSaveCommand extends AsyncAbstractCustomCommand {

    protected abstract IDomainService getDomainService();
    protected abstract String getFileName();

    @Override
    public void execute() throws Exception {
        System.out.println(message());
        final Domain domain = new Domain();
        getDomainService().export(domain);
        final ObjectMapper objectMapper = new XmlMapper();
        final ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        final String json = objectWriter.writeValueAsString(domain);
        final byte[] data = json.getBytes(StandardCharsets.UTF_8);
        final File file = new File(getFileName());
        Files.write(file.toPath(), data);
        System.out.println("[OK]");
    }

}
