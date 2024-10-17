package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";


    public static final String MESSAGE_USAGE = COMMAND_WORD
                + ": Deletes the people identified by the index numbers used in the displayed person list.\n"
                + "Parameters: INDEXES (must be a positive integer)\n"
                + "Example: " + COMMAND_WORD + " 1, 2";

    public static final String MESSAGE_DELETE_PEOPLE_SUCCESS = "Deleted People: %s";
    private final Index[] targetIndexes;


    /**
     * Takes in the array of indexes to be used for deletion.
     *
     * @param targetIndexes array of Index.
     */
    public DeleteCommand(Index[] targetIndexes) {
        this.targetIndexes = targetIndexes;

    }

    /**
     * Executes the Delete command with multiple >= 1 index.
     *
     * @param model {@code Model} which the command should operate on.
     * @return Message to user that deletions were successful.
     * @throws CommandException If any of the index given falls out of range.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        String s = "";
        Person[] personsToDelete = new Person[targetIndexes.length];
        for (int i = targetIndexes.length - 1; i >= 0; i--) {
            if (targetIndexes[i].getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            personsToDelete[targetIndexes.length - i - 1] = lastShownList.get(targetIndexes[i].getZeroBased());
        }

        for (int j = 0; j < personsToDelete.length; j++) {
            model.deletePerson(personsToDelete[j]);
            s += Messages.format(personsToDelete[j]);
            s += "\n";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PEOPLE_SUCCESS, s));
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return Arrays.equals(targetIndexes, otherDeleteCommand.targetIndexes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", Arrays.toString(targetIndexes))
                .toString();
    }




}
