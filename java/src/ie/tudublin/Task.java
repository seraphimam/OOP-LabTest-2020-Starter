package ie.tudublin;

/*
	name: Ming Chung Poon
	Student number: C18748391
*/

import processing.data.Table;
import processing.data.TableRow;

public class Task{
	private String name;
	private int start, end;
	
	public Task(){
	}
	
	//constructor accepting individual values to each data field
	public Task(String name, int start, int end){
		this.name = name;
		this.start = start;
		this.end = end;
	}
	
	//constructor accepting TableRow
	public Task(TableRow row){
		this.name = row.getString("Task");
		this.start = row.getInt("Start");
		this.end = row.getInt("End");
	}
	
	//Setters
	public void set_name(String s){
		this.name = s;
	}
	
	public void set_start(int x){
		this.start = x;
	}
	
	public void set_end(int x){
		this.end = x;
	}
	
	//Getters
	public String get_name(){
		return this.name;
	}
	
	public int get_start(){
		return this.start;
	}
	
	public int get_end(){
		return this.end;
	}
	
	//toString method that uses concatenation 
	public String toString(){
		String out = "Task ";
		
		out += this.name;
		out += " will start on day ";
		out += this.start;
		out += " and ends on day ";
		out += this.end;
		out += ".";
		
		return out;
	}
}