package com.peter.solution.repository.dates;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;

/**
 * Represents an exempted date for toll calculation purposes.
 * This record captures information about dates that are exempted from toll charges
 * based on different criteria: day of the week, specific holiday date, or month.
 */
@Data
@NoArgsConstructor
public final class ExemptedDate {

    private Long id;
    private Type type;
    private DayOfWeek dayOfWeek;
    private LocalDate holidayDate;
    private Month month;

    public ExemptedDate(Long id, Type type, DayOfWeek dayOfWeek, LocalDate holidayDate, Month month) {
        this.id = id;
        this.type = type;
        this.dayOfWeek = dayOfWeek;
        this.holidayDate = holidayDate;
        this.month = month;
    }

    /**
     * Creates an ExemptedDate for a specific day of the week.
     *
     * @param id  the unique identifier for the exempted date
     * @param day the day of the week
     * @return a new ExemptedDate for the specified day of the week
     */
    public static ExemptedDate ofDayOfWeek(Long id, DayOfWeek day) {
        return new ExemptedDate(id, Type.DAY_OF_WEEK, day, null, null);
    }

    /**
     * Creates an ExemptedDate for a specific holiday date.
     *
     * @param id   the unique identifier for the exempted date
     * @param date the holiday date
     * @return a new ExemptedDate for the specified holiday date
     */
    public static ExemptedDate ofHolidayDate(Long id, LocalDate date) {
        return new ExemptedDate(id, Type.HOLIDAY_DATE, null, date, null);
    }

    /**
     * Creates an ExemptedDate for a specific month.
     *
     * @param id    the unique identifier for the exempted date
     * @param month the month
     * @return a new ExemptedDate for the specified month
     */
    public static ExemptedDate ofMonth(Long id, Month month) {
        return new ExemptedDate(id, Type.MONTH, null, null, month);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExemptedDate that = (ExemptedDate) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Enum representing the type of exemption applied to the date.
     */
    public enum Type {DAY_OF_WEEK, HOLIDAY_DATE, MONTH}
}