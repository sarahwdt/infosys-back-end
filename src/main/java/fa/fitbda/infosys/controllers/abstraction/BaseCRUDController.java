package fa.fitbda.infosys.controllers.abstraction;

import fa.fitbda.infosys.dto.BaseDTO;
import fa.fitbda.infosys.entities.BaseEntity;
import fa.fitbda.infosys.entities.User;
import fa.fitbda.infosys.services.abstractions.BaseCRUDService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public abstract class BaseCRUDController<Entity extends BaseEntity, DTO extends BaseDTO>
        extends BaseROController<Entity, DTO> {

    @Setter(onMethod_ = {@Autowired})
    protected BaseCRUDService<Entity> baseCRUDService;


    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<DTO> create(@RequestBody DTO entity) {
        Entity auditedEntity = baseMapper.toEntity(entity);
        auditedEntity.setCreatedBy((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        auditedEntity.setCreatedDate(LocalDateTime.now());
        return ResponseEntity.ok(baseMapper.toDto(baseCRUDService.create(auditedEntity)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<DTO> update(@PathVariable Long id, @RequestBody DTO entity) {
        return baseROService.get(id).map(entity1 -> {
            Entity auditedEntity = baseMapper.toEntity(entity);
            entity1.setLastModifiedBy((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            entity1.setLastModifiedDate(LocalDateTime.now());
            ReflectionUtils.doWithFields(auditedEntity.getClass(), field -> {
                Optional.ofNullable(ReflectionUtils.findMethod(entityClass, "get" + StringUtils.capitalize(field.getName())))
                        .ifPresent(getMethod -> {
                            Object getResult = ReflectionUtils.invokeMethod(getMethod, auditedEntity) instanceof Optional ?
                                    ((Optional<?>) ReflectionUtils.invokeMethod(getMethod, auditedEntity)).orElse(null)
                                    : ReflectionUtils.invokeMethod(getMethod, auditedEntity);
                            if (getResult != null) {
                                Class<?> resultClass = getResult.getClass();
                                Method setMethod = ReflectionUtils.findMethod(entityClass, "set" + StringUtils.capitalize(field.getName()), getResult.getClass());

                                if (setMethod != null)
                                    ReflectionUtils.invokeMethod(setMethod, entity1, getResult);
                                else Arrays.stream(resultClass.getInterfaces()).filter(aClass ->
                                        ReflectionUtils.findMethod(entityClass, "set" + StringUtils.capitalize(field.getName()), aClass) != null)
                                        .map(aClass -> ReflectionUtils.findMethod(entityClass, "set" + StringUtils.capitalize(field.getName()), aClass))
                                        .findFirst().ifPresent(setMethod1 -> ReflectionUtils.invokeMethod(setMethod1, entity1, getResult));
                            }
                        });
            });
            return baseMapper.toDto(baseCRUDService.update(entity1));
        }).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return baseROService.get(id).map(entity -> baseCRUDService.delete(entity)).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}