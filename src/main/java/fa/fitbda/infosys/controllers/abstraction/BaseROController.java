package fa.fitbda.infosys.controllers.abstraction;


import fa.fitbda.infosys.dto.BaseDTO;
import fa.fitbda.infosys.entities.BaseEntity;
import fa.fitbda.infosys.mappers.abstractions.Mapper;
import fa.fitbda.infosys.services.abstractions.BaseROService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public abstract class BaseROController<Entity extends BaseEntity, DTO extends BaseDTO> {

    @Setter(onMethod_ = {@Autowired})
    protected BaseROService<Entity> baseROService;
    @Setter(onMethod_ = {@Autowired})
    protected Mapper<Entity, DTO> baseMapper;

    @SuppressWarnings("unchecked")
    protected final Class<Entity> entityClass = ((Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass())
            .getActualTypeArguments()[0]);
    @SuppressWarnings("unchecked")
    protected final Class<Entity> dtoClass = ((Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass())
            .getActualTypeArguments()[1]);

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Page<DTO>> get(Pageable pageable) {
        Page<Entity> page = baseROService.getAll(pageable);
        return ResponseEntity.ok(new PageImpl<>(baseROService.getAll(pageable).stream()
                .map(entity -> baseMapper.toDto(entity))
                .collect(Collectors.toList()), page.getPageable(), page.getTotalElements()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<DTO> get(@PathVariable Long id) {
        return baseROService.get(id).map(entity -> baseMapper.toDto(entity)).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
