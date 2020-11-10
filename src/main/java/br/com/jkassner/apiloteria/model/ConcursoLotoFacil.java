package br.com.jkassner.apiloteria.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * @created 07/11/2020 - 12:07
 * @project api-loteria
 * @author Juliano Kassner
 */
@Entity
@Table(name = "concurso_lotofacil")
@Getter
@Setter
public class ConcursoLotoFacil implements Serializable, Concurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_concurso", nullable = false)
    private Long idConcurso;

    @Column(name = "dt_sorteio", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dtSorteio;

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

    @Column(name = "st_dezena", nullable = false)
    private Integer stDezena;

    @Column(name = "ot_dezena", nullable = false)
    private Integer otDezena;

    @Column(name = "no_dezena", nullable = false)
    private Integer noDezena;

    @Column(name = "dc_dezena", nullable = false)
    private Integer dcDezena;

    @Column(name = "dpr_dezena", nullable = false)
    private Integer dprDezena;

    @Column(name = "dse_dezena", nullable = false)
    private Integer dseDezena;

    @Column(name = "dte_dezena", nullable = false)
    private Integer dteDezena;

    @Column(name = "dqa_dezena", nullable = false)
    private Integer dqaDezena;

    @Column(name = "dqi_dezena", nullable = false)
    private Integer dqiDezena;

    @Column(name = "vl_arrecadacao_total", nullable = false)
    private BigDecimal vlArrecadacaoTotal;

    @Column(name = "nr_ganhadores_15_num")
    private int nrGanhadores15Num;

    @Column(name = "nr_ganhadores_14_num")
    private int nrGanhadores14Num;

    @Column(name = "nr_ganhadores_13_num")
    private int nrGanhadores13Num;

    @Column(name = "nr_ganhadores_12_num")
    private int nrGanhadores12Num;

    @Column(name = "nr_ganhadores_11_num")
    private int nrGanhadores11Num;

    @Column(name = "vl_rateio_15_num", nullable = false)
    private BigDecimal vlRateio15Num;

    @Column(name = "vl_rateio_14_num", nullable = false)
    private BigDecimal vlRateio14Num;

    @Column(name = "vl_rateio_13_num", nullable = false)
    private BigDecimal vlRateio13Num;

    @Column(name = "vl_rateio_12_num", nullable = false)
    private BigDecimal vlRateio12Num;

    @Column(name = "vl_rateio_11_num", nullable = false)
    private BigDecimal vlRateio11Num;

    @Column(name = "vl_acumulado_15_num", nullable = false)
    private BigDecimal vlAcumulado15Num;

    @Column(name = "vl_estimativa_premio")
    private BigDecimal vlEstimativaPremio;

    @Column(name = "vl_acumulado_especial")
    private BigDecimal vlAcumuladoEspecial;

    @OneToMany(mappedBy = "concursoLotoFacil", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Cidade> cidades = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_dezenas_ordenadas", referencedColumnName = "id")
    @JsonIgnore
    DezenasLotoFacilOrdenadas dezenasLotoFacilOrdenadas;
}
