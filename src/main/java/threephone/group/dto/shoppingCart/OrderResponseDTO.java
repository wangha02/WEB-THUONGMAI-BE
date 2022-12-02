package threephone.group.dto.shoppingCart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import threephone.group.model.product.Product;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderResponseDTO {
    private List<Integer> amount;
    private List<Product> products;
}
