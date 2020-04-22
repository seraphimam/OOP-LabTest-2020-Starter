package ie.tudublin;

/*
	name: Ming Chung Poon
	Student number: C18748391
*/

import java.util.ArrayList;
import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

public class Gantt extends PApplet
{	
	//Variables
	//Array list of class Task
	ArrayList<Task> tasks = new ArrayList<Task>();
	
	//Table for loading in csv onto - to use TableRow constructor in Task class
	Table table;
	
	//number of rows of data in csv file
	int row_count = 0;
	
	//width and height of boxes
	int col_width = 20;
	int row_height = 40;
	
	//row chosen on mouse click - index for ArrayList, -1 means no row is selected (mouse click in wrong location
	int row_selected = -1;
	
	//stores the initial number of the selected data row (both start and end)
	int data = 0;
	
	//the distance of mouse from original location, used for shrinking and expanding the data bars
	int distance = 0;
	
	//the maximum length that you can shrink
	int max = 0;
	
	//map function related variables
	float x, y, w, h;
	int data_x, data_y;
	
	//determines whether the user clicked on start or end
	boolean start = true;
	
	public void settings()
	{
		size(800, 600);
	}

	public void loadTasks()
	{
		//loads csv file into a table
		table = loadTable("../data/tasks.csv", "header");
		
		//gets amount of data rows in table
		row_count = table.getRowCount();
		//println(row_count);
		
		//put each row in table into Task class objects and initialise them
		for(TableRow r : table.rows()){
			tasks.add(new Task(r));
		}
		
	}//end loadTasks()

	public void printTasks()
	{
		//prints onto data in each Task object inside ArrayList using its toString method
		for(int i = 0; i < row_count; i++){
			println(tasks.get(i).toString());
		}
		
		//more visible if called again
		println("------------------------------");
		println();
		
	}//end printTasks()
	
	public void mousePressed()
	{
		/*
			determine if user clicked on the right location without using map function
		*/
		
		/*
		//for loop to determine if the user clicked on the start or end location of any data row
		for(int i = 0; i < row_count; i++){
			
			//if user's mouse Y coordinate is between any of the drawn rectangles it proceeds to test for its x coordinate
			
			if(mouseY >= ((float)((2.5 + i) * row_height)) && mouseY < ((float)((3.3 + i) * row_height))){
				
				//if user's mouse X coordinate is between the starting point of rectangle and 20 pixels to the right
				//meaning user selected the start point of this data block
				
				//Grid drawn from 9 * col_width as number 1, width to left used to write task names,
				//since the day number starts from 1 not 0, so we need to subtract 1 from 9 to give us starting point,
				//hence (8 + start) * col_width will give us the left most x coordinate for starting point
				//then adding 1 to it means (9 + start) * col_width will give us the right most x coordinate for it
				
				if(mouseX >= ((8 + tasks.get(i).get_start()) * col_width) && mouseX < ((9 + tasks.get(i).get_start()) * col_width)){
					
					//stores data row user clicked on
					row_selected = i;
					
					//determines how many "squares" the user can move towards end point 
					//(-1 at end to make sure there's at least one point left)
					max = tasks.get(i).get_end() - tasks.get(i).get_start() - 1;
					
					//stores original start value
					data = tasks.get(i).get_start();
					
					//tells the drag function that it is moving from start point not end point
					start = true;
					
					//println(tasks.get(i).get_name() + " selected start.");
					
				}//end if start
				
				//if user's mouse X coordinate is between the ending point of rectangle and 20 pixels to the left
				//meaning user selected the end point of this data block
				
				//Grid drawn from 9 * col_width as number 1, width to left used to write task names,
				//since the day number starts from 1 not 0, so we need to subtract 1 from 9 to give us starting point,
				//hence (8 + end) * col_width will give us the left most x coordinate for ending point,
				//but because we need the square to be left of the end point, therefore take 1 from 8 will give us it,
				//so (7 + end) * col_width will give us the left most coordinate that we need,
				//then adding 1 to it means (8 + end) * col_width will give us the right most x coordinate for it
				
				if(mouseX >= ((7 + tasks.get(i).get_end()) * col_width) && mouseX < ((8 + tasks.get(i).get_end()) * col_width)){
					
					//stores data row user clicked on
					row_selected = i;
					
					//determines how many "squares" the user can move towards start point 
					//(+1 at end to make sure there's at least one point left)
					max = tasks.get(i).get_start() - tasks.get(i).get_end() + 1;
					
					//stores original start value
					data = tasks.get(i).get_end();
					
					//tells the drag function that it is moving from end point not start point
					start = false;
					
					//println(tasks.get(i).get_name() + " selected end.");
					
				}//end if end
				
			}//end if(y)
		
		}//end for
		
		*/
		
		/*
			end of user click determiner without map function 
		*/
		
		/*
			determine if user clicked on the right location using map function
		*/
		
		/* 
			(9 * col_width) is left most x coordinate of the grid,
			and since it has 29 "squares" in total (30 lines => 29 "squares"),
			(9 + 30 - 1) = 38, so (38 * col_width) will be the right most x coordinate 
			of the grid.
		*/
		
		//if the user's mouse X coordinate is within range of the grid
		if(mouseX >= (9 * col_width) && mouseX <= (38 * col_width)){
			
			//maps the user's mouse X onto a range of 1 to 30 - days 1 to 30
			//later used to determine if clicked on start or end point
			data_x = (int)(map(mouseX, (9 * col_width), (38 * col_width), 1, 30));
			
		}else{
			
			//because user clicked outside of the grid, we cannot map their mouse X
			data_x = -1;
			
		}//end map data_x

		//if the user's mouse Y coordinate is within range of the grid
		//2.5 * row_height is the first rectangle's top most location,
		//because max index is count - 1, so max Y is (2.5 + row_count - 1) * row_height
		if(mouseY >= (float)(2.5 * row_height) && mouseY <= (float)((1.5 + row_count) * row_height)){
			
			//maps user's mouse Y onto a range of 0 to "max index number"
			//used to determine the index of arraylist row user clicked on
			data_y = (int)(map(mouseY, (float)(2.5 * row_height), (float)((1.5 + row_count) * row_height), 0, row_count - 1));
			
		}else{
			
			//user clicked outside of grid, cannot map
			data_y = -1;
			
		}//end map data_y

		//if both user's X and Y coordinates are mapped
		//this means the mapped y coordinate - data_y is the row index
		//and the mapped x - data_x means which "square" the user clicked on
		if(data_x != -1 && data_y != -1){
			//println("go");
			
			//if user clicked on the starting square of current data row
			if(data_x == tasks.get(data_y).get_start()){
				//println("start");
				
				//when dragging, move from start point
				start = true;
				
				//row to be dragged is data_y
				row_selected = data_y;
				
				//original start point value 
				data = tasks.get(data_y).get_start();
				
				//amount of squares user can move towards end day
				max = tasks.get(data_y).get_end() - tasks.get(data_y).get_start() - 1;
				
			}//end if(start)
			
			//if user clicked on the ending square of current data row
			if(data_x == tasks.get(data_y).get_end() - 1){
				//println("end");
				
				//when dragging, move from end point
				start = false;
				
				//row to be dragged is data_y
				row_selected = data_y;
				
				//original end point value 
				data = tasks.get(data_y).get_end();
				
				//amount of squares user can move towards start day
				max = tasks.get(data_y).get_start() - tasks.get(data_y).get_end() + 1;
				
			}//end if(end)
				
		}//end if(mapped)
		
		/*
		if(row_selected != -1){
			println(tasks.get(row_selected).get_name() + " selected.");
		}
		*/
		
	}//end mousePressed()

	public void mouseDragged()
	{
		//if an existing data row has been selected
		if(row_selected != -1){
			
			//calculates the amount of squares the user moved from the original point
			distance = ((int)((mouseX - (9 * col_width)) / col_width)) - data + 1;
			
			//dragged from start point
			if(start){
				
				//if the user moved too much towards end point, it will stop at end - 1
				if(distance > max){
					distance = max;
				}
				
				//if the user moved start point below 1, this will make sure start point go back to 1
				if(data + distance < 1){
					tasks.get(row_selected).set_start(1);
					
				//else change the start point value stored in array list
				}else{
					tasks.get(row_selected).set_start(data + distance);
				}
				
			//dragged from end point
			}else{
				
				//if the user moved too much towards start point, it will stop at start + 1
				if(distance < max){
					distance = max;
				}
				
				//if the user moved end point above 30, this will make sure end point stop at 30
				if(data + distance > 30){
					tasks.get(row_selected).set_end(30);
					
				//else change the end point value stored in array list
				}else{
					tasks.get(row_selected).set_end(data + distance);
				}
				
			}
		}
		
	}//end mouseDragged

	//resets determine variables for moving data rows once user releases the mouse button
	public void mouseReleased(){
		row_selected = -1;
		data = 0;
		distance = 0;
		max = 0;
		
		//prints values stored in array list again
		//printTasks();
	}//end mouseReleased()
	
	
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
	
	//Draws the grid and the rectangle for displaying data
	public void displayTasks(){
		textSize(12);
		textAlign(CENTER, CENTER);
		
		//draws day numbers and the grid lines from 1 to 30
		for(int i = 0; i < 30; i++){
			fill(0,0,100);
			
			//writes day numbers
			text((i+1), ((9 + i) * col_width), row_height);
			
			//changes the line color to varying white and grey
			if(i % 2 == 0 || i == 29){
				stroke(0,0,100);
			}else{
				stroke(0,0,70);
			}
			
			//draws 30 lines below corresponding day numbers
			line(((9 + i) * col_width), (2 * row_height), ((9 + i) * col_width), (height - (2 * row_height)));
		}
		
		textAlign(LEFT, CENTER);
		
		//writes the task names and draws the corresponding rectangles
		for(int i = 0; i < row_count; i++){
			fill(0,0,100);
			
			//writes taks names
			text(tasks.get(i).get_name(), row_height, ((3 + i) * row_height));
			
			fill((i * (100 / row_count)),100,100);
			noStroke();
			
			//draws corresponding task rectangles without using map function
			//(8 + start) for starting left most x coordinate because day values starts at 1 not 0
			//(2.5 + index) for starting top most y coordinate because grid starts at 2.5 * row_height
			//rect(((8 + tasks.get(i).get_start()) * col_width), ((float)((2.5 + i) * row_height)), ((tasks.get(i).get_end() - tasks.get(i).get_start()) * col_width), ((float)(0.8 * row_height)), 10);
			
			//using map function
			//calculates the mapped x, y coordinates of each rectangle and their corresponding width and height
			//maps the start day value from 1 to 30 onto x coordinates based on col_width
			x = map(tasks.get(i).get_start(), 1, 30, (9 * col_width), (38 * col_width));
			//maps each data row's index onto y coordinate based on row_height
			y = map(i, 0, (row_count - 1), (float)(2.5 * row_height), (float)((1.5 + row_count) * row_height));
			//maps the width of the box (length is end day - start day) from 1 to 30 onto (1 to 30) * col_width
			w = map((tasks.get(i).get_end() - tasks.get(i).get_start()), 1, 30, col_width, (30 * col_width));
			//makes each box's height 80% of row_height to show separation
			h = map(row_height, 0, 100, 0, 80);
			
			rect(x, y, w, h, 10);
		
		}
	}
}
