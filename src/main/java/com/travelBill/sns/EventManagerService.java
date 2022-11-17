package com.travelBill.sns;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.travelBill.config.ApplicationConfiguration;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class EventManagerService {
    static AWSCredentials awsCredentials;
    static AWSCredentialsProvider awsCredentialsProvider;
    static AmazonSNS snsClient;
    static String topicArn;

    public EventManagerService(ApplicationConfiguration applicationConfiguration) {
        awsCredentials = new BasicAWSCredentials(
                applicationConfiguration.getAwsAccessKey(), applicationConfiguration.getAwsSecretAccessKey());
        awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
        snsClient = AmazonSNSClientBuilder
                .standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(Regions.EU_NORTH_1)
                .build();
        topicArn = applicationConfiguration.getSnsTopic();
    }

    public void sendMessage(String eventName, String payload) {
        PublishRequest publishRequest = new PublishRequest(topicArn, payload);
        publishRequest.addMessageAttributesEntry("event", new MessageAttributeValue().withDataType("String").withStringValue(eventName));
        snsClient.publish(publishRequest);
    }

    public void sendBillCreatedEvent(String eventName, BillCreatedEvent billCreatedEvent) {
        JSONObject json = new JSONObject(billCreatedEvent);
        this.sendMessage(eventName, json.toString());
    }
}
