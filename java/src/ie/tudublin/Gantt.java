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
	int row_selected = -1;
	int data = 0;
	int distance = 0;
	int max = 0;
	float x, y, w, h;
	int data_x, data_y;
	boolean start = true;
	
	public void settings()
	{
		size(800, 600);
	}

	public void loadTasks()
	{
		table = loadTable("../data/tasks.csv", "header");
		
		row_count = table.getRowCount();
		println(row_count);
		
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
		/*
		for(int i = 0; i < row_count; i++){
			
			if(mouseY >= ((float)((2.5 + i) * row_height)) && mouseY < ((float)((3.3 + i) * row_height))){
				
				if(mouseX >= ((8 + tasks.get(i).get_start()) * col_width) && mouseX < ((9 + tasks.get(i).get_start()) * col_width)){
					row_selected = i;
					max = tasks.get(i).get_end() - tasks.get(i).get_start() - 1;
					data = tasks.get(i).get_start();
					start = true;
					println(tasks.get(i).get_name() + " selected start.");
				}
				
				if(mouseX >= ((7 + tasks.get(i).get_end()) * col_width) && mouseX < ((8 + tasks.get(i).get_end()) * col_width)){
					row_selected = i;
					max = tasks.get(i).get_start() - tasks.get(i).get_end() + 1;
					data = tasks.get(i).get_end();
					start = false;
					println(tasks.get(i).get_name() + " selected end.");
				}
			}
		
		}
		*/
		
		if(mouseX >= (9 * col_width) && mouseX <= (38 * col_width)){
			data_x = (int)(map(mouseX, (9 * col_width), (38 * col_width), 1, 30));
		}else{
			data_x = -1;
		}

		if(mouseY >= (float)(2.5 * row_height) && mouseY <= (float)((1.5 + row_count) * row_height)){
			data_y = (int)(map(mouseY, (float)(2.5 * row_height), (float)((1.5 + row_count) * row_height), 0, row_count - 1));
		}else{
			data_y = -1;
		}

		if(data_x != -1 && data_y != -1){
			println("go");
			
			if(data_x == tasks.get(data_y).get_start()){
				println("start");
				start = true;
				row_selected = data_y;
				data = tasks.get(data_y).get_start();
				max = tasks.get(data_y).get_end() - tasks.get(data_y).get_start() - 1;
			}
			
			if(data_x == tasks.get(data_y).get_end() - 1){
				println("end");
				start = false;
				row_selected = data_y;
				data = tasks.get(data_y).get_end();
				max = tasks.get(data_y).get_start() - tasks.get(data_y).get_end() + 1;
			}
		}
		
		if(row_selected != -1){
			println(tasks.get(row_selected).get_name() + " selected.");
		}
	}

	public void mouseDragged()
	{
		if(row_selected != -1){
			distance = ((int)((mouseX - (9 * col_width)) / col_width)) - data + 1;
			
			if(start){
				if(distance > max){
					distance = max;
				}
				
				if(data + distance < 1){
					tasks.get(row_selected).set_start(1);
				}else{
					tasks.get(row_selected).set_start(data + distance);
				}
				
			}else{
				if(distance < max){
					distance = max;
				}
				
				if(data + distance > 30){
					tasks.get(row_selected).set_end(30);
				}else{
					tasks.get(row_selected).set_end(data + distance);
				}
				
			}
		}
	}

	public void mouseReleased(){
		row_selected = -1;
		data = 0;
		distance = 0;
		max = 0;
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
			//rect(((8 + tasks.get(i).get_start()) * col_width), ((float)((2.5 + i) * row_height)), ((tasks.get(i).get_end() - tasks.get(i).get_start()) * col_width), ((float)(0.8 * row_height)), 10);
			
			x = map(tasks.get(i).get_start(), 1, 30, (9 * col_width), (38 * col_width));
			y = map(i, 0, (row_count - 1), (float)(2.5 * row_height), (float)((1.5 + row_count) * row_height));
			w = map((tasks.get(i).get_end() - tasks.get(i).get_start()), 1, 30, col_width, (30 * col_width));
			h = map(row_height, 0, 100, 0, 80);
			rect(x, y, w, h, 10);
		
		}
	}
}
