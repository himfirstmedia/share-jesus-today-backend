package com.himfirst.vidshare.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T extends BaseEntity, ID extends Serializable>{
    public  T save(T entity);
    public  List<T> findAll();
    public  T findById(ID entityId);
    public  T update(T entity);
    public  T updateById(T entity, ID entityId);
    public  void delete(T entity);
    public  void deleteById(ID entityId);
    public Slice<T> findAll(Pageable pageable);
    public Page<T> findByPage(Pageable pageable);

}