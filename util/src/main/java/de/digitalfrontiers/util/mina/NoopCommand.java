package de.digitalfrontiers.util.mina;

import org.apache.sshd.common.util.threads.CloseableExecutorService;

public class NoopCommand extends AbstractCommand {
  public NoopCommand(CloseableExecutorService executor) {
    super("noop-shell", executor);
  }

  @Override
  public void run() {
    println("No shell configured");
    getExitCallback().onExit(0);
  }
}
