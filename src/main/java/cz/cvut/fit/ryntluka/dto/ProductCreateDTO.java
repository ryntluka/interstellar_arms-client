package cz.cvut.fit.ryntluka.dto;

import java.util.List;

public class ProductCreateDTO {

    private final int price;
    private final String name;
    private final List<Integer> ordersIds;

    public ProductCreateDTO(int price, String name, List<Integer> ordersIds) {
        this.price = price;
        this.name = name;
        this.ordersIds = ordersIds;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getOrdersIds() {
        return ordersIds;
    }
}
