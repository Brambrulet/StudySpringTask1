package ru.volnenko.se.component;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.volnenko.se.api.component.IInputProvider;
import ru.volnenko.se.api.event.CommandEvent;
import ru.volnenko.se.api.event.InputRequestEvent;
import ru.volnenko.se.api.event.InputResponseEvent;
import ru.volnenko.se.constant.DataConstant;

/**
 * @author Shmelev Dmitry
 */
@Component
public class InputProvider implements IInputProvider {

    private final Scanner scanner = new Scanner(System.in);
    @Setter(onMethod_=@Autowired)
    private ApplicationEventPublisher publisher;
    private Deque<String> requestQueue = new LinkedList<>();

    @Override
    public void start() {
        System.out.println("*** WELCOME TO TASK MANAGER ***");

        while (true) {
            String inputValue = scanner.nextLine();

            if (StringUtils.isEmpty(inputValue)) {
                continue;
            }
            if (!publishEvent(inputValue)) {
                break;
            }
        }
    }

    @Override
    public void onInputRequest(InputRequestEvent event) {
        requestQueue.add(event.getReturnAddress());
    }

    private boolean publishEvent(String inputValue) {
        String pendingCommand = requestQueue.pollLast();

        if (!StringUtils.isEmpty(pendingCommand)) {
            publisher.publishEvent(new InputResponseEvent(this, pendingCommand, inputValue));
            return true;
        } else if (DataConstant.EXIT.equals(inputValue)) {
            return false;
        }

        publisher.publishEvent(new CommandEvent(this, inputValue));
        return true;
    }
}
