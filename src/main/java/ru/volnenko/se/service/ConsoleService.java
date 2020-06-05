package ru.volnenko.se.service;

import java.util.Scanner;
import org.springframework.stereotype.Service;
import ru.volnenko.se.api.service.IConsoleService;

@Service
public class ConsoleService implements IConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String nextLine() {
        return scanner.nextLine();
    }

    @Override
    public Integer nextInteger() {
        final String value = nextLine();
        if (value == null || value.isEmpty()) return null;
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return null;
        }
    }

}
