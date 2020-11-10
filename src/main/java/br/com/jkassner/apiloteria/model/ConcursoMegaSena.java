package br.com.jkassner.apiloteria.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="concurso_mega_sena")
@Getter
@Setter
public class ConcursoMegaSena implements Serializable, Concurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_concurso", nullable = false)
    private Long idConcurso;

    @Column(name = "vl_acumulado", nullable = false)
    private BigDecimal vlAcumulado;

    @Column(name = "vl_acumulado_mega_virada")
    private BigDecimal vlAcumuladoMegaVirada;

    @Column(name = "dt_sorteio", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dtSorteio;

    @Column(name = "vl_estimativa_premio")
    private BigDecimal vlEstimativaPremio;

    @Column(name = "nr_ganhadores_quina", nullable = false)
    private int nrGanhadoresQuina;

    @Column(name = "nr_ganhadores_sena", nullable = false)
    private int nrGanhadoresSena;

    @Column(name = "nr_ganhadores_quadra", nullable = false)
    private int nrGanhadoresQuadra;

    @Column(name = "pr_dezena", nullable = false)
    private Integer prDezena;

    @Column(name = "se_dezena", nullable = false)
    private Integer seDezena;

    @Column(name = "te_dezena", nullable = false)
    private Integer teDezena;

    @Column(name = "qa_dezena", nullable = false)
    private Integer qaDezena;

    @Column(name = "qi_dezena", nullable = false)
    private Integer qiDezena;

    @Column(name = "sx_dezena", nullable = false)
    private Integer sxDezena;

    @Column(name = "vl_rateio_sena", nullable = false)
    private BigDecimal vlRateioSena;

    @Column(name = "vl_rateio_quina", nullable = false)
    private BigDecimal vlRateioQuina;

    @Column(name = "vl_rateio_quadra", nullable = false)
    private BigDecimal vlRateioQuadra;

    @Column(name = "acumulado", nullable = false)
    private boolean acumulado;

    @Column(name = "vl_arrecadacao_total", nullable = false)
    private BigDecimal vlArrecadacaoTotal;

    @OneToMany(mappedBy = "concursoMegaSena", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Cidade> cidades = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_dezenas_ordenadas", referencedColumnName = "id")
    @JsonIgnore
    DezenasMegaSenaOrdenadas dezenasMegaSenaOrdenadas;

    @Override
    public String toString() {
        return "ConcursoMegaSena{" +
                "idConcurso=" + idConcurso +
                ", vlAcumulado=" + vlAcumulado +
                ", vlAcumuladoMegaVirada=" + vlAcumuladoMegaVirada +
                ", dtSorteio=" + dtSorteio +
                ", vlEstimativaPremio=" + vlEstimativaPremio +
                ", nrGanhadoresQuina=" + nrGanhadoresQuina +
                ", nrGanhadoresSena=" + nrGanhadoresSena +
                ", nrGanhadoresQuadra=" + nrGanhadoresQuadra +
                ", prDezena=" + prDezena +
                ", seDezena=" + seDezena +
                ", teDezena=" + teDezena +
                ", qaDezena=" + qaDezena +
                ", qiDezena=" + qiDezena +
                ", sxDezena=" + sxDezena +
                ", vlRateioSena=" + vlRateioSena +
                ", vlRateioQuina=" + vlRateioQuina +
                ", vlRateioQuadra=" + vlRateioQuadra +
                ", acumulado=" + acumulado +
                ", vlArrecadacaoTotal=" + vlArrecadacaoTotal +
                '}';
    }
}
