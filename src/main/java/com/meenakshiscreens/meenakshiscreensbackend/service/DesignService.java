package com.meenakshiscreens.meenakshiscreensbackend.service;

import com.meenakshiscreens.meenakshiscreensbackend.entity.Design;
import com.meenakshiscreens.meenakshiscreensbackend.repository.DesignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * Service class to handle operations releated to design
 *
 * @author navinkc
 *
 */
@Service
public interface DesignService {

    /**
     * To get the AccessChannel with the given name.
     *
     * @param designName
     * @return design
     */
    public Design getByDesignName(String designName);


    public Page<Design> getDesigns(Pageable pageable);

    /**
     * To get the AccessChannel with the given name.
     *
     * @param id
     * @return design
     */
    public Design getById(Long id);

    public void saveDesign(Design design);

    public void deleteDesign(Long id);
}
