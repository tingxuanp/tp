package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FORCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING;

import java.util.stream.Stream;

import seedu.address.logic.commands.DeleteWeddingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.Wedding;

/**
 * Parses input arguments and creates a new DeleteWeddingCommand object.
 */
public class DeleteWeddingCommandParser implements Parser<DeleteWeddingCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteWeddingCommand
     * and returns a DeleteWeddingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteWeddingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_WEDDING, PREFIX_FORCE);

        if (!arePrefixesPresent(argMultimap, PREFIX_WEDDING)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteWeddingCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_WEDDING);
        Wedding wedding = ParserUtil.parseWedding(argMultimap.getValue(PREFIX_WEDDING).get());
        if (arePrefixesPresent(argMultimap, PREFIX_FORCE)) {
            return new DeleteWeddingCommand(wedding, true);
        } else {
            return new DeleteWeddingCommand(wedding);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
