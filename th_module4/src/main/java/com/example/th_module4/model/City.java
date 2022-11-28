package com.example.th_module4.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city_name", nullable = false)
    @Length(min = 1, max = 30)
    private String cityName;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    @NotNull(message = "Diện  tích không được bỏ trống")
    @Min(value = 1,message = "Diện tích phải lớn hơn 0")
    @Max(value = 30000000, message = "Diện tích tối đa là 3.000.000.000")
    private BigDecimal area;

    @NotNull(message = "Dân số không được bỏ trống")
    @Min(1)
    @Max(100000000)
    private BigDecimal population;

    @NotNull(message = "GDP không được để trống")
    @Min(1)
    @Max(100000000)
    private BigDecimal GDP;

    @NotEmpty(message = "Vui lòng thêm mô tả")
    @Length(min = 1, max = 1000,message = "Mô tả từ 1 đến 1000 kí tự")
    private String description;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean deleted;
}
