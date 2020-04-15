package de.digitalfrontiers.util.mina;

import org.apache.sshd.common.util.threads.CloseableExecutorService;
import org.apache.sshd.server.command.AbstractCommandSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiElement;
import org.springframework.boot.ansi.AnsiStyle;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public abstract class AbstractCommand extends AbstractCommandSupport {

  // ansi encoding constants. Used by encodeAnsi(...)
  private static final byte[] ENCODE_START = {'\033', '['};
  private static final byte[] ENCODE_END = {'m'};
  private static final byte[] RESET = ("0;" + AnsiColor.DEFAULT.toString())
      .getBytes(StandardCharsets.US_ASCII);
  private static final byte[] ENCODE_JOIN = {';'};


  private static final Logger LOGGER = LoggerFactory
      .getLogger(AbstractCommand.class);

  protected final Charset charset;
  protected Reader inReader;

  public AbstractCommand(String command, CloseableExecutorService executorService) {
    this(command, executorService, StandardCharsets.UTF_8);
  }

  public AbstractCommand(String command, CloseableExecutorService executorService, Charset charset) {
    super(command, executorService);
    this.charset = charset;
  }

  @Override
  public void setInputStream(InputStream in) {
    super.setInputStream(in);
    if (in == null)
      inReader = null;
    else
      inReader = new InputStreamReader(getInputStream(), charset);
  }

  protected void print(String s) {
    try {
      out.write(encode(s));
      out.flush();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  protected void print(char c) {
    try {
      out.write(encode(new String(new char[]{c})));
      out.flush();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  protected void print(Object... elements) {
    try {
      encodeAnsi(out, elements);
      out.flush();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  protected void println(String s) {
    try {
      out.write(encode(s));
      newLine();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  protected void println(Object... elements) {
    try {
      encodeAnsi(out, elements);
      newLine();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  protected void println() {
    try {
      newLine();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void newLine() throws IOException {
    out.write('\r');
    out.write('\n');
    out.flush();
  }


  private byte[] encode(String s) {
    return StandardCharsets.UTF_8.encode(s).array();
  }

  protected String prompt(String msg) {
    print(AnsiColor.BRIGHT_BLUE, msg);

    final StringBuilder sb = new StringBuilder();
    try {

      writeAnsi(out, AnsiColor.BRIGHT_WHITE, AnsiStyle.BOLD);

      int read;
      while ((read = inReader.read()) != -1) {

        if (read == '\n' || read == '\r') {
          println();
          return sb.toString();
        }

        print((char) read);
        sb.append((char) read);
      }
    } catch (IOException e) {
      LOGGER.error("failed to read", e);
    } finally {
      try {
        writeAnsiReset(out);
      } catch (IOException e) {
        // ignore
      }
    }
    return null;
  }

  private void writeAnsi(OutputStream out, AnsiElement... elements)
      throws IOException {

    if (elements == null || elements.length == 0)
      return;

    out.write(ENCODE_START);
    out.write(encode(elements[0].toString()));

    for (int i = 1; i < elements.length; i++) {
      out.write(ENCODE_JOIN);
      out.write(encode(elements[i].toString()));
    }
    out.write(ENCODE_END);
  }

  private void writeAnsiReset(OutputStream out) throws IOException {
    out.write(ENCODE_START);
    out.write(RESET);
    out.write(ENCODE_END);
  }

  private void encodeAnsi(OutputStream out, Object... elements)
      throws IOException {

    // this is a copy of the AnsiOutput.buildEnabled. Unfortunately,
    // AnsiOutput.buildEnabled is strictly designed to be used within the output
    // of the jvm, not for writing to a arbitrary output stream.

    boolean writingAnsi = false;
    boolean containsEncoding = false;
    for (Object element : elements) {
      if (element instanceof AnsiElement) {
        containsEncoding = true;
        if (!writingAnsi) {
          out.write(ENCODE_START);
          writingAnsi = true;
        } else {
          out.write(ENCODE_JOIN);
        }
      } else {
        if (writingAnsi) {
          out.write(ENCODE_END);
          writingAnsi = false;
        }
      }
      out.write(encode("" + element));
    }
    if (containsEncoding) {
      out.write(writingAnsi ? ENCODE_JOIN : ENCODE_START);
      out.write(RESET);
      out.write(ENCODE_END);
    }
  }

}
