package de.digitalfrontiers.util;

import org.springframework.boot.ansi.AnsiOutput;

import java.io.PrintStream;
import java.lang.management.ManagementFactory;

public class AnsiPrintStream {

  private static final String IDEA_RT_JAR = "idea_rt.jar";

  static {
    // the default ansi detection fails when running using intellij. To
    // circumvent that, we check if the application has been started using
    // IntelliJ and always enable the ansi encoding.
    //
    // Background:
    // IntelliJ starts processes using Runtime.exec. This doesn't allocate a
    // pty, which causes System.console() to be null.
    // (See https://youtrack.jetbrains.com/issue/IDEABKL-5949)
    if (isRunByIntelliJ()) {
      AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
    }
  }

  private static boolean isRunByIntelliJ() {

    // check if the input arguments contain the idea runtime jar
    final var runtimeMXBean = ManagementFactory.getRuntimeMXBean();
    if (runtimeMXBean != null) {
      final var args = runtimeMXBean.getInputArguments();

      for (String arg : args) {
        if (arg.toLowerCase().contains(IDEA_RT_JAR))
          return true;
      }
    }

    return false;
  }


  private final PrintStream target;

  public AnsiPrintStream(PrintStream target) {
    this.target = target;
  }

  public void flush() {
    target.flush();
  }

  public void close() {
    target.close();
  }

  public void print(Object... elements) {
    target.print(AnsiOutput.toString(elements));
  }

  public void println() {
    target.println();
  }

  public void println(Object... elements) {
    target.println(AnsiOutput.toString(elements));
  }

  public void print(boolean b) {
    target.print(b);
  }

  public void print(char c) {
    target.print(c);
  }

  public void print(int i) {
    target.print(i);
  }

  public void print(long l) {
    target.print(l);
  }

  public void print(float f) {
    target.print(f);
  }

  public void print(double d) {
    target.print(d);
  }

  public void print(char[] s) {
    target.print(s);
  }

  public void print(String s) {
    target.print(s);
  }

  public void println(boolean x) {
    target.println(x);
  }

  public void println(char x) {
    target.println(x);
  }

  public void println(int x) {
    target.println(x);
  }

  public void println(long x) {
    target.println(x);
  }

  public void println(float x) {
    target.println(x);
  }

  public void println(double x) {
    target.println(x);
  }

  public void println(char[] x) {
    target.println(x);
  }

  public void println(String x) {
    target.println(x);
  }


}
