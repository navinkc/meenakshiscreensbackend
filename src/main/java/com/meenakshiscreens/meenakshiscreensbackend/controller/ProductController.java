package com.meenakshiscreens.meenakshiscreensbackend.controller;

import com.meenakshiscreens.meenakshiscreensbackend.entity.Product;
import com.meenakshiscreens.meenakshiscreensbackend.entity.User;
import com.meenakshiscreens.meenakshiscreensbackend.entity.request.ProductRequest;
import com.meenakshiscreens.meenakshiscreensbackend.enums.Role;
import com.meenakshiscreens.meenakshiscreensbackend.exception.DuplicateEntityException;
import com.meenakshiscreens.meenakshiscreensbackend.exception.EntityNotFoundException;
import com.meenakshiscreens.meenakshiscreensbackend.exception.RequestValidationException;
import com.meenakshiscreens.meenakshiscreensbackend.service.CategoryService;
import com.meenakshiscreens.meenakshiscreensbackend.service.ProductService;
import com.meenakshiscreens.meenakshiscreensbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;

@RestController
@EnableHypermediaSupport(type = {EnableHypermediaSupport.HypermediaType.HAL})
@RequestMapping(value = "/api/private/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private PagedResourcesAssembler<Product> designPagedResourcesAssembler;

    @GetMapping("")
    public ResponseEntity<?> getProducts(@PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {
        Page<Product> products = productService.getProducts(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id) {
        Product product = productService.getById(id);
        if (product == null) {
            return new ResponseEntity<>(String.format("Product with %s id not found.", id), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authorisedUser = userService.getByUserName(authentication.getName());
        if (!Role.ADMIN.equals(authorisedUser.getRole())) {
            return new ResponseEntity<String>(String.format("User %s not authorised to perform this action", authentication.getName()), HttpStatus.UNAUTHORIZED);
        }
        Product existingProduct = productService.getByProductName(productRequest.getProductName());
        if (existingProduct != null) {
            throw new DuplicateEntityException(String.format("Product with %s name already exists", productRequest.getProductName()));
        }
        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        productService.saveProduct(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProduct(@Valid @PathVariable Long id, @RequestBody ProductRequest productRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authorisedUser = userService.getByUserName(authentication.getName());
        if (!Role.ADMIN.equals(authorisedUser.getRole())) {
            return new ResponseEntity<String>(String.format("User %s not authorised to perform this action", authentication.getName()), HttpStatus.UNAUTHORIZED);
        }
        if (id == null) {
            throw new RequestValidationException("Id cannot be null.");
        }
        String existingProductName = productService.findProductName(id);
        if (existingProductName == null) {
            throw new EntityNotFoundException(String.format("Product with %s id does not exists.", id));
        }
        if (productRequest.getProductName().equals(existingProductName)) {
            throw new DuplicateEntityException("Please modify the productName field and update.");
        }
        Product updateProduct = productService.getById(id);
        updateProduct.setProductName(productRequest.getProductName());
        productService.saveProduct(updateProduct);
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authorisedUser = userService.getByUserName(authentication.getName());
        if (!Role.ADMIN.equals(authorisedUser.getRole())) {
            return new ResponseEntity<String>(String.format("User %s not authorised to perform this action", authentication.getName()), HttpStatus.UNAUTHORIZED);
        }
        if (id == null) {
            throw new RequestValidationException("Id cannot be null.");
        }
        Product existingProduct = productService.getById(id);
        if (existingProduct == null) {
            throw new EntityNotFoundException(String.format("Product with %s id does not exists.", id));
        }
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<String>("The product is currently in use.", HttpStatus.IM_USED);
        }
    }

}
