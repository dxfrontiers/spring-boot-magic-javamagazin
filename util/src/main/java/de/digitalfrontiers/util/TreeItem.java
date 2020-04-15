package de.digitalfrontiers.util;

import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TreeItem {
  private final String name;
  private final List<TreeItem> children;
  private final List<String> values;

  public TreeItem(@NonNull String name) {
    this.name = name;
    children = new ArrayList<>(2);
    values = new ArrayList<>(2);
  }

  @NonNull
  public String getName() {
    return name;
  }

  @NonNull
  public List<TreeItem> getChildren() {
    return children;
  }

  @NonNull
  public List<String> getValues() {
    return values;
  }

  @NonNull
  public TreeItem getOrCreateChild(String name) {
    for (TreeItem child : getChildren()) {
      if (child.getName().equals(name))
        return child;
    }
    final var created = new TreeItem(name);
    getChildren().add(created);
    return created;
  }

  public void addValue(String value) {
    getValues().add(value);
  }

  public void sort() {
    Collections.sort(values);
    children.sort(new TreeItemComparator());
    children.forEach(TreeItem::sort);
  }

  protected static class TreeItemComparator implements Comparator<TreeItem> {

    @Override
    public int compare(TreeItem o1, TreeItem o2) {
      final var n1 = o1.getName();
      final var n2 = o2.getName();
      return n1.compareTo(n2);
    }
  }
}
