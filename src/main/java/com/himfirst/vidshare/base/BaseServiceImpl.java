package com.himfirst.vidshare.base;

import com.himfirst.vidshare.exceptions.ApiResponseException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public abstract class BaseServiceImpl<T extends BaseEntity, ID extends Serializable> implements BaseService<T, ID> {

	private BaseRepository<T, ID> abstractBaseRepository;
	private final ModelMapper modelMapper;

	public abstract Class<T> getClassName();


	@Autowired
	public BaseServiceImpl(BaseRepository<T, ID> abstractBaseRepository, ModelMapper modelMapper ) {
		this.abstractBaseRepository = abstractBaseRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public T save(T entity) {
		return (T) abstractBaseRepository.save(entity);
	}

	@Override
	public List<T> findAll() {
		return abstractBaseRepository.findAll();
	}

	@Override
	public Slice<T> findAll(Pageable pageable) {
		return abstractBaseRepository.findAll(pageable);
	}

	@Override
	public Page<T> findByPage(Pageable pageable) {
		return abstractBaseRepository.findAll(pageable);
	}

	@Override
	public T findById(ID entityId) {
		Optional<T> t = abstractBaseRepository.findById(entityId);
		if (t.isPresent()) {
			return t.get();
		}
		throw new ApiResponseException(
				"The " + getClassName().getSimpleName() + " with id " + entityId + " does not exist");
	}

	@Override
    public T update(T entity) {
		return abstractBaseRepository.save(entity);
	}

	@Override
    public T updateById(T entity, ID entityId) {
		Optional<T> optional = abstractBaseRepository.findById(entityId);
		if (optional.isPresent()) {
			entity.setId(optional.get().getId());
			entity.setCreatedTimestamp(optional.get().getCreatedTimestamp());
			entity.setCreatedBy(optional.get().getCreatedBy());

			return (T) abstractBaseRepository.save(entity);
		} else {
			throw new ApiResponseException(
					"The " + getClassName().getSimpleName() + " with id " + entityId + " does not exist");
		}
	}

	@Override
	public void delete(T entity) {
		abstractBaseRepository.delete(entity);
	}

	@Override
	public void deleteById(ID entityId) {
		Optional<T> optional = abstractBaseRepository.findById(entityId);
		if (optional.isPresent()) {
			abstractBaseRepository.deleteById(entityId);
		} else {
			throw new ApiResponseException(
					"The " + getClassName().getSimpleName() + " with id " + entityId + " does not exist");
		}

	}

}
