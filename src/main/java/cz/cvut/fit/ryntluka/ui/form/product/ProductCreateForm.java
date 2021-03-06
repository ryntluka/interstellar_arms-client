package cz.cvut.fit.ryntluka.ui.form.product;

import com.vaadin.flow.component.textfield.TextField;
import cz.cvut.fit.ryntluka.dto.ProductDTO;
import cz.cvut.fit.ryntluka.ui.form.FormComponent;

import java.util.List;

public class ProductCreateForm extends FormComponent<ProductDTO> {

    protected TextField id = new TextField("Id");
    protected TextField price = new TextField("Price");
    protected TextField name = new TextField("Name");

    public ProductCreateForm() {
        super();
        id.setVisible(false);
        delete.setVisible(false);
        addClassName("contact-form");
        id.setReadOnly(true);

        add(
                id,
                name,
                price,
                createButtonsLayout()
        );
    }

    protected ProductDTO getEntity() {
        return ProductHelperForm.getEntity(id, price, name);
    }

    public void setProduct(ProductDTO product) {
        ProductHelperForm.setProduct(product, id, price, name);
    }
}
