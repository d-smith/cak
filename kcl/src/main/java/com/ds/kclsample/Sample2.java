package com.ds.kclsample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.awssdk.services.kinesis.model.DescribeStreamRequest;
import software.amazon.awssdk.services.kinesis.model.DescribeStreamResponse;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;
import software.amazon.awssdk.services.sts.model.AssumeRoleResponse;
import software.amazon.awssdk.services.sts.model.Credentials;
import software.amazon.kinesis.common.KinesisClientUtil;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class Sample2 {
    private static final Logger log = LoggerFactory.getLogger(Sample2.class);

    static final String roleArnTemplate = "arn:aws:iam::%s:role/service-role/KA-Source-Stream-Role";

    public static void main(String... args) throws Exception {
        final String streamName = "ExampleInputStream";
        final String region = "us-east-1";

        log.info("yep");
        String roleArn = String.format(roleArnTemplate, System.getenv("PRODUCER_ACCOUNT_NO"));
        log.info("roleArn is {}", roleArn);

        Credentials myCreds = getCredentialsForRole(Region.US_EAST_1, roleArn);
        System.setProperty("aws.accessKeyId", myCreds.accessKeyId());
        System.setProperty("aws.secretAccessKey", myCreds.secretAccessKey());
        System.setProperty("aws.sessionToken", myCreds.sessionToken());

        KinesisAsyncClient kinesisClient = KinesisClientUtil.createKinesisAsyncClient(
                KinesisAsyncClient
                        .builder()
                        .credentialsProvider(SystemPropertyCredentialsProvider.create())
                        .region(Region.US_EAST_1));

        DescribeStreamResponse dsr =kinesisClient.describeStream(
                DescribeStreamRequest.builder().streamName("ExampleInputStream").build()
        ).get();

        log.info(dsr.toString());

        new SampleProcessor("ca", streamName, region, kinesisClient).run();

    }

    private static Credentials getCredentialsForRole(Region region, String roleArn) {
        StsClient stsClient = StsClient.builder()
                .region(region)
                .credentialsProvider(ProfileCredentialsProvider.create("ca"))
                .build();

        AssumeRoleRequest roleRequest = AssumeRoleRequest.builder()
                .roleArn(roleArn)
                .roleSessionName("sessionname")
                .build();

        AssumeRoleResponse roleResponse = stsClient.assumeRole(roleRequest);
        Credentials myCreds = roleResponse.credentials();

        // Display the time when the temp creds expire
        Instant exTime = myCreds.expiration();
        String tokenInfo = myCreds.sessionToken();

        // Convert the Instant to readable date
        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                        .withLocale( Locale.US)
                        .withZone( ZoneId.systemDefault() );

        formatter.format( exTime );
        log.info("The token "+tokenInfo + "  expires on " + exTime );

        return myCreds;
    }
}
