You will build a spring boot message simulator.

This service will deploy into AWS EKS.

It will expose a REST API to allow users to configure the simulator.

It will also expose a REST API to allow users to start and stop the simulator.

It will also expose a REST API to allow users to get the status of the simulator.

It will also expose a REST API to allow users to get the metrics of the simulator.

It will also expose a REST API to allow users to get the logs of the simulator.

It will also expose a REST API to allow users to get the configuration of the simulator.

It will also expose a REST API to allow users to get the configuration of the simulator.

Features:
The simulator must enable XML/SOAP Message requests and Responses
The simulator must support JMS Queue and Topic style message request and response using XML and JSON
The simulator must support JSON and AVRO Schemas for request and responses.
The simulator must support HTTP and HTTPS for request and responses.
The responses must be configurable based on the input content of the message.
The simulator must support persistence to a file log and database.

Logging and Audit
The Simulator will enable publishing of logs to AWS Cloudwatch
The Simulator will support OpenTelemetry for metrics and tracing.

User Interface
As well as an API there will be a simple web interface to allow users to configure the simulator.

The User interface will also enable viewing request and responses in the log and database. These will be presented as a side-by-side view of the request and response. The view will allow filtering by date, time, message type, and message content.  
