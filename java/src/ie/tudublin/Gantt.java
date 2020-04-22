package ie.tudublin;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

public class Gantt extends PApplet
{	
	ArrayList<Task> tasks = new ArrayList<Task>();
	Table table;
	int row_count = 0;
	
	public void settings()
	{
		size(800, 600);
	}

	public void loadTasks()
	{
		table = loadTable("../data/tasks.csv", "header");
		
		row_count = table.getRowCount();
		
		for(TableRow r : table.rows()){
			tasks.add(r);
		}
	}

	public void printTasks()
	{
		for(int i = 0; i < row_count; i++){
			println(tasks.get(i).toString());
		}
	}
	
	public void mousePressed()
	{
		println("Mouse pressed");	
	}

	public void mouseDragged()
	{
		println("Mouse dragged");
	}

	
	
	public void setup() 
	{
		loadTasks();
		printTasks();
	}
	
	public void draw()
	{			
		background(0);
	}
}
