package shared.entity;

public enum TicketType implements Comparable<TicketType> {
    VIP,
    USUAL,
    BUDGETARY,
    CHEAP;
    public String toString() {
        return this == null ? "На коленочках у проводника" : this.name();
    }
}
