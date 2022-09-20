package ru.neoflex.trainingcenter.msdeal.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.neoflex.trainingcenter.msdeal.model.Employment;
import ru.neoflex.trainingcenter.msdeal.model.Gender;
import ru.neoflex.trainingcenter.msdeal.model.MaritalStatus;
import ru.neoflex.trainingcenter.msdeal.type.EmploymentType;
import ru.neoflex.trainingcenter.msdeal.type.PassportType;

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
@Table(name = "client")
@TypeDef(name = "PassportType", typeClass = PassportType.class, defaultForType = Passport.class)
@TypeDef(name = "EmploymentType", typeClass = EmploymentType.class, defaultForType = Employment.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Client implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    private String lastName;

    private String firstName;

    private String middleName;

    private LocalDate birthDate;

    private String email;

    private Gender gender;

    private MaritalStatus maritalStatus;

    private Integer dependentAmount;

    @Type(type = "PassportType")
    @Column(columnDefinition = "JSON")
    private Passport passport;

    @Type(type = "EmploymentType")
    @Column(columnDefinition = "JSON")
    private Employment employment;

    private String account;

    @OneToMany
    @JoinColumn(name = "client")
    @ToString.Exclude
    private List<Application> applications;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Client client = (Client) o;

        return id != null && Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
