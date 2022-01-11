package com.kenny.microservices.core.product.datalayer;


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
@Table(name = "product")
@Builder(builderMethodName = "product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 8192)
    @Column(name = "product_id",unique = true, nullable = false)
    @Builder.Default
    private String product_id = UUID.randomUUID().toString().replace("-", "");

    @Column(name = "category_id")
    private int category_id;

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
