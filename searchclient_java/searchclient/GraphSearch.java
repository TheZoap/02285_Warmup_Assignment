package searchclient;

import java.util.HashSet;

public class GraphSearch {

    public static Action[][] search(State initialState, Frontier frontier)
    {
        boolean outputFixedSolution = false;

        if (outputFixedSolution) {
            // ------------------------------------------------------------
            // PART 1: Fixed (hand-coded) solution
            // ------------------------------------------------------------

            Action[][] level1Sol = {
                    {Action.MoveS},
                    {Action.MoveE},
                    {Action.MoveE},
                    {Action.MoveS},
                    {Action.MoveE},
                    {Action.MoveE},
                    {Action.MoveE},
                    {Action.MoveE},
                    {Action.MoveE},
                    {Action.MoveE},
                    {Action.MoveE},
                    {Action.MoveE},
                    {Action.MoveE},
                    {Action.MoveE},
                    {Action.MoveE},
                    {Action.MoveE},
                    {Action.MoveE},
                    {Action.MoveE},
                    {Action.PushSS}
            };

            Action[][] selectedSolution = level1Sol;
            return selectedSolution;

        } else{
            // ------------------------------------------------------------
            // PART 2: Breadth-First Graph Search (R&N Fig. 3.7)
            // ------------------------------------------------------------

            int iterations = 0;

            frontier.add(initialState);
            HashSet<State> expanded = new HashSet<>();

            while (true) {

                if (frontier.isEmpty()) {
                    return null; // Failure: no solution
                }

                // Print search status every 10000 generated nodes
                if (++iterations % 10000 == 0) {
                    printSearchStatus(expanded, frontier);
                }

                State state = frontier.pop();

                // Goal test
                if (state.isGoalState()) {
                    printSearchStatus(expanded, frontier);
                    return state.extractPlan();
                }

                expanded.add(state);

                // Expand state
                for (State child : state.getExpandedStates()) {
                    if (!expanded.contains(child) && !frontier.contains(child)) {
                        frontier.add(child);
                    }
                }
            }
        }
    }

    // ------------------------------------------------------------
    // Search statistics
    // ------------------------------------------------------------

    private static long startTime = System.nanoTime();

    private static void printSearchStatus(HashSet<State> expanded, Frontier frontier)
    {
        String statusTemplate =
                "#Expanded: %,8d, #Frontier: %,8d, #Generated: %,8d, Time: %3.3f s\n%s\n";

        double elapsedTime =
                (System.nanoTime() - startTime) / 1_000_000_000d;

        System.err.format(
                statusTemplate,
                expanded.size(),
                frontier.size(),
                expanded.size() + frontier.size(),
                elapsedTime,
                Memory.stringRep()
        );
    }
}
