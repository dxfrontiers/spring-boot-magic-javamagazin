package de.digitalfrontiers.util.mina;

import org.apache.sshd.server.channel.ChannelSession;
import org.apache.sshd.server.command.Command;
import org.apache.sshd.server.shell.ShellFactory;

import java.io.IOException;

public class NoopShellFactory implements ShellFactory {
  @Override
  public Command createShell(ChannelSession channel) throws IOException {
    return new NoopCommand(channel.getExecutorService());
  }
}
