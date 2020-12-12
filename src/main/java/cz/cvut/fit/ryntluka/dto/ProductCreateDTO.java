package cz.cvut.fit.ryntluka.dto;

import java.util.List;
import java.util.Objects;

public class ProductCreateDTO implements ModelCreateDTO {

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

    @Override
    public int hashCode() {
        return Objects.hash(price, name, ordersIds);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        ProductCreateDTO product = (ProductCreateDTO) obj;
        return Objects.equals(price, product.price)
                && Objects.equals(name, product.name)
                && (Objects.equals(ordersIds, product.ordersIds)
                || ordersIds.isEmpty() && product.ordersIds == null);
    }

    public static ProductCreateDTO toCreateDTO(ProductDTO productDTO) {
        return new ProductCreateDTO(productDTO.getPrice(), productDTO.getName(), productDTO.getOrdersIds());
    }
}
