# Stock
The application is designed and developed to demonstrate the Model View ViewModel(MVVM) architectural pattern in Kotlin.
Single activity architecture is followed using navigation jetpack component

# Supported functionaries
The application allows the user to view stock list and mark the stock as favourite.
User can view stock details, with the price refreshed every 15 seconds.
User can switch between dark and light mode and see the application version

# Idea
!(Structure)[https://github.com/SupriyaNaveen/Stock/blob/master/docs/1620217123467.jpg]

# Libraries
Navigation <br/>
ViewModel <br/>
Retrofit <br/>
OkHttp <br/>
Kotlin Serialization <br/>
Hilt <br/>
Room <br/>
Ktx <br/>
RxKotlin <br/>
Paging <br/>
Coil <br/>
LiveData <br/>
Coroutines <br/>
UI: RecyclerView, CardView, ConstraintLayout.

# Reference
https://developer.android.com/topic/libraries/architecture/index.html

# TODO
Added few comments in code, imp notes here
* Fragment level test setup with hilt.
* Improve unit testing with paging
* Data sync with WorkManager with retry limit
* Error handling
* Color setup for dark and light mode
* MockWebServer setup for api level testing
