package br.com.jkassner.apiloteria.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="dezenas_mega_sena_ordenadas")
@Getter
@Setter
public class DezenasMegaSenaOrdenadas implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "dezenasMegaSenaOrdenadas")
    @JsonBackReference
    private ConcursoMegaSena concursoMegaSena;

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

}
