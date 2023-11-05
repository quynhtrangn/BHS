package net.javaguides.springboot.models;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import net.javaguides.springboot.user.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="market")
@Builder
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long market_id;

    @Column(name = "market_name")
    private String market_name;

    @Column(name = "address")
    private String address;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "city_id")
    public City city;

    @OneToMany(mappedBy = "market",fetch = FetchType.EAGER)
    @JsonIgnore
    private List<WorkPlace> workPlaces;

}
