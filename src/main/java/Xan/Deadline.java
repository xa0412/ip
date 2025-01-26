package Xan;

import java.time.LocalDate;

public class Deadline extends Task {

    public Deadline(String description, LocalDate date) {
        super(description, date);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString();
    }
}
