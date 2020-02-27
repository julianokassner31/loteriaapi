package jkassner.com.br.apiloteria.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="dezenas_mega_sena_ordenadas")
@Getter
@Setter
public class DezenasMegaSenaOrdenadas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "dezenasMegaSenaOrdenadas")
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
