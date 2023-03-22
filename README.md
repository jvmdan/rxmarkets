# rxmarkets

## Introduction 

A reactive application for real-time assessment of market sentiment vs. risk in equity trading.

Highly concurrent data-driven processing built upon a Quarkus & Vert.x stack using JDK 17. 

## The Concept
Financial markets are often emotionally driven. In theory, the value of a company should be set by a free market, 
driven by the underlying business fundamentals. But the free market is operated by humans & we have feelings. 
Whether consciously or not, your <strong>valuation of an asset is influenced by your opinion</strong> on the greater world around it.

In practice, the market is very good at collectively valuing a company based upon the information available. But <strong>sometimes it is wrong</strong>. And when it is wrong, this is where we can capitalise and make money. RxMarkets make use of <strong>advanced data science techniques</strong> to provide a platform for identifying anomolies in the current price of tradable assets.

## Analysing Sentiment
Markets being wrong is often a result of <strong>market sentiment</strong>. Human emotions muddy decision making, 
and prices are affected accordingly. For example, a plane crash will often cause airline stocks to dip immediately
because industry sentiment changes overnight. The underlying need for air travel does not. The airlines keep selling tickets, keep making profits, and the 'true' valuation momentarily drifts apart from the current market price. 

Now that is an obvious example & you are unlikely to be able to capitalise on something so predictable. But what if there are thousands of assets trading every day that are <strong>undervalued or overvalued</strong> as a result of unconcious bias affecting asset prices? If you can detect such situations, you can profit from it.

Financial organisations (such as hedge funds) know this and so they attempt to measure sentiment in a number of ways. Until now, such financial modelling tools were not available to the public. For a large part, their data analysis is built upon proprietary data collection techniques & processed manually. This is slow, cumbersome & prone to error.

## Our Product
We aim to do better. We use a proprietary <strong>artificial intelligence</strong> (AI) engine to analyse <strong>hundreds of thousands</strong> of pieces of data <strong>every single day</strong>. Using this information, we provide a platform for quantifying & monitoring sentiment across a total of <strong>8 categories</strong>. Such categories include; industry sentiment, political exposure, confidence in board, employee satisfaction & more.

The autonomous aggregation of this data allows our customers to detect (and capitalise on) anomolies in pricing due to sudden changes in market sentiment. Our platform is <strong>reactive</strong>, meaning that we can assess the market sentiment of an asset <strong>right now</strong> and act upon it. 

We provide a web platform for viewing the data, along with a comprehensive API for wiring into existing financial modelling tools. This makes <strong>RxMarkets</strong> a viable option for individual traders & investment organisations alike. 

## Prerequisites

The application expects you to have a running postgreSQL instance for the purpose of testing the reactive database layer. Fortunately, if you don't have postgres installed, Quarkus will pull a container & create the necessary schemas for you automatically. For this to work you must have `Docker Engine` installed.

Developers are also advised to install the `Quarkus Run Configs` plugin for their IDE. This allows you to strap a debugger to the Quarkus application with ease. Otherwise, you must connect a remote debugger yourself, which can be cumbersome to do each time.