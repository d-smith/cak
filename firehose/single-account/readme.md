

```
aws kinesis put-record --stream-name cakhose-dev-stream --data  eHh4eAo= --partition-key 1
```

$ aws s3 cp s3://cakhose-dev-snshose-destination/pubrecord/year=2021/month=07/day=01/cakhose-dev-firehose-delivery-stream-1-2021-07-01-21-55-29-fe74eb8c-39a7-442d-86ab-a22d5fad4ab4 ./foo
download: s3://cakhose-dev-snshose-destination/pubrecord/year=2021/month=07/day=01/cakhose-dev-firehose-delivery-stream-1-2021-07-01-21-55-29-fe74eb8c-39a7-442d-86ab-a22d5fad4ab4 to ./foo
$ cat foo
xxxx
xxxx
xxxx
xxxx
xxxx