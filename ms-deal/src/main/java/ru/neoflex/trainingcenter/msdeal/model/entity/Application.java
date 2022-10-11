package ru.neoflex.trainingcenter.msdeal.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import ru.neoflex.trainingcenter.msdeal.model.ApplicationStatus;
import ru.neoflex.trainingcenter.msdeal.model.dto.LoanOfferDto;
import ru.neoflex.trainingcenter.msdeal.type.ListType;
import ru.neoflex.trainingcenter.msdeal.type.LoanOfferType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "application")
@TypeDefs(value = {
        @TypeDef(name = "ListType", typeClass = ListType.class, defaultForType = List.class),
        @TypeDef(name = "LoanOfferType", typeClass = LoanOfferType.class, defaultForType = LoanOfferDto.class)})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Application implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client", referencedColumnName = "id", nullable = false)
    private Client client;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credit")
    private Credit credit;

    private ApplicationStatus status;

    private LocalDate creationDate;

    @Type(type = "LoanOfferType")
    @Column(columnDefinition = "JSON")
    private LoanOfferDto appliedOffer;

    private LocalDate signDate;

    private String sesCode;

    @Type(type = "ListType")
    @Column(columnDefinition = "JSON")
    private List<ApplicationStatusHistory> statusHistory;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Application that = (Application) o;

        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
