package domainapp.modules.simple.dto;

import domainapp.modules.simple.dom.so.SimpleObject;
import domainapp.modules.simple.dom.so.SimpleObjectRepository;

import lombok.*;

import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.Title;

import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import javax.xml.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Named("simple.SimpleDto")
@DomainObject(nature = Nature.VIEW_MODEL)
@XmlRootElement(name = "SimpleDto")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        propOrder = {
                "id",
                "name",
                "field1",
                "field2",
                "field3"
        }
)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class SimpleDto {
    @Getter @Setter
    private Long id;

    @Title
    @Getter @Setter
    private String name;

    @Autowired
    @XmlTransient
    private SimpleObjectRepository simpleObjectRepository;

    @Collection
    @CollectionLayout(paged = 50)
    public List<SimpleObject> getEntityList() {
        return simpleObjectRepository.findAllByIds(List.of(id));
    }

    public SimpleDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}