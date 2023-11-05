package net.javaguides.springboot.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="field")
@Builder
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long field_id;

    @Column(name = "first_name")
    @NotBlank(message = "Không được để trống")
    private String fieldName;

    @OneToMany(mappedBy = "field")
    @JsonIgnore
    private List<WorkWith> workWith;

}
