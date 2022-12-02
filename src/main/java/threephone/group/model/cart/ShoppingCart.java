package threephone.group.model.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import threephone.group.model.User;
import threephone.group.model.product.Product;

import javax.persistence.*;
import java.util.List;

@ToString
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, targetEntity = Product.class)
    @JoinColumn(name = "oder_id",referencedColumnName = "id")
    private List<Orders> orderList;
    @ManyToOne
    private User user;
}
