package com.peter.solution.repository.tollpass;

import com.peter.solution.repository.InMemoryRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository class responsible for managing and accessing TollPass data in an in-memory storage.
 * <p>
 * This class extends {@link InMemoryRepository} and provides specific methods to retrieve toll pass records
 * based on plate number and date-time ranges. It helps in filtering toll passes efficiently using a stream-based
 * approach, ensuring that only the relevant toll passes are returned.
 * </p>
 * <p>
 * All toll passes are stored in memory and indexed by their respective identifiers. Methods provided allow for
 * searching, saving, and deleting toll pass entries.
 * </p>
 */
@Component
public class TollPassRepository extends InMemoryRepository<TollPass> {

    /**
     * Finds all toll passes for a given plate number within a specified date-time range.
     *
     * @param plateNumber   the plate number to search for
     * @param startDateTime the start date-time of the range (inclusive)
     * @param endDateTime   the end date-time of the range (inclusive)
     * @return a list of matching toll passes, or an empty list if no matches found
     */
    public List<TollPass> findAllByPlateNumberAndDateTime(String plateNumber,
                                                          LocalDateTime startDateTime,
                                                          LocalDateTime endDateTime) {
        return findAll().stream()
                .filter(tollPass -> isMatchingTollPass(tollPass, plateNumber, startDateTime, endDateTime))
                .collect(Collectors.toList());
    }

    /**
     * Helper method to check if a toll pass matches the given plate number and date-time range.
     *
     * @param tollPass      the toll pass to check
     * @param plateNumber   the plate number to match
     * @param startDateTime the start date-time range
     * @param endDateTime   the end date-time range
     * @return true if the toll pass matches the criteria, false otherwise
     */
    private boolean isMatchingTollPass(TollPass tollPass, String plateNumber,
                                       LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return plateNumber.equals(tollPass.getPlateNumber()) &&
                isWithinDateTimeRange(tollPass.getPassDateTime(), startDateTime, endDateTime);
    }

    /**
     * Helper method to check if a given date-time is within the specified range.
     *
     * @param dateTime      the date-time to check
     * @param startDateTime the start date-time range
     * @param endDateTime   the end date-time range
     * @return true if the date-time is within the range (inclusive)
     */
    private boolean isWithinDateTimeRange(LocalDateTime dateTime,
                                          LocalDateTime startDateTime,
                                          LocalDateTime endDateTime) {
        return !dateTime.isBefore(startDateTime) && !dateTime.isAfter(endDateTime);
    }
}