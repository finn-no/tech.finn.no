---
layout: post
comments: true

date: 2017-09-08 15:56:49+0200
authors:
- Joakim Rishaug
- Simen Eide
title: "Deep NLP-based Recommenders at Finn.no"
tags:
- NLP
- CNN
- recommendations
---

#Deep NLP-based Recommenders at Finn.no
During a hackathon at FINN.no, we figured we wanted to learn more about deep NLP-models. FINN.no has a large database with ads of people trying to sell stuff (around 1 million active ads at any time), and they are categorized into a category tree with three or four layers. For example, full suspension bikes can be found under “Sport and outdoor activities” / “Bike sport” / “Full suspension bikes”.

In our daily jobs we are working on recommendations. There, we already have a content based (tf-idf) recommender build on Solr’s More Like This. It seems to work well in areas where our collaborative filtering approaches does not. Would it be possible to build a deep learning NLP-model of similar performance?

To achieve a measure of similarity, building a classifier of the previously mentioned categories seemed like a good choice, since we already had a lot of pre-existing data. The NLP team at Schibsted had already tokenized around six million ads as well as trained a word2vec model for us - we were ready to roll!

Some preprocessing still had to be done. We ran through all ads, concatenated the title and description strings, and after a quick look at the data took the first 15 words of each ad.

<figure>
   <img class="center-block" src="/images/2017-09-08-NLP-based-recommenders-at-finn/model architecture proposed by the paper.jpg" alt="alt" title="Model architecture proposed by the paper" />
   <figcaption style="text-align:center; font-style:italic;">Model architecture proposed by the paper</figcaption>
</figure>


Our initial experiments were done with a simple [“Bag of words” model included in the Keras repository](https://github.com/fchollet/keras/blob/2.0.3/examples/reuters_mlp.py), but we promptly switched over to [“Convolutional Neural Networks for Sentence Classification” based architecture](https://arxiv.org/pdf/1408.5882.pdf) after hearing about it from our colleague, Tobias. By looking at the first 15 words of the ad, and using 200 dimensional embeddings for each word, our input is transformed into a 15x200 matrix. We apply three different convolutions on each document. The three convolutions looks at 2, 3 and 4 words (kernel sizes) in each convolution. It then max-pools each over the whole document, so that you end up with one value per document per convolution. For each kernel size you do 100 different filters. Finally you add a dense layer for classification. In addition to the standard model described in the paper, we experimented with different kernel sizes, number of filters, dense layers, batch normalization and dropout. We also added several losses, so that the model optimized both the higher and lower category at the same time. That helped.

<figure>
   <img class="center-block" src="/images/2017-09-08-NLP-based-recommenders-at-finn/keras representation of our nlp model .jpg" alt="alt" title="Keras representation of our NLP model" />
   <figcaption style="text-align:center; font-style:italic;">Keras representation of our NLP model</figcaption>
</figure>


So how did it go? Our hackathon model managed to categorize 10'000 ads into 359 categories with an accuracy of 50%. We were surprised it worked so well, it was about the same accuracy image models have achieved on roughly the same ads. After tuning and pruning it further and adding 1 million data points, we have reached an accuracy of 70% on the 359 classes. In comparison, the bag-of-words model we started with managed an accuracy rate of around 25% and image recognition models have reached accuracies around 50%.



##Using the model in recommendations
It is usually the case that category-similarity translates decently to ad-similarity. Using our classifier model we can serve users more ads similar to what they’re already seeing, based on the text of a selected ad.

We use the model by cutting the last dense layer (called feature layer in figure above), then comparing normalized dot products (cosine similarity) between objects. Since our our benchmarks for judging anything a success or failure is based on how it performs in a production environment, we went ahead and did that. 
This gave us decent results using only text, performing about 5-6% percent worse than our top collaborative filtering approach. When we made an ensemble model combining text and collaborative filtering we managed to improve our existing best model by about 10%.
This is likely due to better supporting “cold ads”, or ads without traffic, while still retaining the accuracy of the collaborative filtering-model.

<figure>
   <img class="center-block" src="/images/2017-09-08-NLP-based-recommenders-at-finn/cold ad.jpg" alt="alt" title="Cold ad" />
   <figcaption style="text-align:center; font-style:italic;">Cold ad</figcaption>
</figure>

<figure>
   <img class="center-block" src="/images/2017-09-08-NLP-based-recommenders-at-finn/collaborative filtering.jpg" alt="alt" title="Collaborative filtering" />
   <figcaption style="text-align:center; font-style:italic;">Collaborative filtering</figcaption>
</figure>

<figure>
   <img class="center-block" src="/images/2017-09-08-NLP-based-recommenders-at-finn/nlp recommendations.jpg" alt="alt" title="NLP Recommendations" />
   <figcaption style="text-align:center; font-style:italic;">NLP Recommendations</figcaption>
</figure>

##Further work
The pure text-model does not prioritize the popularity (or perhaps by proxy, how good the ad is) of the ad at all. This leads us to suspect that although users are being directed to similar ads, they could for example be missing an enticing image to make engagement likely. Seeing how the ensemble model in the end is optimized for click-rate, it likely only gives the NLP model high priority when the ad has low traffic. It would be interesting to somehow introduce this aspect into the NLP model.

We would like to eventually have a more thorough NLP representation of all our ads for other teams to build services and functionality on, and this recommender is an important first step to achieve that.

[Resources](https://arxiv.org/pdf/1408.5882.pdf) 

