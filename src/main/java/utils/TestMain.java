package utils;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class TestMain {

    public static void main(String[] args) {

        try {
            Terminal terminal = TerminalBuilder.builder()
                    .system(true)
                    .build();

            terminal.writer().println("dfsdfdsfsdfdsf");
//            Helper.sleep(1);
//            terminal.puts(InfoCmp.Capability.clear_screen);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
