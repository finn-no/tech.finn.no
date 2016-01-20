---
layout: post
comments: true
date: 2016-01-20 08:13:00+0100
authors: Sjur Millidahl
title: "Why Try - Java 8 lambdas and checked Exceptions"
tags:
- java
- java8
- functional
- lambda
- exceptions
- monad
- try
- success
- failure
---

<figure>
  <img src="/images/2016-01-20-why-try-java-8-functional-programming/thumbnail.png" alt="lambdas dislike checked exceptions" />
  <figcaption>Why won't this compile? (And whyyy does riskyTask throw a PrinterException?!)</figcaption>
</figure>

One does not have to use lambdas in Java 8 long before running into the obstacle of checked Exceptions. Because the PrinterException is checked, the compiler forces us to deal with it, even within a lambda:

{% highlight java %}
private void demonstrate() {
    IntStream ones = IntStream.generate(() -> 1);
    ones.map(i -> {
        try {
            return riskyTask(i);
        } catch (PrinterException e) {
            System.out.println(
                "Your printer is out of ink or laser beams!"
            );
        }
        return i;
    });
}

private int riskyTask(int a) throws PrinterException {
    return 2;
}
{% endhighlight %}

But we don't like to do this. We use lambdas to express intent in a concise and elegant fashion. The try/catch-brackets feels like noise.

For this reason, FINN.no's open source  [lambda-companion](https://github.com/finn-no/lambda-companion) project introduces a useful structure for using lambdas in a world with checked Exceptions : Try.

A Try represents a computation which might fail, and is always represented as a Success or a Failure (but never both). The concept borrows from Scala's Try, and shares several properties to other monadic functional structures :
- Future (completable success or failure)
- Optional (present or empty value)
- Either (one of two values)

FINN.no's Try is right-biased, meaning that one can map and flatMap on a Try without providing behaviour for when the Try is a Failure; the computation will simply only take place if it is a Success.

Using a Try we could refactor our riskyTask-example:

{% highlight java %}
private void demonstrate() {
    IntStream ones = IntStream.generate(() -> 1);
    Stream<Try<Integer>> tryStream = ones.mapToObj(this::riskyTask);
    tryStream.forEach(this::print);
}

private Try<Integer> riskyTask(int a) {
    return new Success<>(2);
}

private void print(Try<Integer> t) {
    Integer defaultNumber = 0;
    Integer i = t.recover(success -> success, failure -> defaultNumber);
    System.out.print(i);
}
{% endhighlight %}

This is contrived of course. And one should rarely accept a Try as a method argument. Aaand one should rarely generate an infinite stream of 2s and print them out.

{% highlight java %}
public class OrderProcessor {

    CustomerLookupService customerLookupService = 
            new CustomerLookupService();
    ProductLookupService productLookupService = 
            new ProductLookupService();
    PaymentService paymentService = 
            new PaymentService();

    public static void main(String[] args) {
        new OrderProcessor().validateOrder(14L, 21L);
    }

    private Boolean validateOrder(Long customerId, Long productId) {
        return Try.of(customerLookupService::lookup, customerId)
                .flatMap(customerFound ->
                        Try.of(productLookupService::lookup, productId))
                .flatMap(price ->
                        Try.of(paymentService::pay, customerId, price))
                .recover(Function.identity(), this::handleFailure);
    }

    private Boolean handleFailure(Throwable throwable) {
        System.out.println(
                "Could not process order because of " + 
                        throwable.getCause());
        return Boolean.FALSE;
    }
}

class CustomerLookupService {
    Boolean lookup(Long customerId) throws Exception { 
        return Boolean.TRUE; 
    }
}

class ProductLookupService {
    Long lookup(Long productId) throws Exception { 
        return 50L; 
    }
}

class PaymentService {
    Boolean pay(Long customerId, Long price) throws Exception { 
        return Boolean.TRUE;
    }
}
{% endhighlight %}

In this example we combine services to determine if a Order is valid or not. We use Try.flatMap() because this guarantees that the provided lambda will only be run if the proceeding Try is a Success. If any of the 3 service calls result in a Failure, the flatMapping call-chain will simply roll the Failure forward. The recover-call provides two lambdas: One to be handle in the case of Success, and one to be handled in the case of Failure.

Read more about Try, Either, StreamableOptional and more in FINN.no's open sourced lambda-companion project available at [github](https://github.com/finn-no/lambda-companion) and for use in your project through the central maven repository.

Happy lambda'ing, and may all your Tries be Successes!