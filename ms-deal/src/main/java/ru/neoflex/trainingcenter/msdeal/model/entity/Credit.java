package ru.neoflex.trainingcenter.msdeal.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.neoflex.trainingcenter.msdeal.model.CreditStatus;
import ru.neoflex.trainingcenter.msdeal.type.ListType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
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
@Table(name = "credit")
@TypeDef(name = "ListType", typeClass = ListType.class, defaultForType = List.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Credit {

    @Id
    @GeneratedValue
    private UUID id;

    private BigDecimal amount;

    private Integer term;

    private BigDecimal monthlyPayment;

    private BigDecimal rate;

    private BigDecimal psk;

    @Type(type = "ListType")
    @Column(columnDefinition = "JSON")
    private List<PaymentScheduleElement> paymentSchedule;

    private Boolean isInsuranceEnabled;

    private Boolean isSalaryClient;

    private CreditStatus creditStatus;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Credit credit = (Credit) o;

        return id != null && Objects.equals(id, credit.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
