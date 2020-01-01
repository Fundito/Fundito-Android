 # Fundito - Android

**SOPT 25th AppJam**



## [ About ] 

Fund your taste **Fundito** 


## [ Develop Environment ]

- Kotlin : 1.3.61
- Android Gradle Plugin : 3.5.3

## [ Libraries ]

- Android Material : Material Design implementation Views by Google

- Kotlin Standard Library : Kotlin graceful helper features

- ConstraintLayout(MotionLayout) : MotionLayout for custom animating + Basic Layout

- AAC - ViewModel : for ViewModel

- AAC - LiveData : LiveData

- AAC - Room : Recent Search Keyword entities

- Gson : Json (De)Serialization

- OkHttp3 : Robust Http Connection

- Retrofit2 : RESTful API top of OkHttp3

- Glide : Image download, caching

- Timber : Handiful Logging

- Dagger2 : Dependency Injection

- Dexter : Permission grant helper

- Kotlin Coroutine : Asyncronized Behavior and Data retriving

- Coroutine Flow Binding : like RxBinding, Search keyword debounce stream

- AutofitTextView : TextView fit in llimit container automatically adjust text size

- Firebase Auth : Firebase Login

- Facebook Auth SDK : Facebook GraphAPI for get access token of Facebook

- Facebook Share SDK : Store Cheer share

- Firebase FCM : Server Notification

- CircleImageView : rounded ImageView

- JUnit4 : Local Unit Test or Instrumentation test based on JUnit

- Mockito : Mocking or Stubing test doubles

- Truth : Test Assertion


## [ Package Structure ]

- application : Application Class

- common : Kotlin Extension, Data Binding Adapter funtions, Constants 
- common/util : Utility/Helper class
- common/view : Custom View
- common/widget : Custom Class for utility

- data/database : Room
- data/model : Entity
- data/service : Network Client/Service using Retrofit

- di : Application Component, Custom Scopes for Dagger
- di/module : Dependency Modules

- presentation/** : package by feature (Activity, Fragment, Adapter etc...)

- test/** : Local Unit Test for extension functions

## [ Project Architecture ]

MVVM using Dagger, AAC ViewModel, Data Binding

## [ Feature ]

0. **Splash** (Lottie)

1. **Virtual Card Charging** (Custom SoftInput keyboard dialog)

2. **Fund to favorite store** (Custom money input View, animation)

3. **Sign In** (Facebook SDK)

4. **Store Information** (Custom Animation, CoordinatorLayout + MotionLayout)

5. **Feed** (Friend Fund list)

6. **My Funding Status** (current ongoing funding, status, Animation, BottomSheet, SystemUI Change)

7. **Home**

8. **More**

9. **Notification** (FCM, token register, Notification API)

10. **Search** (query with keyword using debounce operator)

11. **Store Cheering(Share)** (Facebook SDK for sharing link + Firebase DynamicLink + DeepLink using Intent)

12. **Wifi Connection** (WifiManager + ConnectivityManager)

## [Custom View + Edge Feature]

<img src="1.gif" width=200/> <img src="2.gif" width=200/> <img src="3.gif" width=200/> <img src="4.gif" width=200/>

### FundingProgressView

- Calculate spacing between circles and draw circle in **onDraw()**
- Distinct color of each circle with current progress value.

### FundingGraphView

- Clip View with drawPath in **onDraw** and draw stroke and background
- Fill background of graph with gradient color with clipping
- Animate value when progress is changed using **startAnimation** method

### InvestmentDialView

- Draw small, middle size grid in HorizontalScrollView(Custom ListenableHorizontalScrollView)
- Draw center long line for indicate current value
- When view is scrolled, round the offset -> calculate current nearest value remained unit of 100₩
- Using Animator, start TextView color, transformation animation
- Snap nearest value remained unit of 100₩
- Call method of custom value change listener instance

### Overlap BottomSheet + SystemUI + Scene

- Little bit Massive

### Custom Keyboard Dialog


### Using Wifi Connectivity for Server Request





## [ Team ]

[문명주] ,[이성은], [이도경]

