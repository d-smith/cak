Prior to starting jupyter notebook in this directory, set AWS_DEFAULT_REGION
and AWS_DEFAULT_PROFILE.

Note the notebook hardcodes the region to us-west-2 - change this before running if needed or update the code the configure this via env var.

For lambda output...  same account

* create an output function via the kinesis data analytics lambda blueprint (search for kinesis)
* delete the existing destination
* add the output as the destination

This snippet helps provide better log info...

```
console.log(JSON.stringify(event));
console.log("record zero data");

let data = event.records[0].data;
let buff = new Buffer(data, 'base64');
let text = buff.toString('ascii');
console.log(text);
```


