package domainapp.modules.simple.dom.so;

import domainapp.modules.simple.dto.SimpleDto;

import lombok.AccessLevel;

import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.NoArgsConstructor;

import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.Title;

import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

@Named("simple.SimpleObjectViewModel")
@DomainObject(nature = Nature.VIEW_MODEL)
@XmlRootElement(name = "SimpleObjectViewModel")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class SimpleObjectViewModel {
    @Autowired
    @XmlTransient
    private SimpleObjectRepository simpleObjectRepository;

    @Collection
    @CollectionLayout(paged = 30)
    public List<SimpleDto> getDtoList(){
        return simpleObjectRepository.findAllDtoByIds(List.of(1L));
    }


    @Title
    @XmlTransient
    @Getter
    private final String viewModelTitle = "Dto List View Model";

}