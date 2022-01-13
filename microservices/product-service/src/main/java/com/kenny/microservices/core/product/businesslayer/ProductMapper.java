package com.kenny.microservices.core.product.businesslayer;

import com.kenny.microservices.core.product.datalayer.Product;
import com.kenny.microservices.core.product.datalayer.ProductDTO;
import com.kenny.microservices.core.product.datalayer.ProductIdLessDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class})
public interface ProductMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "productId", expression = "java(UUID.fromString(model.getProductId()))")
    })
    Product ProductDTOToEntity(ProductDTO model);

//    @Mapping(target = "id", ignore = true)
//    Product ProductToEntity(Product product);

    @Mapping(target = "productId", expression = "java(entity.getProductId().toString())")
    ProductDTO EntityToModelDTO(Product entity);
/*
    @Mapping(target = "product_id", expression = "java(entity.getProduct_Id().toString())")
    Product EntityToModel(Product entity);
*/
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "productId", ignore = true)
    })
    Product ProductIdLessDtoToEntity(ProductIdLessDTO product);

    List<ProductDTO> entityToModelList(List<Product> entity);


}
