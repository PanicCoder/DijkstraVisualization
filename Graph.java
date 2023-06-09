import java.util.*;
public class Graph {
    
    //Inizialisierung der benötigten Attribute
    private int anzahlKnoten;
    private Knoten[] knoten;
    private int[][] adjazenzmatrix;
    private boolean[] besucht;
    private Map<String, Integer> kosten;
    private Map<String, String> Vorgänger;    
    

    //Wertzuweisung der Attribute
    public Graph(int maximaleKnoten){
        anzahlKnoten = 0;
        knoten = new Knoten[maximaleKnoten];
        adjazenzmatrix = new int[maximaleKnoten][maximaleKnoten];
        besucht = new boolean[maximaleKnoten];
        kosten = new HashMap<String, Integer>();
        Vorgänger = new HashMap<String, String>();        
    }

    public Knoten[] get_knoten_list(){
        return knoten;
    }

    public void KnotenEinfuegen(String bezeichner, int[] pos)
    {
        if ((anzahlKnoten < knoten.length) && (KnotenNummer(bezeichner) == -1))
        {
            knoten[anzahlKnoten] = new Knoten(bezeichner, pos);
            adjazenzmatrix[anzahlKnoten][anzahlKnoten] = 0;
            for (int i=0; i<anzahlKnoten; i++)
            {
                // Symmetrie, da ungerichteter Graph
                adjazenzmatrix[anzahlKnoten][i] = -1;
                adjazenzmatrix[i][anzahlKnoten] = -1;
            }
            anzahlKnoten++;
        }
    }
    
    //gibt den Namen des Knotens ausgehend von der Knotennummer zurück
    public String KnotenBezeichnerGeben(int knotenNummer)
    {
        if ((knotenNummer < anzahlKnoten) && (knotenNummer >= 0))
            return knoten[knotenNummer].BezeichnungGeben();
        return "";
    }
    
    //gibt die position des Knotens im Feld "knoten" zurück falls vorhanden, ansonsten -1
    public int KnotenNummer(String bezeichner)
    {
        for (int i=0; (i < anzahlKnoten); i++)
            if (knoten[i].BezeichnungGeben().equals(bezeichner))
                return i;
        return -1;
    }

    //weißt der Position in der Adjazenmatrix der zwei Knoten den Wert der Gewichtung zu
    public void KanteEinfuegen(String von, String nach, int gewichtung)
    {   
        int vonNummer = KnotenNummer(von);
        int nachNummer = KnotenNummer(nach);
        if ((vonNummer!=-1) && (nachNummer!=-1) && (vonNummer!=nachNummer))
        {
            adjazenzmatrix[vonNummer][nachNummer] = gewichtung;
            adjazenzmatrix[nachNummer][vonNummer] = gewichtung;
        }
    }

    public void Ausgeben()
    {
        int breite = 12;
        // Kopfzeile
        System.out.print("          ");  
        for (int i=0; i<anzahlKnoten; i++){ 
            System.out.print(knoten[i].BezFormatGeben(breite));
        }
        System.out.println();
        
        for (int i=0; i<anzahlKnoten; i++)
        {
            System.out.print(knoten[i].BezFormatGeben(breite));
            for (int j=0; j<anzahlKnoten; j++){
                if (adjazenzmatrix[i][j] != -1){
                    System.out.print((adjazenzmatrix[i][j]+"                           ").substring(0,breite));
                }else{
                    System.out.print("            ");
                }
            }
            System.out.println();           
        }
    }   

    //Durchläuft das Feld besucht und gibt wahr zurück wenn kein Wert auf false ist
    public boolean alle_besucht(){
        for(int i = 0; i < anzahlKnoten; i++){
            if(!besucht[i]){
                return false;
            }
        }
        return true;
    }

    //Durchläuft das Hashmap kosten und gibt den Knotennamen des Knotens zurück der die niedrigsten kosten hat
    public String niedrigste_kosten(){
        int min = (int) Double.POSITIVE_INFINITY;
        String next_knoten = "";
        for(int i = 0; i < kosten.size();i++){
            if (min > kosten.get(KnotenBezeichnerGeben(i)) && !besucht[i]){
                min = kosten.get(KnotenBezeichnerGeben(i));
                next_knoten = KnotenBezeichnerGeben(i);
            }
        }
        return next_knoten;
    }
    
    //Der Graph wir rekursiv durchlaufen solange nicht jeder Knoten besucht wurde
    public void besuchen(String aktueller_knoten, String end_knoten){
        String K_name;
        besucht[KnotenNummer(aktueller_knoten)] = true;
        if(alle_besucht()){
            return;
        }else{
            int K_nummer = KnotenNummer(aktueller_knoten);

            //Durchlaufen der Akjazenmatrix[aktueller_knoten] um alle verbindungen zu finden
            for(int i = 0; i < knoten.length;i++){
                if(adjazenzmatrix[K_nummer][i] > 0 && !besucht[i]){
                    K_name = KnotenBezeichnerGeben(i);

                    /*Wenn die kosten des aktuellen_knotens + die der Verbindung zum neuen Knoten 
                    niedriger sind als die Kosten des besuchten Knotens, wird der neue Wert für den 
                    besuchten Knoten gespeichert*/
                    if(kosten.get(aktueller_knoten)+adjazenzmatrix[K_nummer][i] < kosten.get(K_name)){
                        kosten.put(K_name,kosten.get(aktueller_knoten)+adjazenzmatrix[K_nummer][i]);
                        Vorgänger.put(K_name, aktueller_knoten);          
                    }
                }
            }

            //rekursiver Aufruf, mit neuem startknoten
            //neuer startknoten ist der mit den niedrigsten kosten   
            besuchen(niedrigste_kosten(),end_knoten);     
        }
    }

    public void Dijkstra(String start_knoten, String end_knoten){
        kosten_erstellen(start_knoten);
        //als anfangsknoten den start_knoten im Hashmap speichern
        Vorgänger.put(start_knoten, start_knoten);
        besuchen(start_knoten, end_knoten);
        kürzester_weg(start_knoten, end_knoten);
        //zeichnet die Endkosten auf die Zeichenfläche
        ZEICHENFENSTER.gibFenster().zeichneZahlb(kosten.get(end_knoten),475,590);
    }

    //gibt den kürzesten weg züruck durch das sog. "backtracking"
    public void kürzester_weg(String start_knoten, String end_knoten){
        String route = end_knoten;
        String anfang = Vorgänger.get(end_knoten);
        //zeichnen der verbindungslinie
        knoten[KnotenNummer(end_knoten)].draw_connection(knoten[KnotenNummer(anfang)]);
        String temp_name = null;
        while(true){
            if(!anfang.equals(start_knoten)){
                route +=" <- "+anfang;
                temp_name = Vorgänger.get(anfang); 
                //zeichnen der verbindungslinie
                knoten[KnotenNummer(temp_name)].draw_connection(knoten[KnotenNummer(anfang)]); 
                              
                if(!temp_name.equals(start_knoten)){
                    route +=" <- "+temp_name;
                    anfang = Vorgänger.get(temp_name);
                    //zeichnen der verbindungslinie
                    knoten[KnotenNummer(temp_name)].draw_connection(knoten[KnotenNummer(anfang)]);
                    //springt zurück zum anfang der while-schleife
                    continue;
                }
                
            }
            System.out.println(route+" <- "+start_knoten);
            //beendet die while-schleife
            return;           
        }
    }    

    //setzt die Kosten aller Knoten zu beginn auf +unendlich mit außnahme des startknotens
    public void kosten_erstellen(String start_knoten){
        //setzen der Kosten des Startknotens auf 0
        kosten.put(start_knoten,0);
        int start_knoten_nummer = KnotenNummer(start_knoten);
        for (int i = 0; i < knoten.length;i++){
            if( i == start_knoten_nummer){
                //springen zurück zum start des for-schleife
                continue;
            }
            //setzen der Startkosten auf +unendlich
            kosten.put(KnotenBezeichnerGeben(i),(int)Double.POSITIVE_INFINITY);
        }
    }
}