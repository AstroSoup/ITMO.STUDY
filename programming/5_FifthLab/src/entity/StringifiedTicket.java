package entity;

public record StringifiedTicket(String name, String price, String discount, String type,
                                StringifiedCoordinates coordinates, StringifiedPerson person) {
}
