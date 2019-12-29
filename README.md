 # Fundito - Android

**SOPT 25th AppJam**



## [ About ] 

Fund your taste **Fundito** 


## [ Develop Environment ]

- Kotlin : 1.3.61
- Android Gradle Plugin : 3.5.3

## [ Libraries ]

- Android Material
- Kotlin Standard Library
- ConstraintLayout(MotionLayout)
- AAC - ViewModel
- AAC - LiveData
- AAC - Room
- Gson
- OkHttp3
- Retrofit2
- Glide
- Timber
- Dagger2
- Dexter
- Kotlin Coroutine
- Coroutine Flow Binding
- AutofitTextView
- Firebase Auth
- CircleImageView

- JUnit4
- Mockito
- Truth


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




## [ Team ]

[문명주] ,[이성은], [이도경]

