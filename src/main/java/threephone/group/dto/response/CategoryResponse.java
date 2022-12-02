package threephone.group.dto.response;

import lombok.Data;
import threephone.group.model.product.Product;

import java.util.List;
@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private String avatar;
    private List<Product> products;
}
