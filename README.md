#### MVVMDemo

A playground project using [Android JetPack app architecture guide][1]
The app fetch `Repos` from the [Github Api][2].
The Endpoint is [here][3].


Use:
- [Kotlin][KOTLIN]
- [Coroutines][COROUTINES]
- [JetPack][JETPACK]
- [Architecture MVVM][ARCHITECTURE]
- [Data binding][DATA_BINDING]
- [Navigation][NAVIGATION]
- [Hilt][HILT] for DI. [Hilt Training][HILT_TRAINING]. [Hilt Release][HIL_RELEASE].
- [Room][ROOM] for local database.
- [Glide][GLIDE] for image loading.
- [Retrofit][RETROFIT] for REST api communication.
- [Retrofit Mock][RETROFIT_MOCK] for creating a fake API implementation for tests.
- [Espresso][ESPRESSO] for UI tests.
- [Mockito][MOCKITO] for mocking in tests.
- [Barista][BARISTA]
- [Unit Tests][UNIT_TEST]
- [Leak Canary][LEAK_CANARY]
- [Timber][TIMBER]

- UseCases
  - FetchDatasUseCase
  - FetchNextDatasUseCase
  - RefreshDatasUseCase
  - DeleteAllUseCase
  - DeleteAllCacheUseCase
- Repositories:
  - CacheRepository (memory)
  - LocalRepository (Room)
  - RemoteRepository (Retrofit)


[KOTLIN]: https://kotlinlang.org/docs/home.html
[COROUTINES]: https://developer.android.com/kotlin/coroutines

[JETPACK]: https://developer.android.com/jetpack
[ARCHITECTURE]: https://developer.android.com/jetpack/guide
[HILT]: https://github.com/googlecodelabs/android-hilt
[HILT_TRAINING]: https://developer.android.com/training/dependency-injection/hilt-android
[HIL_RELEASE]: https://developer.android.com/jetpack/androidx/releases/hilt
[ROOM]: https://developer.android.com/jetpack/androidx/releases/room
[LIFECYCLE_OBSERVER]: https://developer.android.com/topic/libraries/architecture/lifecycle
[NAVIGATION]: https://developer.android.com/guide/navigation
[DATA_BINDING]: https://developer.android.com/topic/libraries/data-binding/index.html

[GLIDE]: https://github.com/bumptech/glide
[RETROFIT]: https://github.com/square/retrofit
[RETROFIT_MOCK]: https://github.com/square/retrofit/tree/master/retrofit-mock

[MOCK_WEBSERVER]: https://github.com/square/okhttp/tree/master/mockwebserver
[ESPRESSO]: https://google.github.io/android-testing-support-library/docs/espresso
[ESPRESSO_2]: https://developer.android.com/training/testing/ui-testing/espresso-testing
[BARISTA]: https://github.com/AdevintaSpain/Barista
[UNIT_TEST]: https://developer.android.com/training/testing/unit-testing

[MOCKITO]: http://site.mockito.org

[LEAK_CANARY]: https://github.com/square/leakcanary
[TIMBER]: https://github.com/JakeWharton/timber

[1]: https://developer.android.com/jetpack/guide).
[2]: https://api.github.com/
[3]: https://api.github.com/search/repositories?sort=stars&q=Kotlin:name,description&page=1


[SUPPORT_LIB]: https://developer.android.com/topic/libraries/support-library/index.html
[ARCH]: https://developer.android.com/arch
