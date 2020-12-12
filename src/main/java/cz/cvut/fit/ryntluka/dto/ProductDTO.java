package cz.cvut.fit.ryntluka.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

public class ProductDTO extends RepresentationModel<ProductDTO> implements ModelDTO {

    private final int id;
    private final int price;
    private final String name;
    private final List<Integer> ordersIds;

    public ProductDTO(int id, int price, String name, List<Integer> ordersIds) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.ordersIds = ordersIds;
    }

    public int getId() {
        return id;
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

    @Override
    public int hashCode() {
        return Objects.hash(id, price, name, ordersIds);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        ProductDTO product = (ProductDTO) obj;
        return id == product.id
                && Objects.equals(price, product.price)
                && Objects.equals(name, product.name)
                && (Objects.equals(ordersIds, product.ordersIds)
                || ordersIds.isEmpty() && product.ordersIds == null);
    }
}
