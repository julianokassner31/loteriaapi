package jkassner.com.br.apiloteria.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/*
 * @created 17/10/2020 - 22:14
 * @project api-loteria
 * @author Juliano Kassner
 */
@Entity
@Table(name = "jogo_user")
@Getter
@Setter
public class JogoUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @JsonBackReference
    private User user;

    @Column(name = "tp_loteria")
    @Enumerated(EnumType.STRING)
    private TipoLoteria tipoLoteria;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "jogoUser", cascade = CascadeType.ALL)
    private List<DezenaUser> dezenaUsers = new ArrayList<>();

}
