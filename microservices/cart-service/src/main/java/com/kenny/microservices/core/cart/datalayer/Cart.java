package com.kenny.microservices.core.cart.datalayer;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "cart")
@Builder(builderMethodName = "cart")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Type(type = "uuid-char")
    @Column(name = "product_id",unique = true, nullable = false)
    @Builder.Default
    //Spring boot is so fun just cuz I put product_id, instead of productId, jpa repo dont understand
    private UUID productId = UUID.randomUUID();
    //private String product_id = UUID.randomUUID().toString().replace("-", "");

    @Column(name = "category_id")
    private int categoryId;

    @Size(max = 8192)
    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private double price;

    @Column(name = "quantity")
    private int quantity;

    @Size(max = 8192)
    @Column(name = "description")
    private String description;


}
