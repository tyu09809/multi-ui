package edu.bothell.multi_ui.core;

import java.util.Random;

public class World extends State {
    // PROPERTIES ----------------------------------------------------------------
    private final char[][] S = super.getIt();
    private Location[][] map; // Map array is y,x
    private int count = 0;
    
    // CONSTRUCTOR ----------------------------------------------------------------
    public World(){
        super();
        this.map = new Location[S.length][];

        build();
    }

    // METHODS -------------------------------------------------------------------
    public void build() {
        Random die = new Random();

        for (int y = 0; y < S.length; y++) {
            
            if(map[y] == null) map[y] = new Location[S[y].length];
            for (int x = 0; x < map[y].length; x++) {

                // Step 1: Create the location if it doesn't exist
                map[y][x] = (map[y][x] == null)? new Location(x, y, null):map[y][x];

                // Step 2: Loop Directions and add them as adjacent
                for(Directions d: Directions.values()){
                    Location nei = getNeighbor(map[y][x], d.offset(y%2==1));
                    map[y][x].addAdjacent(nei, d);
                    nei.addAdjacent(map[y][x], d.opposite()); // Ensure bidirectional linkage
                }
                
            }
        }

        Location location = map[2][2];
        location.setTerrain(Terrain.CITY);

        Crawler terriformer = new Crawler(this);

        terriformer.treverse(
            location,
            die, 
            (l, d) -> (l.getTerrain() == null )&& l.getAdjacents() != null,
            (l, d) -> l.setTerrain(Crawler.bestFit(l, d))
        );
      
        terriformer.treverse(
            location,
            die, 
            (l, d) -> l.getTerrain() != null && !l.isWalled(),
            (l, d) -> {}//Crawler.buildWalls(l, d)
            
        ); 
    }

    public Location[][] getMap(){
        return this.map;
    }

    private Location getNeighbor(Location l, Directions d) {
        return getNeighbor(l.getX(), l.getY(), d);
    }
    private Location getNeighbor(int x, int y, Directions d) {
        // Calculate the neighbor coordinates
        int nX = x + d.dX();
        int nY = y + d.dY();

        // Wrap vertical (rows)
        nY = (nY + map.length) % map.length;
    
        // Ensure the row exists
        if (map[nY] == null) {
            map[nY] = new Location[S[nY].length]; // Initialize the row if it doesn't exist
        }
    
        // Wrap horizontal (columns within the row)
        nX = (nX + map[nY].length) % map[nY].length;

        // Ensure the location exists
        if (map[nY][nX] == null) {
            map[nY][nX] = new Location(nX, nY, null); // Create a new Location if it doesn't exist
        }
    
        return map[nY][nX];
    }
    

    public Location getLocation(int x, int y){
        System.out.println("world get's it!");
        return map[y][x];
    }

    @Override
    public Object getIt(int[] pos){
        System.out.println("world get's it!");
        return map[pos[1]][pos[0]];
    }

}
