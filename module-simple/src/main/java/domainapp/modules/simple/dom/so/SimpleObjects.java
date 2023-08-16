package domainapp.modules.simple.dom.so;

import java.util.List;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.TypedQuery;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.DomainService;
import org.apache.causeway.applib.annotation.NatureOfService;
import org.apache.causeway.applib.annotation.PriorityPrecedence;
import org.apache.causeway.applib.annotation.PromptStyle;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.query.Query;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.persistence.jpa.applib.services.JpaSupportService;

import lombok.RequiredArgsConstructor;

import domainapp.modules.simple.SimpleModule;
import domainapp.modules.simple.types.Name;

import org.springframework.lang.Nullable;

@Named(SimpleModule.NAMESPACE + ".SimpleObjects")
@DomainService(nature = NatureOfService.VIEW)
@Priority(PriorityPrecedence.EARLY)
@RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class SimpleObjects {

    final RepositoryService repositoryService;
    final JpaSupportService jpaSupportService;
    final SimpleObjectRepository simpleObjectRepository;


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public SimpleObject create(
            @Name final String name) {
        return repositoryService.persist(SimpleObject.withName(name));
    }


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<SimpleObject> findByNameLike(
            @Name final String name) {
        return repositoryService.allMatches(
                Query.named(SimpleObject.class, SimpleObject.NAMED_QUERY__FIND_BY_NAME_LIKE)
                     .withParameter("name", "%" + name + "%"));
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<SimpleObject> findByName(
            @Name final String name
            ) {
        return simpleObjectRepository.findByNameContaining(name);
    }


    public SimpleObject findByNameExact(final String name) {
        return simpleObjectRepository.findByName(name);
    }



    @Action(semantics = SemanticsOf.SAFE)
    public List<SimpleObject> listAll() {
        return simpleObjectRepository.findAll();
    }

    @Action(semantics = SemanticsOf.SAFE)
    public SimpleObjectViewModel listAllDto(

    ) {
        return new SimpleObjectViewModel();
    }

    @Action(semantics = SemanticsOf.SAFE)
    public SimpleObjectViewModel findByMultipleFilters(@Nullable String filter1, @Nullable Long filter2, @Nullable Integer filter3, @Nullable Boolean filter4) {
        return new SimpleObjectViewModel();
    }

    public String validateFindByMultipleFilters(String filter1, Long filter2, Integer filter3, Boolean filter4) {
        if (filter1 == null && filter2 == null) {
            return "Invalid!";
        }
        return "";
    }



    public void ping() {
        jpaSupportService.getEntityManager(SimpleObject.class)
            .mapEmptyToFailure()
            .mapSuccessAsNullable(entityManager -> {
                final TypedQuery<SimpleObject> q = entityManager.createQuery(
                                "SELECT p FROM SimpleObject p ORDER BY p.name",
                                SimpleObject.class)
                        .setMaxResults(1);
                return q.getResultList();
            })
        .ifFailureFail();
    }

}
