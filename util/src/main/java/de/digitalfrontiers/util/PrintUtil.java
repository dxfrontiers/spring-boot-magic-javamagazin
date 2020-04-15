package de.digitalfrontiers.util;

import org.springframework.boot.ansi.AnsiColor;

import java.io.PrintStream;
import java.util.List;

public class PrintUtil {

  /**
   * Prints the provided class names as a tree, grouped by package names to
   * {@link System#out system out}.
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

}
