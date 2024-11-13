package com.peter.solution.repository.dates;

import com.peter.solution.repository.Repository;
import com.peter.solution.repository.dates.ExemptedDate.Type;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In-memory repository for storing and managing exempted dates.
 * This class handles the storage and retrieval of exempted dates, which can be categorized by type
 * (e.g., day of the week, holiday date, month). It also provides functionality for saving and deleting
 * exempted date entries by their unique identifier.
 */
@Component
public class ExemptedDateRepository implements Repository<ExemptedDate, Long> {
    private final Map<Type, Set<ExemptedDate>> dateEntries = new EnumMap<>(Type.class);
    private final AtomicLong idGenerator = new AtomicLong(1);

    /**
     * Constructs a new repository for storing exempted dates. Initializes categories for day of the week,
     * holiday date, and month.
     */
    public ExemptedDateRepository() {
        dateEntries.put(Type.DAY_OF_WEEK, new HashSet<>());
        dateEntries.put(Type.HOLIDAY_DATE, new HashSet<>());
        dateEntries.put(Type.MONTH, new HashSet<>());

        dateEntries.get(Type.DAY_OF_WEEK).add(ExemptedDate.ofDayOfWeek(generateId(), DayOfWeek.SATURDAY));
        dateEntries.get(Type.DAY_OF_WEEK).add(ExemptedDate.ofDayOfWeek(generateId(), DayOfWeek.SUNDAY));
        dateEntries.get(Type.MONTH).add(ExemptedDate.ofMonth(generateId(), Month.JULY));
    }

    /**
     * Retrieves all exempted dates from the repository.
     *
     * @return a list of all exempted dates
     */
    @Override
    public List<ExemptedDate> findAll() {
        return dateEntries.values().stream()
                .flatMap(Collection::stream)
                .toList();
    }

    /**
     * Saves a new exempted date entry to the repository, generating a unique ID for it.
     *
     * @param entry the exempted date entry to save
     * @return the saved exempted date entry with the generated ID
     */
    @Override
    public ExemptedDate save(ExemptedDate entry) {
        ExemptedDate entryWithId = new ExemptedDate(generateId(),
                entry.getType(),
                entry.getDayOfWeek(),
                entry.getHolidayDate(),
                entry.getMonth());
        dateEntries.get(entry.getType()).add(entryWithId);
        return entryWithId;
    }

    /**
     * Deletes an exempted date entry by its unique identifier.
     *
     * @param id the unique identifier of the exempted date entry to delete
     */
    @Override
    public void deleteById(Long id) {
        for (Collection<ExemptedDate> entries : dateEntries.values()) {
            if (entries.removeIf(entry -> id.equals(entry.getId()))) break;
        }
    }

    /**
     * Generates a unique identifier for an exempted date entry.
     *
     * @return a new unique ID
     */
    private Long generateId() {
        return idGenerator.getAndIncrement();
    }
}