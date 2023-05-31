package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
public class MemoryCandidateRepository implements CandidateRepository {
    private AtomicInteger nextId = new AtomicInteger(1);
    private final Map<Integer, Candidate> candidates = new HashMap<>();

    public MemoryCandidateRepository() {
        save(new Candidate(1, "Sergey Bazhukov", "Java Developer",
                LocalDateTime.of(2023, 1, 15, 10, 0)));
        save(new Candidate(2, "Sergey Bazhukov", "Java Developer",
                LocalDateTime.of(2023, 1, 15, 10, 0)));
        save(new Candidate(3, "Sergey Bazhukov", "Java Developer",
                LocalDateTime.of(2023, 1, 15, 10, 0)));
        save(new Candidate(4, "Sergey Bazhukov", "Java Developer",
                LocalDateTime.of(2023, 1, 15, 10, 0)));
        save(new Candidate(5, "Sergey Bazhukov", "Java Developer",
                LocalDateTime.of(2023, 1, 15, 10, 0)));
        save(new Candidate(6, "Sergey Bazhukov", "Java Developer",
                LocalDateTime.of(2023, 1, 15, 10, 0)));
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId.getAndIncrement());
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public boolean deleteById(int id) {
        return candidates.remove(id) != null;
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(candidate.getId(), (id, oldCandidate) -> new Candidate(oldCandidate.getId(), candidate.getName(),
                candidate.getDescription(), candidate.getCreationDate())) != null;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
