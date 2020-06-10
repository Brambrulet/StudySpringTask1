package ru.volnenko.se.component;

import java.util.Scanner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.volnenko.se.api.component.IInputProvider;

/**
 * @author Shmelev Dmitry
 */
@Component
public class InputProvider implements IInputProvider {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String nextLine() {
        return scanner.nextLine();
    }

    @Override
    public Integer nextInteger() {
        final String value = nextLine();
        if (StringUtils.isEmpty(value)) return null;
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return null;
        }
    }

}
