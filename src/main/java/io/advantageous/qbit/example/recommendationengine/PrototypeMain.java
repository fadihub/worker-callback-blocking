package io.advantageous.qbit.example.recommendationengine;

import io.advantageous.boon.core.Sys;
import io.advantageous.qbit.service.ServiceQueue;

import java.util.List;

import static io.advantageous.boon.Lists.list;
import static io.advantageous.qbit.service.ServiceBuilder.serviceBuilder;
import static io.advantageous.qbit.service.ServiceProxyUtils.flushServiceProxy;
import static java.lang.System.out;

/**
 * Created by rhightower on 2/20/15.
 */
public class PrototypeMain {

    public static void main(String... args) {


        /* Create the service. */
        RecommendationService recommendationServiceImpl =
                new RecommendationService();

        /* Wrap the service in a queue. */
        ServiceQueue recommendationServiceQueue = serviceBuilder()
                .setServiceObject(recommendationServiceImpl)
                .build().start().startCallBackHandler();

        /* Create a proxy interface for the service. */
        RecommendationServiceClient recommendationServiceClient =
                recommendationServiceQueue.createProxy(RecommendationServiceClient.class);


        /* Call the service with the proxy. */
        recommendationServiceClient.recommend(out::println, "Rick");

        /* Flush the call. This can be automated, but is a core concept. */
        flushServiceProxy(recommendationServiceClient);
        Sys.sleep(1000);



        /* Lambdas gone wild. */

        List<String> userNames = list("Bob", "Joe", "Scott", "William");

        userNames.forEach( userName->
                        recommendationServiceClient.recommend(recommendations -> {
                            System.out.println("Recommendations for: " + userName);
                            recommendations.forEach(recommendation->
                                    System.out.println("\t" + recommendation));
                        }, userName)
        );



        flushServiceProxy(recommendationServiceClient);
        Sys.sleep(1000);

    }
}
