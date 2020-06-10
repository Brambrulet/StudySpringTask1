package ru.volnenko.se.api.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import ru.volnenko.se.api.service.IDomainService;
import ru.volnenko.se.constant.DataConstant;
import ru.volnenko.se.entity.Domain;

/**
 * @author Shmelev Dmitry
 */
public abstract class AsyncAbstractLoadCommand extends AsyncAbstractCustomCommand {

    protected abstract ObjectMapper newObjectMapper();

    protected abstract IDomainService getDomainService();

    @Override
    public void execute() throws Exception {
        System.out.println(message());
        final File file = new File(DataConstant.FILE_XML);
        if (!exists(file)) {
            return;
        }
        final byte[] bytes = Files.readAllBytes(file.toPath());
        final String json = new String(bytes, StandardCharsets.UTF_8);
        final ObjectMapper objectMapper = newObjectMapper();
        final Domain domain = objectMapper.readValue(json, Domain.class);
        getDomainService().load(domain);
        System.out.println("[OK]");
    }

    private boolean exists(final File file) {
        if (file == null) {
            return false;
        }
        final boolean check = file.exists();
        if (!check) {
            System.out.println("FILE NOT FOUND");
        }
        return check;
    }
}
