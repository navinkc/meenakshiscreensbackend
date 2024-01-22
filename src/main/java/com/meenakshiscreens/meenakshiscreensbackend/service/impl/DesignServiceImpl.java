package com.meenakshiscreens.meenakshiscreensbackend.service.impl;

import com.meenakshiscreens.meenakshiscreensbackend.entity.Design;
import com.meenakshiscreens.meenakshiscreensbackend.repository.DesignRepository;
import com.meenakshiscreens.meenakshiscreensbackend.service.DesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DesignServiceImpl implements DesignService {

    @Autowired
    private DesignRepository designRepository;

    @Override
    public Design getByDesignName(String designName){
        return designRepository.findByDesignName(designName).orElse(null);
    }

    @Override
    public Page<Design> getDesigns(Pageable pageable){
        return designRepository.findAll(pageable);
    }

    @Override
    public Design getById(Long id){
        return designRepository.findById(id).orElse(null);
    }

    @Override
    public void saveDesign(Design design){
        designRepository.save(design);
    }

    @Override
    public void deleteDesign(Long id) {
        designRepository.deleteById(id);
    }
}
