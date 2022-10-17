package org.zuzex.controller;

import lombok.RequiredArgsConstructor;
import org.zuzex.dto.CategoryDto;
import org.zuzex.model.Category;
import org.zuzex.service.CategoryService;
import org.zuzex.util.mapper.ProductMapper;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@RequiredArgsConstructor
@Path("/api/v1/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductMapper productMapper;

    @PermitAll
    @GET
    @Path("/{categoryId}")
    public CategoryDto getCategoryById(@PathParam("categoryId") Long categoryId) {
        return productMapper.toCategoryDto(categoryService.getCategoryById(categoryId));
    }

    @PermitAll
    @GET
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategory()
                .stream()
                .map(productMapper::toCategoryDto)
                .toList();
    }

    @RolesAllowed("admin")
    @POST
    public Response createCategory(CategoryDto categoryDto) {
        Category category = productMapper.toCategory(categoryDto);
        CategoryDto categoryAfterSave = productMapper.toCategoryDto(categoryService.createCategory(category));
        return Response.status(Response.Status.CREATED).entity(categoryAfterSave).build();
    }

    @RolesAllowed("admin")
    @DELETE
    @Path("/{categoryId}")
    public Response deleteCategoryById(@PathParam("categoryId") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return Response.noContent().build();
    }


}