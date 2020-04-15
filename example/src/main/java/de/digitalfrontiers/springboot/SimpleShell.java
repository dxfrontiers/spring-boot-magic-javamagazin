package de.digitalfrontiers.springboot;

import de.digitalfrontiers.util.mina.AbstractCommand;
import org.apache.sshd.common.util.threads.CloseableExecutorService;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiStyle;

public class SimpleShell extends AbstractCommand {

  protected SimpleShell(CloseableExecutorService executorService) {
    super("shell", executorService);
  }

  @Override
  public void run() {

    println("Simple Example Shell");
    println("====================");

    String cmd;
    while ((cmd = prompt("#> ")) != null) {

      switch (cmd) {
        case "exit":
          println();
          println(AnsiColor.BRIGHT_CYAN, "Goodbye cruel world!");
          println();
          getExitCallback().onExit(0);
          return;
        case "hello":
          println("Hello, how are you?");
          break;
        case "help":
          println("");
          println("known commands:");
          println("  ",
              AnsiStyle.BOLD, AnsiColor.BRIGHT_WHITE, "hello",
              AnsiColor.DEFAULT, AnsiStyle.NORMAL, "    will greet you");
          println("  ",
              AnsiStyle.BOLD, AnsiColor.BRIGHT_WHITE, "help",
              AnsiColor.DEFAULT, AnsiStyle.NORMAL,
              "     shows this help message");
          println("  ",
              AnsiStyle.BOLD, AnsiColor.BRIGHT_WHITE, "exit",
              AnsiColor.DEFAULT, AnsiStyle.NORMAL,
              "     exit and close the connection");
          break;
        default:
          println(" => unknown command: " + cmd);
          println();
      }
    }

    // when reaching here, reading failed
    getExitCallback().onExit(-1);
  }

}
