package io.advantageous.qbit.example.recommendationengine;




import io.advantageous.boon.core.Lists;
import io.advantageous.boon.primitive.SimpleLRUCache;
import java.util.List;

public class RecommendationService {


    private final SimpleLRUCache<String, User> users =
            new SimpleLRUCache<>(10_000);

    public List<Recommendation> recommend(final String userName) {
        System.out.println("recommend called");
        User user = users.get(userName);
        if (user == null) {
            user = loadUser(userName);
        }
        return runRulesEngineAgainstUser(user);
    }

    private User loadUser(String userName) {
        return new User("bob"); //stubbed out... next example will use UserService
    }

    private List<Recommendation> runRulesEngineAgainstUser(final User user) {

        return Lists.list(new Recommendation("Take a walk"), new Recommendation("Read a book"),
                new Recommendation("Love more, complain less"));
    }


}