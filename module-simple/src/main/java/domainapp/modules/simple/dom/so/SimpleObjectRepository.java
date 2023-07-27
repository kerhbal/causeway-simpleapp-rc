package domainapp.modules.simple.dom.so;

import java.util.List;

import domainapp.modules.simple.dto.SimpleDto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SimpleObjectRepository extends JpaRepository<SimpleObject, Long> {

    List<SimpleObject> findByNameContaining(final String name);

    SimpleObject findByName(final String name);

    @Query("SELECT new domainapp.modules.simple.dto.SimpleDto(so.id, so.name) FROM SimpleObject so WHERE so.id IN :ids")
    List<SimpleDto> findAllDtoByIds(@Param("ids") List<Long> ids);

    @Query("SELECT so FROM SimpleObject so WHERE so.id IN :ids")
    List<SimpleObject> findAllByIds(@Param("ids") List<Long> ids);

    @Query("SELECT so FROM SimpleObject so WHERE so.id IN :ids AND so.name IS NOT NULL")
    List<SimpleObject> anotherQuery(@Param("ids") List<Long> ids);

}
