package Event;

public enum RageLvl {
    CALM, ANGRY, FURIOUS;
    public int toNum() {
        return switch (this) {
            case CALM -> 1;
            case ANGRY -> 2;
            case FURIOUS -> 3;
        };
    }
    public static RageLvl toRageLvl(int num){
        return switch(num){
            case 1 -> CALM;
            case 2 -> ANGRY;
            case 3 -> FURIOUS;
            default -> FURIOUS;
            };
    }
}
