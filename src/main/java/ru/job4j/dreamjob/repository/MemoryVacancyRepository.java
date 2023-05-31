package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Vacancy;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
public class MemoryVacancyRepository implements VacancyRepository {
    private AtomicInteger nextId = new AtomicInteger();
    private final Map<Integer, Vacancy> vacancies = new HashMap<>();

    private MemoryVacancyRepository() {
        save(new Vacancy(1, "Intern Java Developer", "Java Developer",
                LocalDateTime.of(2023, 1, 15, 10, 0), true, 1));
        save(new Vacancy(2, "Junior Java Developer", "Java Developer",
                LocalDateTime.of(2023, 1, 15, 10, 0), true, 2));
        save(new Vacancy(3, "Junior+ Java Developer", "Java Developer",
                LocalDateTime.of(2023, 1, 15, 10, 0), true, 3));
        save(new Vacancy(4, "Middle Java Developer", "Java Developer",
                LocalDateTime.of(2023, 1, 15, 10, 0), true, 1));
        save(new Vacancy(5, "Middle+ Java Developer", "Java Developer",
                LocalDateTime.of(2023, 1, 15, 10, 0), true, 1));
        save(new Vacancy(6, "Senior Java Developer", "Java Developer",
                LocalDateTime.of(2023, 1, 15, 10, 0), true, 1));
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId.getAndIncrement());
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public boolean deleteById(int id) {
        return vacancies.remove(id) != null;
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(vacancy.getId(), (id, oldVacancy) -> new Vacancy(oldVacancy.getId(), vacancy.getTitle(),
                vacancy.getDescription(), vacancy.getCreationDate(),  vacancy.getVisible(), vacancy.getCityId())) != null;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancies.values();
    }
}
