package com.himfirst.vidshare.base;

import com.himfirst.vidshare.security.MyUserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class BaseController<T extends BaseEntity, Model extends BaseModel> {

    private final BaseService<T, UUID> service;
    private final ModelMapper modelMapper;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    public BaseController(BaseService<T, UUID> service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }
    public abstract Class<Model> getClassModelType ();

    public  abstract Class<T> getClassType();

    @GetMapping("/{id}")
    public ResponseEntity<Model> getOne(@PathVariable UUID id){
        T t = service.findById(id);
        Model model =  modelMapper.map(t, this.getClassModelType());
        return ResponseEntity.ok(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Model> updateById(@Valid @RequestBody Model updated, @PathVariable UUID id){
        T type =  modelMapper.map(updated, getClassType());
        type.setUpdatedBy(myUserDetailsService.currentUser().getId());
        T responseType = service.updateById(type, id);
        Model model =  modelMapper.map(responseType, this.getClassModelType());

        return new ResponseEntity<>(model, HttpStatus.ACCEPTED);
    }
    @PostMapping
    public ResponseEntity<Model> create(@Valid @RequestBody  Model created){
        T type =  modelMapper.map(created, getClassType());
        type.setId(UUID.randomUUID());
        type.setCreatedBy(myUserDetailsService.currentUser().getId());
        type.setUpdatedBy(myUserDetailsService.currentUser().getId());
        type.setCreatedTimestamp(LocalDateTime.now());
        T responseType = service.save(type);
        Model model =  modelMapper.map(responseType, this.getClassModelType());
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id){
        service.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Model>> getAll(
            @RequestParam(defaultValue = "0") int page ,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "createdTimestamp") String sortBy
    ){
        Pageable sortedPage =
                PageRequest.of(page, size, Sort.by(sortBy).descending());
        List<Model> models = new ArrayList<>();
          return ResponseEntity.ok(models);
    }
}