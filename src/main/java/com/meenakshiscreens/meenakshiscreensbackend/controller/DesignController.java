package com.meenakshiscreens.meenakshiscreensbackend.controller;

import com.meenakshiscreens.meenakshiscreensbackend.entity.Category;
import com.meenakshiscreens.meenakshiscreensbackend.entity.Design;
import com.meenakshiscreens.meenakshiscreensbackend.entity.User;
import com.meenakshiscreens.meenakshiscreensbackend.entity.request.DesignRequest;
import com.meenakshiscreens.meenakshiscreensbackend.enums.Role;
import com.meenakshiscreens.meenakshiscreensbackend.exception.DuplicateEntityException;
import com.meenakshiscreens.meenakshiscreensbackend.exception.EntityNotFoundException;
import com.meenakshiscreens.meenakshiscreensbackend.exception.RequestValidationException;
import com.meenakshiscreens.meenakshiscreensbackend.service.CategoryService;
import com.meenakshiscreens.meenakshiscreensbackend.service.DesignService;
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
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@EnableHypermediaSupport(type = {EnableHypermediaSupport.HypermediaType.HAL})
@RequestMapping(value = "/api/private/v1/designs")
public class DesignController {

    @Autowired
    private DesignService designService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private PagedResourcesAssembler<Design> designPagedResourcesAssembler;

    @GetMapping("")
    public ResponseEntity<?> getDesigns(@PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {
        Page<Design> designs = designService.getDesigns(pageable);
        return new ResponseEntity<>(designs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDesignById(@PathVariable("id") Long id) {
        if (id == null) {
            throw new RequestValidationException("Id cannot be null");
        }
        Design design = designService.getById(id);
        if (design == null) {
            return new ResponseEntity<>(String.format("Design with %s id not found.", id), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(design, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createDesign(@Valid @RequestBody DesignRequest designRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authorisedUser = userService.getByUserName(authentication.getName());
        if (!Role.ADMIN.equals(authorisedUser.getRole())) {
            return new ResponseEntity<String>(String.format("User %s not authorised to perform this action", authentication.getName()), HttpStatus.UNAUTHORIZED);
        }
        Long requestCategoryId = designRequest.getCategoryId();
        if (requestCategoryId == null) {
            throw new RequestValidationException("Category id cannot be null.");
        }
        Category categoryRequest = categoryService.getById(designRequest.getCategoryId());
        if (categoryRequest == null) {
            throw new EntityNotFoundException(String.format("Category with id %s not found", designRequest.getCategoryId()));
        }
        Design existingDesign = designService.getByDesignName(designRequest.getDesignName());
        if (existingDesign != null) {
            throw new DuplicateEntityException("Design with %s name already exists.");
        }
        if (!StringUtils.hasText(designRequest.getDesignName())) {
            throw new RequestValidationException("Design name cannot be null.");
        }
        Design design = new Design();
        design.setDesignName(designRequest.getDesignName());
        design.setImg(designRequest.getImg());
        design.setCategory(categoryRequest);
        designService.saveDesign(design);
        return new ResponseEntity<>(design, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateDesign(@Valid @PathVariable Long id, @RequestBody DesignRequest designRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authorisedUser = userService.getByUserName(authentication.getName());
        if (!Role.ADMIN.equals(authorisedUser.getRole())) {
            return new ResponseEntity<String>(String.format("User %s not authorised to perform this action", authentication.getName()), HttpStatus.UNAUTHORIZED);
        }
        Design designToUpdate = designService.getById(id);
        if (designToUpdate == null) {
            throw new EntityNotFoundException(String.format("Design with %s id not found.", id));
        }
        if (designRequest.getCategoryId() != null || !ObjectUtils.isEmpty(designRequest.getCategoryId())) {
            Category categoryToUpdate = categoryService.getById(designRequest.getCategoryId());
            if (categoryToUpdate == null) {
                throw new EntityNotFoundException(String.format("Category with %s id does not exists.", designRequest.getCategoryId()));
            }
            designToUpdate.setCategory(categoryToUpdate);
        }
        if (designRequest.getDesignName() != null || !ObjectUtils.isEmpty(designRequest.getDesignName())) {
            designToUpdate.setDesignName(designRequest.getDesignName());
        }
        if (designRequest.getImg() != null || !ObjectUtils.isEmpty(designRequest.getImg())) {
            designToUpdate.setImg(designRequest.getImg());
        }
        designService.saveDesign(designToUpdate);
        return new ResponseEntity<>(designToUpdate, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDesign(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authorisedUser = userService.getByUserName(authentication.getName());
        if (!Role.ADMIN.equals(authorisedUser.getRole())) {
            return new ResponseEntity<String>(String.format("User %s not authorised to perform this action", authentication.getName()), HttpStatus.UNAUTHORIZED);
        }
        Design designToDelete = designService.getById(id);
        if (designToDelete == null) {
            throw new EntityNotFoundException(String.format("Design with %s id not found.", id));
        }
        try {
            designService.deleteDesign(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<String>(String.format("Design with %s id is currently being used.", id), HttpStatus.IM_USED);
        }
    }
}
