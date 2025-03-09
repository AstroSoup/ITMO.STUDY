package entity;

public enum TicketType implements Comparable<TicketType>{
    VIP,
    USUAL,
    BUDGETARY,
    CHEAP;

    @Override
    public String toString() {
        return switch(this){
            case VIP -> "VIP";
            case USUAL -> "USUAL";
            case BUDGETARY -> "BUDGETARY";
            case CHEAP -> "CHEAP";
        };
    }
}
