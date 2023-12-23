package util;

public enum Powers {
    
    BOOST(1),
    STICKY_GOO(2),
    BLOCK(3),
    RAMP(4),
    BEACH_BALL(5),
    SWAP_GOALS(6),
    GHOSTED(7),
    BIG_HEAD(8),
    BIG_BUMPERS(9),
    MOVE_BALL(10),
    MOVE_PLAYER(11);
    
    private final int number;
    
    Powers(int number) {
        this.number = number;
    }
    
    public int getNumber() {
        return number;
    }
    
}
