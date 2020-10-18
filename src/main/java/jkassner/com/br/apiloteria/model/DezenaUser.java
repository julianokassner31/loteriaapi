package jkassner.com.br.apiloteria.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*
 * @created 17/10/2020 - 22:22
 * @project api-loteria
 * @author Juliano Kassner
 */
@Entity
@Table(name = "dezena_user")
@Getter
@Setter
public class DezenaUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nr_dezena")
    private int nrDezena;

    @ManyToOne
    @JoinColumn(name = "id_jogo_user", referencedColumnName = "id")
    @JsonBackReference
    private JogoUser jogoUser;

}
