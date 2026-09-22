package com.example.coche.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
@Table(name = "coche")
public class Coche {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La marca es obligatoria")
    @Size(max = 50, message = "La marca no puede superar los 50 caracteres")
    @Column(name = "marca", nullable = false, length = 50)
    private String marca;

    @NotBlank(message = "El modelo es obligatorio")
    @Size(max = 50, message = "El modelo no puede superar los 50 caracteres")
    @Column(name = "modelo", nullable = false, length = 50)
    private String modelo;

    @NotBlank(message = "La matricula es obligatoria")
    @Size(max = 15, message = "La matricula no puede superar los 15 caracteres")
    @Column(name = "matricula", nullable = false, unique = true, length = 15)
    private String matricula;

    @NotNull(message = "El anio de fabricacion es obligatorio")
    @Min(value = 1900, message = "El anio de fabricacion no es valido")
    @Max(value = 2100, message = "El anio de fabricacion no es valido")
    @Column(name = "anio_fabricacion", nullable = false)
    private Integer anioFabricacion;

    @NotBlank(message = "El color es obligatorio")
    @Size(max = 30, message = "El color no puede superar los 30 caracteres")
    @Column(name = "color", nullable = false, length = 30)
    private String color;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @NotNull(message = "El kilometraje es obligatorio")
    @PositiveOrZero(message = "El kilometraje no puede ser negativo")
    @Column(name = "kilometraje", nullable = false)
    private Integer kilometraje;

    @NotNull(message = "El combustible es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "combustible", nullable = false, length = 20)
    private Combustible combustible;

    @NotNull(message = "La transmision es obligatoria")
    @Enumerated(EnumType.STRING)
    @Column(name = "transmision", nullable = false, length = 20)
    private Transmision transmision;

    public Coche() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Integer getAnioFabricacion() {
        return anioFabricacion;
    }

    public void setAnioFabricacion(Integer anioFabricacion) {
        this.anioFabricacion = anioFabricacion;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(Integer kilometraje) {
        this.kilometraje = kilometraje;
    }

    public Combustible getCombustible() {
        return combustible;
    }

    public void setCombustible(Combustible combustible) {
        this.combustible = combustible;
    }

    public Transmision getTransmision() {
        return transmision;
    }

    public void setTransmision(Transmision transmision) {
        this.transmision = transmision;
    }
}
