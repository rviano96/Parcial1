package delivery.model.DTO;

public class RestaurantDto {
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "RestaurantDto{" +
                "address='" + address + '\'' +
                '}';
    }
}
