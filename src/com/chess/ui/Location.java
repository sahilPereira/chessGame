package com.chess.ui;

public class Location {
  public int row;
  public int column;

  public Location(int row, int column) {
    this.row = row;
    this.column = column;
  }

  public static Location toLocation(String data) {
    String[] values = data.split(";");
    if (values != null) {
      int row = Integer.parseInt(values[0]);
      int column = Integer.parseInt(values[1]);
      return new Location(row, column);
    }
    return null;
  }

  public static String toString(Location location) {
    return location.row + ";" + location.column;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (!Location.class.isAssignableFrom(obj.getClass())) {
      return false;
    }
    final Location other = (Location) obj;
    if (this.row != other.row) {
      return false;
    }
    if (this.column != other.column) {
      return false;
    }
    return true;
  }
}
