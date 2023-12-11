package dev.axolotl.homwork;

import java.util.function.Consumer;


// Please ignore this file, I wanted to solve the mantra problem, but in the most annoying way possible.

public class MantraButAnnoying {

    public static abstract class AbstractMantra {
        protected abstract void printMantra();

        public void execute() {
            printMantra();
        }
    }

    public static class Mantra extends AbstractMantra {
        private static final String MANTRA = """
                There's one thing every coder must understand:
                The System.out.println command.

                """;

        private final Consumer<String> printer = System.out::print;

        @Override
        protected void printMantra() {
            printer.accept(MANTRA);
        }
    }

    public interface MantraFactory {
        AbstractMantra createMantra();
    }

    public static class DefaultMantraFactory implements MantraFactory {
        @Override
        public AbstractMantra createMantra() {
            return new Mantra();
        }
    }

    public static void main(String[] args) {
        MantraFactory factory = new DefaultMantraFactory();
        AbstractMantra mantra = factory.createMantra();
        mantra.execute();
        mantra.execute();
    }
}
