package jkassner.com.br.apiloteria.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

@Entity
@Table(name = "cidade")
@Getter
@Setter
public class Cidade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nm_cidade")
    private String nmCIdade;

    @Column(name = "uf")
    private String uf;

    @ManyToOne
    @JoinColumn(name = "id_concurso_megasena", referencedColumnName = "id_concurso")
    @JsonIgnore
    private ConcursoMegaSena concursoMegaSena;

}
