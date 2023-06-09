import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class UI {
    
    private ZEICHENFENSTER window;
    private Graph graph;
    private Knoten Start_k,End_k;

    public UI(Graph g){
        window = ZEICHENFENSTER.gibFenster();
        this.graph = g;
        BufferedImage img = null;
        //laden des Bildes
        try{
            img  =  ImageIO.read(this.getClass().getResource("/Images/Karte.png"));   
        }catch (IOException e){
            e.printStackTrace();
        }   
        window.zeichneBild(img, 0, 0);
    }

    public Knoten get_knoten_by_click(){
        Knoten[] k_list = graph.get_knoten_list();
        Knoten k;
        int[] current_pos;
        for(int i = 0; i < k_list.length; i++){
            k = k_list[i];
            current_pos = k.Position_geben();
            if(current_pos[0]+4 >= window.getpos_x() && window.getpos_x()>= current_pos[0]-4 && current_pos[1]+4 >= window.getpos_y() && window.getpos_y() >= current_pos[1]-4){
                if(Start_k == null)
                {
                    return k_list[i];
                }else{
                    if(Start_k.Position_geben()[0] != k_list[i].Position_geben()[0] && Start_k.Position_geben()[1] != k_list[i].Position_geben()[1]){
                        return k_list[i];
                    }
                }
                
            }
        }
        return null;
    }

    public Knoten get_start(){
        while(Start_k == null){
            Start_k = get_knoten_by_click();
        }
        return Start_k;
    }

    public Knoten get_end(){
        
        while(End_k == null){
            End_k = get_knoten_by_click();
        }
        return End_k;
    }

    public void draw_start(){
        Knoten start = get_start();
        window.zeichneKreisc(start.Position_geben()[0],start.Position_geben()[1],5);
        window.zeichneKreisc(start.Position_geben()[0],start.Position_geben()[1],6);
    }

    public void draw_end(){
        Knoten end = get_end();
        window.zeichneKreisc(end.Position_geben()[0],end.Position_geben()[1],5);
        window.zeichneKreisc(end.Position_geben()[0],end.Position_geben()[1],6);
    }

    public void draw_city_names(){
        Knoten[] k_list = graph.get_knoten_list();
        for(int i = 0; i < k_list.length;i++){
            k_list[i].draw_name();
        }
    }

    //Führt den Dijkstra Algorithmus mit dem zuvor ausgewählten Start- und Endknoten aus
    public void start(){
        draw_city_names();
        draw_start();
        draw_end();
        graph.Dijkstra(Start_k.BezeichnungGeben(), End_k.BezeichnungGeben());
    }

}
