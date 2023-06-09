public class Main
{
    Graph gm;    
    UI ui;
    private final int[] x = {369,251,145,207,206,342,475,134,372,279,442,458,235,292,359,298};
    private final int[] y = {561,513,480,422,415,346,341,328,255,237,234,225,179,141,134,77};
    private final String[] city_names = {"München","Stuttgart","Saarbrücken","Mainz","Wiesbaden","Erfurt","Dresden","Düsseldorf","Magdeburg","Hannover","Potsdam","Berlin","Bremen","Hamburg","Schwerin","Kiel"};
    public Main(){
        gm = new Graph(city_names.length);
        ui = new UI(gm);
        
        //Einfügen aller Knoten
        for(int i = 0; i < city_names.length; i++){
            gm.KnotenEinfuegen(city_names[i], new int[]{x[i],y[i]});
        }

        //Einfügen der Kanten 
        gm.KanteEinfuegen("München", "Stuttgart", 232);
        gm.KanteEinfuegen("München", "Erfurt", 396);
        gm.KanteEinfuegen("Stuttgart", "Saarbrücken", 218);
        gm.KanteEinfuegen("Stuttgart", "Mainz", 208);
        gm.KanteEinfuegen("Saarbrücken", "Mainz", 147);
        gm.KanteEinfuegen("Saarbrücken", "Düsseldorf", 282);
        gm.KanteEinfuegen("Mainz", "Wiesbaden", 17);
        gm.KanteEinfuegen("Wiesbaden", "Erfurt", 282);
        gm.KanteEinfuegen("Wiesbaden", "Düsseldorf", 204);
        gm.KanteEinfuegen("Wiesbaden", "Hannover", 374);
        gm.KanteEinfuegen("Erfurt", "Dresden", 216);
        gm.KanteEinfuegen("Erfurt", "Düsseldorf", 407);
        gm.KanteEinfuegen("Erfurt", "Magdeburg", 156);
        gm.KanteEinfuegen("Erfurt", "Hannover", 216);
        gm.KanteEinfuegen("Dresden", "Magdeburg", 227);
        gm.KanteEinfuegen("Dresden", "Potsdam", 203);
        gm.KanteEinfuegen("Dresden", "Berlin", 193);
        gm.KanteEinfuegen("Düsseldorf", "Hannover", 281);
        gm.KanteEinfuegen("Düsseldorf", "Bremen", 292);
        gm.KanteEinfuegen("Magdeburg", "Hannover", 149);
        gm.KanteEinfuegen("Magdeburg", "Potsdam", 126);
        gm.KanteEinfuegen("Hannover", "Potsdam", 255);
        gm.KanteEinfuegen("Hannover", "Bremen", 116);
        gm.KanteEinfuegen("Hannover", "Hamburg", 151);
        gm.KanteEinfuegen("Potsdam", "Berlin", 36);
        gm.KanteEinfuegen("Potsdam", "Schwerin", 208);
        gm.KanteEinfuegen("Berlin", "Schwerin", 213);
        gm.KanteEinfuegen("Bremen", "Hamburg", 122);
        gm.KanteEinfuegen("Hamburg", "Schwerin", 108);
        gm.KanteEinfuegen("Hamburg", "Kiel", 97);
        gm.KanteEinfuegen("Schwerin", "Kiel", 151);
        gm.Ausgeben();
        ui.start();
    }
    
}
