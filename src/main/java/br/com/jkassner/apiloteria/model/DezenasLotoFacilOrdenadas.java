package br.com.jkassner.apiloteria.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="dezenas_lotofacil_ordenadas")
@Getter
@Setter
public class DezenasLotoFacilOrdenadas implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "dezenasLotoFacilOrdenadas")
    @JsonBackReference
    private ConcursoLotoFacil concursoLotoFacil;

    @Column(name = "primeira", nullable = false)
    private Integer primeira;

    @Column(name = "segunda", nullable = false)
    private Integer segunda;

    @Column(name = "terceira", nullable = false)
    private Integer terceira;

    @Column(name = "quarta", nullable = false)
    private Integer quarta;

    @Column(name = "quinta", nullable = false)
    private Integer quinta;

    @Column(name = "sexta", nullable = false)
    private Integer sexta;

    @Column(name = "setima", nullable = false)
    private Integer setima;

    @Column(name = "oitava", nullable = false)
    private Integer oitava;

    @Column(name = "nona", nullable = false)
    private Integer nona;

    @Column(name = "decima", nullable = false)
    private Integer decima;

    @Column(name = "decimaPrimeira", nullable = false)
    private Integer decimaPrimeira;

    @Column(name = "decimaSegunda", nullable = false)
    private Integer decimaSegunda;

    @Column(name = "decimaTerceira", nullable = false)
    private Integer decimaTerceira;

    @Column(name = "decimaQuarta", nullable = false)
    private Integer decimaQuarta;

    @Column(name = "decimaQuinta", nullable = false)
    private Integer decimaQuinta;

}
