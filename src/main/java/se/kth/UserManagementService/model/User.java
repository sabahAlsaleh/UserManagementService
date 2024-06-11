package se.kth.UserManagementService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String institution;
    private String position;
    @Column(name = "user_rank")
    private String rank;
    private String address;
    private LocalDate birthdate;
    private String fatherName;
    private String motherName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public int getAge() {
        return Period.between(this.birthdate, LocalDate.now()).getYears();
    }

    public void setParentsNamesIfUnder18(String fatherName, String motherName) {
        if (getAge() < 18) {
            this.fatherName = fatherName;
            this.motherName = motherName;
        } else {
            this.fatherName = null;
            this.motherName = null;
        }
    }
}