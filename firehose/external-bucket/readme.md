# external bucket 

Stream and firehose in source account, bucket in sink

1. create bucket
2. install firehose stack
3. add bucket policy

aws kinesis put-record --stream-name cakhose2-dev-stream --data  eHh4eAo= --partition-key 1