---
layout: post
comments: true
date: 2015-09-22 15:58:41+0200
authors: Henning Spjelkavik
title: "The Process of Using Kafka"
description: "The tale of how we tried all permutations of settings when we started using Kafka"
tags:
- kafka
- tradeoffs
---

*Use and misuse*

Actually, the title is a bit too broad. We'll look at how different number of acks, async and timeouts will affect the reliability of your messages.

## Abstract / TL;DR ##
Kafka can be used in many ways, and there are also many options that you need to understand and decide upon before you start using Kafka. As always, the combination of options you choose is a compromise - a trade off - and in my opinion, explicit trade offs are much better than accidential ones.

We have seen many ways of using Kafka internally, and to demonstrate the properties of some of the different permutations, we've run a small test to demonstrate how it works.

Before using Kafka, you need to understand it! Read the [documentation](http://kafka.apache.org/documentation.html), the [confluent.io blog](http://www.confluent.io/blog), and [Martin Kleppman's blog](https://martin.kleppmann.com/2015/05/27/logs-for-data-infrastructure.html).

## Configuration Details ##

For a topic, you'll have to choose a replication factor and number of partitions. In this small exercise will fix the replication factor at 3, we'll split each topic in 4 partitions.

You can either produce to a topic synchronously or asynchronously. In the first case, the message is sent on the same thread as your producer code. In the async case, the kafka producer library will put your request in an internal queue, and unless that queue is full, you'll get control back to your main thread immediately.

In the synchronous case you have to choose how many servers need to acknowledge the message before your thread can continue. The fastest case is to not wait for any acknowledgement. That means, your thread continues immediately after the message is sent on the network to leader. If the network goes down - your message is lost. For a higher level of reliability you can wait for acknowledgement from the partition leader, and finally, you can wait for acknowledgement from all in-sync-replicas.

The main trade off is thus between reliability - the possibility of losing data - and (you guessed it) speed. That is a business decision; is it acceptable to lose some data?


These simple demonstrations were run on a laptop connected to our dev-cluster, consisting of 3 brokers. The response times we got are as expected - i.e matches the intuitive expectation, and clearly demonstrates the trade offs.

The code connects to Kafka, does timing of the sending of 8 events, and then sends a big chunk of events. The various combinations of options in this exercise demonstrates actual patterns of code we have observed. Some of the combinations are NON-SAFE. Beware!

## Timing information ##

For various combinations of producer type (sync/async), ```request.required.acks``` and ways of exiting the program, we've run the small test program included at the end of this post.

*Run 1.1 - synchronous producer, wait for all in sync replicas to acknowledge the write. This is the most secure way of producing messages.*

    -----------------------------------------
    ms     %     Task name
    -----------------------------------------
    00012  000%  send 1 kafka event - 0
    00009  000%  send 1 kafka event - 1
    00010  000%  send 1 kafka event - 2
    00008  000%  send 1 kafka event - 3
    00010  000%  send 1 kafka event - 4
    00008  000%  send 1 kafka event - 5
    00009  000%  send 1 kafka event - 6
    00009  000%  send 1 kafka event - 7
    82393  100%  send 10000 kafka events
    00009  000%  closing


*Run 1.2 - synchronous producer, wait for the leader to acknowledge the write. If the leader crashes after acknowledging, but before any replica receives the data, the message will be lost.*

    -----------------------------------------
    ms     %     Task name
    -----------------------------------------
    00006  000%  send 1 kafka event - 0
    00005  000%  send 1 kafka event - 1
    00007  000%  send 1 kafka event - 2
    00007  000%  send 1 kafka event - 3
    00009  000%  send 1 kafka event - 4
    00014  000%  send 1 kafka event - 5
    00009  000%  send 1 kafka event - 6
    00007  000%  send 1 kafka event - 7
    39344  100%  send 10000 kafka events
    00008  000%  closing

*Run 1.3 - synchronous producer, but do not wait for acknowledgement.*

    -----------------------------------------
    ms     %     Task name
    -----------------------------------------
    00001  000%  send 1 kafka event - 0
    00001  000%  send 1 kafka event - 1
    00000  000%  send 1 kafka event - 2
    00001  000%  send 1 kafka event - 3
    00002  000%  send 1 kafka event - 4
    00001  000%  send 1 kafka event - 5
    00000  000%  send 1 kafka event - 6
    00001  000%  send 1 kafka event - 7
    02093  099%  send 10000 kafka events
    00007  000%  closing


Let's switch to the asynchronous producer. The producing thread will continue, but we'll need to be aware of the need to drain any queues before exiting. Messages will be batched together within a 5 s window, so just exiting can be a serious problem if you care about your data.

*Run 2.1 - Async, blocking on producer.close() before exiting:*

    -----------------------------------------
    ms     %     Task name
    -----------------------------------------
    00001  000%  send 1 kafka event - 0
    00000  000%  send 1 kafka event - 1
    00000  000%  send 1 kafka event - 2
    00000  000%  send 1 kafka event - 3
    00000  000%  send 1 kafka event - 4
    00000  000%  send 1 kafka event - 5
    00000  000%  send 1 kafka event - 6
    00000  000%  send 1 kafka event - 7
    00065  005%  send 10000 kafka events
    01302  095%  closing

100% of all messages were delivered.


*Run 2.2 - Async, immediate System.exit()*

    -----------------------------------------
    ms     %     Task name
    -----------------------------------------
    00000  000%  send 1 kafka event - 0
    00000  000%  send 1 kafka event - 1
    00000  000%  send 1 kafka event - 2
    00000  000%  send 1 kafka event - 3
    00000  000%  send 1 kafka event - 4
    00000  000%  send 1 kafka event - 5
    00000  000%  send 1 kafka event - 6
    00000  000%  send 1 kafka event - 7
    00220  100%  send 10000 kafka events

_0% of the messages were delivered!_


*Run 2.3 - Async, main method returns normally, without calling close()*

    -----------------------------------------
    ms     %     Task name
    -----------------------------------------
    00000  000%  send 1 kafka event - 0
    00000  000%  send 1 kafka event - 1
    00000  000%  send 1 kafka event - 2
    00001  000%  send 1 kafka event - 3
    00000  000%  send 1 kafka event - 4
    00000  000%  send 1 kafka event - 5
    00000  000%  send 1 kafka event - 6
    00000  000%  send 1 kafka event - 7
    00209  100%  send 10000 kafka events

The process does not terminate, as there is a non-daemon producer thread left. The default value of ```queue.buffering.max.ms``` is 5000 ms, and ``` queue.buffering.max.messages ``` is 10.000 messages. This means that the queue will be sent after 10.000 messages, or after waiting at most 5000 ms.


*Run 2.4 - Async, sleep 8 seconds after sending, then System.exit()*

    -----------------------------------------
    ms     %     Task name
    -----------------------------------------
    00000  000%  send 1 kafka event - 0
    00000  000%  send 1 kafka event - 1
    00000  000%  send 1 kafka event - 2
    00000  000%  send 1 kafka event - 3
    00000  000%  send 1 kafka event - 4
    00000  000%  send 1 kafka event - 5
    00001  000%  send 1 kafka event - 6
    00000  000%  send 1 kafka event - 7
    00171  002%  send 10000 kafka events
    08000  098%  sleeping

100% of the messages were delivered

As 8 s is more than 5000 ms, the messages will be delivered.


*Run 2.5 - Async, sleep 4 seconds after sending, then System.exit()*

    -----------------------------------------
    ms     %     Task name
    -----------------------------------------
    00000  000%  send 1 kafka event - 0
    00000  000%  send 1 kafka event - 1
    00001  000%  send 1 kafka event - 2
    00000  000%  send 1 kafka event - 3
    00000  000%  send 1 kafka event - 4
    00000  000%  send 1 kafka event - 5
    00000  000%  send 1 kafka event - 6
    00000  000%  send 1 kafka event - 7
    00143  003%  send 10000 kafka events
    04001  097%  sleeping

99.89% (9989/10000) of the messages were delivered.

As 4 s is less than 5000 ms, the first 10.000 messages will be delivered, and the rest will be left in the queue. As we've sent more than 10.000 message during the session, some messages were lost.


## Summary ##

How you configure your Kafka producer will determine whether you have a super-fast-and-lose-everything messaging system, or something slower but reliable. YMMV. 

Before using Kafka, you need to understand it! Read the [documentation](http://kafka.apache.org/documentation.html), the [confluent.io blog](http://www.confluent.io/blog), and [Martin Kleppman's blog](https://martin.kleppmann.com/2015/05/27/logs-for-data-infrastructure.html). [Hermes](http://hermes-pubsub.readthedocs.org/en/latest/) is a message broker built on top of Kafka.


| Milliseconds / event | What |
| --- | --- |
| 8.2 ms | Sync, wait for all in sync replicas |
| 3.9 ms | Sync, wait for leader |
| 2.0 ms | Sync, don't wait for ack |
| 0.13 ms | Async, wait for the queue to finish |
| 0.02 ms | Async, exit without waiting (aka /dev/null for the last messages)|


## The sample code ##

{% highlight java %}
package no.finntech.demo.ecclient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StopWatch;

public final class CleanBulkSenderDemo {
    interface Acks {
        int ALL_ISR = -1;
        int LEADER = 1;
        int NEVER_WAIT = 0;
    }

    public static void main(String[] args) throws InterruptedException, UnknownHostException {

        boolean featureAsync = true;
        int featureSleep = 0;
        boolean featureClose = false;
        boolean featureSystemExit = true;

        String brokers = "super-dev1.finntech.no:1337,super-dev2.finntech.no:1337,super-dev3.finntech.no:1337" ;

        final Properties props = new Properties();
        props.put("metadata.broker.list", brokers);
        props.put("producer.type", featureAsync ? "async" : "sync");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("request.required.acks", "" + Acks.ALL_ISR);
        props.put("client.id", InetAddress.getLocalHost().getHostName());
        Producer producer = new Producer(new ProducerConfig(props) ) ;

        // connect, warm up
        sendEvent(producer, "42", "42_0");
        sendEvent(producer, "42", "42_1");

        System.out.println("Starting");

        StopWatch sw = new StopWatch("kafka");
        for (int i = 0; i <8; i++) {
            sw.start("send 1 kafka event - " + i);
            sendEvent(producer, "42", "42_" + i);
            sw.stop();
        }

        int n = 10000;
        sw.start("send " + n + " kafka events");
        for (int i = 0; i <n; i++) {
            sendEvent(producer, "42", "x2_" + i);
        }
        sw.stop();

        sw.start("closing");
        if (featureClose) producer.close();
        if (featureSleep>0) Thread.sleep(featureSleep);
        sw.stop();

        System.out.println(sw.prettyPrint());
        if (featureSystemExit) { System.exit(0); }
    }

    private static void sendEvent(Producer producer, String s, String s1) {
        producer.send(new KeyedMessage<String, String>("Test.ignore.performance", s, s1+ " - " + new Date()));
    }


}

{% endhighlight %}

