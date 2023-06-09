public class Knoten {
    
    private String bezeichnung;
    private int[] position;

    public Knoten(String neuerWert, int[] pos)
    {
        bezeichnung = neuerWert;
        position = pos;
    }

    public String BezeichnungGeben()
    {
        return bezeichnung;
    }

    public String BezFormatGeben(int breite)
    {
         return (bezeichnung+"                       ").substring(0,breite);
    }
    
    public int[] Position_geben(){
        return position;
    }

    public void draw_name(){
        ZEICHENFENSTER.gibFenster().zeichneText(bezeichnung, position[0], position[1]-5);
    }

    public void draw_connection(Knoten K_connect_to){
        int pos_to_connect[] = K_connect_to.Position_geben();
        ZEICHENFENSTER.gibFenster().zeichneStrecke(position[0],position[1], pos_to_connect[0],pos_to_connect[1]);
    }
}
