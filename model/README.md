# RxMarkets ~ Java Model

The 'model' module is a collection of Java classes & data objects used to facilitate the rest of the application. This module makes use of new Java features (such as records) and so will require <strong>JDK 17</strong> or newer to compile successfully.

This is a common dependency used amongst all other modules in the application. The idea is that multiple modules can make use of the 'model' without having to duplicate code.

Services within the application shall be decoupled so that no two modules compile against eachother. The only permitted compilation dependency is this module (the 'model').