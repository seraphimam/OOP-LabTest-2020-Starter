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
	int col_width = 20;
	int row_height = 40;
	
	public void settings()
	{
		size(800, 600);
	}

	public void loadTasks()
	{
		table = loadTable("../data/tasks.csv", "header");
		
		row_count = table.getRowCount();
		
		for(TableRow r : table.rows()){
			tasks.add(new Task(r));
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
		colorMode(HSB, 100);
	}
	
	public void draw()
	{			
		background(0);
		displayTasks();
	}
	
	public void displayTasks(){
		textSize(12);
		textAlign(CENTER, CENTER);
		
		for(int i = 0; i < 30; i++){
			fill(0,0,100);
			text((i+1), ((9 + i) * col_width), row_height);
			
			if(i % 2 == 0 || i == 29){
				stroke(0,0,100);
			}else{
				stroke(0,0,70);
			}
			line(((9 + i) * col_width), (2 * row_height), ((9 + i) * col_width), (height - (2 * row_height)));
		}
		
		textAlign(LEFT, CENTER);
		for(int i = 0; i < row_count; i++){
			fill(0,0,100);
			text(tasks.get(i).get_name(), row_height, ((3 + i) * row_height));
			
			fill((i * (100 / row_count)),100,100);
			noStroke();
			rect(((8 + tasks.get(i).get_start()) * col_width), ((float)((2.5 + i) * row_height)), ((tasks.get(i).get_end() - tasks.get(i).get_start()) * col_width), ((float)(0.8 * row_height)), 10);
		}
	}
}
