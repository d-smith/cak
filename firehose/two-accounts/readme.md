
# two-account data stream to s3 via firehose

## approach 1

Data stream in producer account, firehose and bucket in consumer acccount

This is the logical extension of the single account version, with a role and stream set up in the producer account, and a delivery stream referencing the remove stream arn and role in the consumer account.

It is a bit of an indirect trust relationship... we allow firehose to assume the role in the producer account, in the stream account the firehose delivery policy allows 

However... the first try at this arrangement fails:

 An error occurred: FirehoseDeliveryStream - Cross-account pass role is not allowed.

 ## approach 2

 data stream and firehose in producer account, s3 bucket in consumer account