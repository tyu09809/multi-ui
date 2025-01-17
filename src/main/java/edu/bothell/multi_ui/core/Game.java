public class Game {
    private final int                  MAX_PLAYERS = 3;
    private final ArrayList<Player>    p;
    private final State                s;
    private int                        turn;
    private Player                     active;

    public Game(Control c){
        this.turn = 0;
        this.s = new World();
        this.p = new ArrayList<>();
    }

    public Player addPlayer(Player p){
        this.p.add(p);
        if(this.active == null) active = p;

        return p;
    }

    public Player addPlayer(char c, String sId){
        Player p = new Player(c);
        p.setSId(sId);
        return addPlayer(p);
    }

    public char[] getPlayersChar(){
        char[] pcs = new char[p.size()];
        for(int i = 0; i < pcs.length; i++) 
            pcs[i] = p.get(i).getChar();
        
        return pcs;
    }

    public boolean isValid(int[] pos, String sId){
        System.out.println("isValid?"+s.getIt(pos)+"|" + sId+"|" + active.getSId()+"|");

        if(pos[0] > 0 && pos[1] <= 2) return false;

        return s.isOpen(pos) && active.getSId().equals(sId);
    }

    public boolean checkGameEnd() {
        char[][] board = s.getIt(); // Assuming this returns a 2D char array representing the board
        char currentChar = active.getChar();

        // Check rows and columns
        for (int i = 0; i < board.length; i++) {
            if (checkLine(board[i][0], board[i][1], board[i][2], currentChar) ||
                checkLine(board[0][i], board[1][i], board[2][i], currentChar)) { 
                return true;
            }
        }

        // Check diagonals
        if (checkLine(board[0][0], board[1][1], board[2][2], currentChar) || 
            checkLine(board[0][2], board[1][1], board[2][0], currentChar)) { 
            return true;
        }

        return false; // No winner found
    }

    private boolean checkLine(char a, char b, char c, char currentChar) {
        return a == currentChar && b == currentChar && c == currentChar;
    }

    public char play(int[] pos, String sId){
        if(!isValid(pos, sId)) return ' ';
        turn++;
        this.s.setIt(active.getChar(), pos[0], pos[1]);
        if (checkGameEnd()) {
            System.out.println("Player " + active.getChar() + " wins!");
            return active.getChar(); // Return the winning character
        }

        this.active = p.get(turn % p.size());
        return active.getChar();
    }

    public Player getActive() {
        return this.active;
    }

    public State getState() {
        return this.s;
    }

    public Location getLocation(int x, int y) {
        return ((World)s).getLocation(x, y);
    }

    public int getMaxPlayers() {
        return MAX_PLAYERS;
    }

    public int getPlayerCount() {
        return p.size();
    }

    public ArrayList<Player> getPlayers(){
        return this.p;
    }

    public int getTurn(){
        return this.turn;
    }
}
