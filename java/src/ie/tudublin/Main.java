package ie.tudublin;

/*
	name: Ming Chung Poon
	Student number: C18748391
*/

public class Main
{

    public void gant()
	{
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new Gantt());
    }
    
    public static void main(String[] arg)
    {
        Main main = new Main();
		main.gant();        
    }
}