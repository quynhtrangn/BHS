package net.javaguides.springboot.models;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import net.javaguides.springboot.models.Market;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="city")
@Builder
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long city_id;

    @Column(name = "city_name")
    private String city_name;

    @JsonIgnore
    @OneToMany(mappedBy = "city")
    private List<Market> markets;

//    @OneToMany(mappedBy = "city")
//    private List<Location> locations;
}
