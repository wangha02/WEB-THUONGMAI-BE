package threephone.group.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import threephone.group.model.category.Category;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private float price;
    private String description;
    private String manufacture;
    private int availableQuantity;

    private int quantity;
    private String image;
    private String advertising;
    @ManyToOne
    private Category category;


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", manufacture='" + manufacture + '\'' +
                ", availableQuantity=" + availableQuantity +
                ", quantity=" + quantity +
                ", image='" + image + '\'' +
                ", advertising='" + advertising + '\'' +
                ", category=" + category +
                '}';
    }
}
