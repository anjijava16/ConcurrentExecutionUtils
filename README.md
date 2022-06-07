# ConcurrentExecutionUtils
Concurrency is the ability to do more than one thing at the same time.
1. Thread and Runnable
2. Executor Service & Thread Pool
3. Callable and Future
4. Thread Synchronization
5. Locks and Atomic Variables


# Reference Links
1. https://www.netjstech.com/2016/04/callable-and-future-in-java-concurrency.html
2. https://www.callicoder.com/java-concurrency-multithreading-basics/

# Response codes sequenclly vs Concurrent execution 

## Sequence call

1. API1 Response =>{"id":1,"firstName":"Roger","lastName":"Federer","email":"roger.federer@yomail.com"}
2. API2 Response =>{"id":2,"firstName":"Rafael","lastName":"Nadal","email":"rafael.nadal@yomail.com"}
3. API3 Response =>{"id":3,"firstName":"John","lastName":"Mcenroe","email":"john.mcenroe@yomail.com"}
### Sequence call  response time = 578 millis

## Future 

1. API1 Response =>{"id":1,"firstName":"Roger","lastName":"Federer","email":"roger.federer@yomail.com"}
2. API2 Response =>{"id":2,"firstName":"Rafael","lastName":"Nadal","email":"rafael.nadal@yomail.com"}
3. API3 Response =>{"id":2,"firstName":"Rafael","lastName":"Nadal","email":"rafael.nadal@yomail.com"}
### Future  response time = 32 millis

## Callable Executor No Lambda 

1. API1 Response => {"id":2,"firstName":"Rafael","lastName":"Nadal","email":"rafael.nadal@yomail.com"}
2. API2 Response => {"id":1,"firstName":"Roger","lastName":"Federer","email":"roger.federer@yomail.com"}
3. API3 Response => {"id":3,"firstName":"John","lastName":"Mcenroe","email":"john.mcenroe@yomail.com"}
### Callable ExecutorServiceNoLambda  response time = 43 millis

## Callable Executor with Lambda 

1. API1 Response => {"id":3,"firstName":"John","lastName":"Mcenroe","email":"john.mcenroe@yomail.com"}
2. API2 Response => {"id":1,"firstName":"Roger","lastName":"Federer","email":"roger.federer@yomail.com"}
3. API3 Response => {"id":2,"firstName":"Rafael","lastName":"Nadal","email":"rafael.nadal@yomail.com"}
### Callable Lambda   response time = 13 millis

