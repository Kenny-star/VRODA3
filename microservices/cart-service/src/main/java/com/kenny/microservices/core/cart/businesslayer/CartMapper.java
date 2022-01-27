package com.kenny.microservices.core.cart.businesslayer;

import com.kenny.microservices.core.cart.datalayer.Cart;
import com.kenny.microservices.core.cart.datalayer.CartDTO;
import com.kenny.microservices.core.cart.datalayer.CartIdLessDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class})
public interface CartMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "productId", expression = "java(UUID.fromString(model.getProductId()))")
    })
    Cart CartDTOToEntity(CartDTO model);

//    @Mapping(target = "id", ignore = true)
//    Product ProductToEntity(Product product);

    @Mapping(target = "productId", expression = "java(entity.getProductId().toString())")
    CartDTO EntityToModelDTO(Cart entity);
    /*
        @Mapping(target = "product_id", expression = "java(entity.getProduct_Id().toString())")
        Product EntityToModel(Product entity);
    */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "productId", ignore = true)
    })
    Cart CartIdLessDtoToEntity(CartIdLessDTO product);

    List<CartDTO> entityToModelList(List<Cart> entity);


}