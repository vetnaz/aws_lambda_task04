package com.task04;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.syndicate.deployment.annotations.events.SnsEventSource;
import com.syndicate.deployment.annotations.lambda.LambdaHandler;
import com.syndicate.deployment.annotations.resources.DependsOn;
import com.syndicate.deployment.model.ResourceType;

@LambdaHandler(lambdaName = "sns_handler",
        roleName = "sns_handler-role"
)
@SnsEventSource(targetTopic = "lambda_topic")
@DependsOn(name = "lambda_topic", resourceType = ResourceType.SNS_TOPIC)
public class SnsHandler implements RequestHandler<Object, String> {

    public String handleRequest(Object request, Context context) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        LambdaLogger logger = context.getLogger();
        String response = new String("SUCCESS");
        // log execution details
        logger.log("ENVIRONMENT VARIABLES: " + gson.toJson(System.getenv()));
        logger.log("CONTEXT: " + gson.toJson(context));
        // process event
        logger.log("EVENT: " + gson.toJson(request));
        return response;
    }
}
