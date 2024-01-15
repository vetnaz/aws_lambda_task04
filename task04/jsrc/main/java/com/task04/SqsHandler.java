package com.task04;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.syndicate.deployment.annotations.events.SqsTriggerEventSource;
import com.syndicate.deployment.annotations.lambda.LambdaHandler;
import com.syndicate.deployment.annotations.resources.DependsOn;
import com.syndicate.deployment.model.DeploymentRuntime;
import com.syndicate.deployment.model.ResourceType;

import java.util.HashMap;
import java.util.Map;

@LambdaHandler(lambdaName = "sqs_handler",
        roleName = "sqs_handler-role",
        isPublishVersion = true,
        runtime = DeploymentRuntime.JAVA8
)
@SqsTriggerEventSource(targetQueue = "async_queue", batchSize = 10)
@DependsOn(name = "async_queue", resourceType = ResourceType.SQS_QUEUE)
public class SqsHandler implements RequestHandler<Object, String> {

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
