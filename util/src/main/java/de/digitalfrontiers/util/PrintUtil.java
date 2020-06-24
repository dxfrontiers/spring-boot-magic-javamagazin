package de.digitalfrontiers.util;

import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiStyle;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public class PrintUtil {

  /**
   * Prints the provided class names as a tree, grouped by package names to {@link System#out system out}.
   */
  public static void printClassTree(List<String> classNames) {
    printClassTree(classNames, System.out);
  }

  /**
   * Prints the provided class names as a tree, grouped by package names.
   */
  public static void printClassTree(List<String> classNames, PrintStream out) {

    final TreeItem treeRoot = new TreeItem("");

    classNames.forEach(className -> {
      final var segments = className.split("\\.");
      TreeItem cur = treeRoot;
      for (int i = 0; i < segments.length; i++) {
        boolean leaf = i == segments.length - 1;
        if (leaf)
          cur.addValue(segments[i]);
        else
          cur = cur.getOrCreateChild(segments[i]);
      }
    });

    printClassTree(treeRoot, out);

  }

  /**
   * Prints the provided class names as a tree, grouped by package names.
   */
  public static void printClassTree(TreeItem tree, PrintStream out) {
    printClassTree(tree, new AnsiPrintStream(out));
  }

  public static void printClassTree(TreeItem treeRoot, AnsiPrintStream out) {

    printTreeNode(out, "", treeRoot);
  }

  private static void printTreeNode(AnsiPrintStream out, String indent, TreeItem node) {
    TreeItem cur = node;
    var sb = new StringBuilder();
    sb.append(cur.getName());
    while (cur.getChildren().size() == 1) {
      cur = cur.getChildren().get(0);
      if (sb.length() > 0)
        sb.append('.');
      sb.append(cur.getName());
    }

    out.println(indent, AnsiColor.WHITE, sb.toString());
    for (String value : cur.getValues()) {
      out.println(indent, "  - ", AnsiColor.BLUE, value);
    }
    for (TreeItem child : cur.getChildren()) {
      printTreeNode(out, indent + "  ", child);
    }
  }

  /**
   * Prints a caption string with underline to {@link System#out}. This method prints the provided {@code caption} in
   * {@link AnsiColor#BLUE blue}, followed by a line of dashes matching the length of the {@code caption}.
   * <p>
   * Example:
   * <code>
   * Caption Printed on the Console ------------------------------
   * </code>
   *
   * @param caption the caption to be printed
   */
  public static void printCaption(String caption) {
    final AnsiPrintStream out = new AnsiPrintStream(System.out);
    out.println();
    out.println(AnsiColor.BLUE, caption, AnsiColor.DEFAULT);
    out.println(createStringOf(caption.length(), '-'));
    out.println();
  }

  /**
   * Prints a key with associated value(s) to {@link System#out}. If more than one value has been specified, the
   * resulting output will be formatted as a list, prefixed with a dash for each entry.
   * <p>
   * Example:
   * <code>
   * Key: - value1 - value2 - value3
   * </code>
   *
   * @param key
   * @param values
   */
  public static void printKeyValue(String key, String... values) {

    final AnsiPrintStream out = new AnsiPrintStream(System.out);

    final String indent = createSpaceIndentation(key.length() + 1);
    out.print(AnsiColor.BLUE, key, " ");

    if (values == null || values.length == 0) {
      out.print(AnsiStyle.ITALIC, AnsiColor.WHITE);
      out.print(values == null ? "<null>" : "<empty>");
      out.println(AnsiStyle.NORMAL, AnsiColor.DEFAULT);
    } else if (values.length == 1)
      out.println(AnsiColor.WHITE, values[0], AnsiColor.DEFAULT);
    else {
      out.println(AnsiColor.WHITE, "- ", values[0], AnsiColor.DEFAULT);
      for (int i = 1; i < values.length; i++) {
        out.println(indent, AnsiColor.WHITE, "- ", values[i], AnsiColor.DEFAULT);
      }
    }

  }

  private static String createSpaceIndentation(int count) {
    return createStringOf(count, ' ');
  }

  private static String createStringOf(int count, char c) {
    final char[] raw = new char[count];
    Arrays.fill(raw, c);
    return new String(raw);
  }
}
