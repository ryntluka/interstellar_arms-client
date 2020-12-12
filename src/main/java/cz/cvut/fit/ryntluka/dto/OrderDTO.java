package cz.cvut.fit.ryntluka.dto;

public class OrderDTO implements ModelDTO {

    int customerId;
    int productId;

    @Override
    public int getId() {
        return 0;
    }

    public OrderDTO(int customerId, int productId) {
        this.customerId = customerId;
        this.productId = productId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getProductId() {
        return productId;
    }
}
