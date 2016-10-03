package com.chess.ui;

public class Location {
	public int row;
	public int column;
	
	public Location(int row, int column){
		this.row = row;
		this.column = column;
	}
	
	public static Location toLocation(String data){
		String[] values = data.split(";");
		if(values != null){
			int row = Integer.parseInt(values[0]);
			int column = Integer.parseInt(values[1]);
			return new Location(row, column);
		}
		return null;
	}
	
	public static String toString(Location location){
		return location.row+";"+location.column;
	}
}
