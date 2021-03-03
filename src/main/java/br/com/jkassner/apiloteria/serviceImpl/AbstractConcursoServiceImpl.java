package br.com.jkassner.apiloteria.serviceImpl;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.jkassner.apiloteria.model.Concurso;
import br.com.jkassner.apiloteria.model.TipoLoteria;
import br.com.jkassner.apiloteria.repository.abstractconcurso.AbstractConcursoRepository;
import br.com.jkassner.apiloteria.service.AbstractConcursoService;

/*
 * @created 08/11/2020 - 02:38
 * @project api-loteria
 * @author Juliano Kassner
 */
public abstract class AbstractConcursoServiceImpl<T extends Concurso> implements AbstractConcursoService<T> {

	private String uri;
	
	private AbstractConcursoRepository<T> repository;

    public AbstractConcursoServiceImpl(
            AbstractConcursoRepository<T> repository,
            String uri
    ) {
        this.repository = repository;
        this.uri = uri;
    }

    @Override
    public T findByIdConcurso(Long idConcurso) {
        return repository.findByIdConcurso(idConcurso);
    }

    @Override
    public Map<String, List<T>> findConcursosByDezenas(List<Integer> dezenasUsuario) {
        return null;
    }

    @Override
    public T getUltimoConcurso() {
        T ultimoConcursoLocal = repository.findFirstByOrderByDtSorteioDesc();

        LocalDate dataConcurso = Instant.ofEpochMilli(ultimoConcursoLocal.getDtSorteio().getTime())
                .atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDateTime agora = LocalDateTime.now();

        if (concursoEDeHoje(dataConcurso, agora.toLocalDate())) {
            return ultimoConcursoLocal;
        }

        RestTemplate restTemplate = new RestTemplate();
        URI uri = null;
		try {
			uri = new URI("http://localhost:8081".concat(this.uri));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
        
		RequestEntity<Void> req = RequestEntity.get(uri).build();
		
		ResponseEntity<? extends Concurso> response = restTemplate.exchange(req, ultimoConcursoLocal.getClass());
        
        return (T) response.getBody();
    }

    @Override
    public List<T> allConcursos(int page, TipoLoteria tpLoteria) {
        Sort sort = Sort.by(Sort.Direction.DESC, "idConcurso");
        PageRequest of = PageRequest.of(page, 10, sort);

       return repository.findAll(of).stream().collect(Collectors.toList());
    }

    private boolean concursoEDeHoje(LocalDate dataConcurso, LocalDate hoje) {

        return dataConcurso.isEqual(hoje);
    }
}
