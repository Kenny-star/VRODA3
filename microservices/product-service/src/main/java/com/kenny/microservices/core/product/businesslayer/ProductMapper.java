package com.kenny.microservices.core.product.businesslayer;

import com.kenny.microservices.core.product.datalayer.Product;
import com.kenny.microservices.core.product.datalayer.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO EntityToModel(Product product);

    @Mapping(target = "id", ignore = true)
    Product ProductToEntity(Product product);

    Product entityToModel(Product entity);

    List<ProductDTO> entityToModelList(List<Product> entity);
}
