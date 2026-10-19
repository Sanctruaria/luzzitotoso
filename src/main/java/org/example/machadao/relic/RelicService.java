package org.example.machadao.relic;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class RelicService {

    private final RelicRepository repository;

    public RelicService(RelicRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<RelicResponse> findAll() {
        return repository.findAllByOrderByIdAsc().stream()
                .map(RelicResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public RelicResponse findById(Long id) {
        return RelicResponse.from(getRelic(id));
    }

    public RelicResponse create(RelicRequest request) {
        if (repository.existsByCode(request.code())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe uma relíquia com esse código.");
        }

        Relic relic = new Relic(
                request.code(),
                request.name(),
                request.description(),
                request.illustrativeValueUsd()
        );
        return RelicResponse.from(repository.save(relic));
    }

    public RelicResponse update(Long id, RelicRequest request) {
        Relic relic = getRelic(id);
        if (repository.existsByCodeAndIdNot(request.code(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe uma relíquia com esse código.");
        }

        relic.setCode(request.code());
        relic.setName(request.name());
        relic.setDescription(request.description());
        relic.setIllustrativeValueUsd(request.illustrativeValueUsd());
        return RelicResponse.from(repository.save(relic));
    }

    public void delete(Long id) {
        repository.delete(getRelic(id));
    }

    private Relic getRelic(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Relíquia não encontrada."));
    }
}
