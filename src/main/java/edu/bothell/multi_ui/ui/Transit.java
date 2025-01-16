package edu.bothell.multi_ui.ui;


public class Transit {
    
    public char[][]     s;
    public String[][]   t;
    public char         a;
    public boolean      over;

    public Transit(char[][] state, char active){
        this.s = state;
        this.a = active;
    }

    public Transit(char[][] state, char active, String[][] terrain){
        this.s = state;
        this.a = active;
        this.t = terrain;
    }

    public Transit(char[][] state, char active, String[][] terrain, String[][] walls){
        this.s = state;
        this.a = active;
        //this.t = terrain;
        for(int y = 0; y < walls.length; y++){
            for(int x = 0; x < walls[y].length; x++){
                t[y][x] += " w" + walls[y][x];
            }
        }
    }
}
 