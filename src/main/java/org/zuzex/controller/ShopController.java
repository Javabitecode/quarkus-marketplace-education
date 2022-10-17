package org.zuzex.controller;

import lombok.RequiredArgsConstructor;
import org.zuzex.dto.ShopDto;
import org.zuzex.model.Shop;
import org.zuzex.util.mapper.ShopMapper;
import org.zuzex.service.ShopService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.CREATED;

@Path("/api/v1/shops")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;
    private final ShopMapper shopMapper;

    @GET
    @Path("/{shopId}")
    public ShopDto getShopById(@PathParam("shopId") Long id) {
        return shopMapper.toShopDto(shopService.getShopById(id));
    }

    @GET
    public List<ShopDto> getAllShop() {
        return shopService.getAllShop()
                .stream()
                .map(shopMapper::toShopDto)
                .toList();
    }

    @POST
    public Response createShop(ShopDto shopDto) {
        Shop shop = shopMapper.toShop(shopDto);
        ShopDto response = shopMapper.toShopDto(shopService.createShop(shop));
        return Response.status(CREATED).entity(response).build();
    }

    @PUT
    @Path(("/{shopId}"))
    public Response updateShopName(@PathParam("shopId") Long shopId, ShopDto shopDto) {
        Shop shop = shopMapper.toShop(shopDto);
        ShopDto response = shopMapper.toShopDto(shopService.updateShopName(shop, shopId));
        return Response.ok().entity(response).build();
    }

    @DELETE
    @Path(("/{shopId}"))
    public Response deleteShopById(@PathParam("shopId") Long shopId) {
        shopService.deleteShop(shopId);
        return Response.noContent().build();
    }
}
