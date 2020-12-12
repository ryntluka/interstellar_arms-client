package cz.cvut.fit.ryntluka.ui.form.product;

import com.vaadin.flow.component.textfield.TextField;
import cz.cvut.fit.ryntluka.dto.ProductDTO;

import java.util.List;

import static java.lang.Integer.parseInt;

public class ProductHelperForm {

    public static void setProduct(ProductDTO product, TextField id, TextField price, TextField name) {
        id.setValue(Integer.toString(product.getId()));
        price.setValue(Integer.toString(product.getPrice()));
        name.setValue(product.getName());
    }

    public static ProductDTO getEntity(TextField id, TextField price, TextField name) {
        if (validateProduct(id, price, name))
            return new ProductDTO(Integer.parseInt(id.getValue()),
                Integer.parseInt(price.getValue()),
                name.getValue(),
                List.of());
        else
            throw new IllegalArgumentException();
    }

    private static boolean validateProduct(TextField id, TextField price, TextField name) {
        boolean number = true;
        try {
            parseInt(price.getValue());
            parseInt(id.getValue());
        } catch (NumberFormatException e) {
            number = false;
        }
        return number && !name.isEmpty();
    }
}
