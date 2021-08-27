#### MVVMDemo

A playground project using [Android JetPack app architecture](https://developer.android.com/jetpack/guide) and architecture techniques.

The app fetch `Repos` from the [github api](https://api.github.com/)

The Endpoint is [here.](https://api.github.com/search/repositories?sort=stars&q=Kotlin:name,description&page=1)


Use:
- Kotlin
- MVVM.
- UseCases
  - FetchDataUseCase
  - FetchNextDataUseCase
  - DeleteAllUseCase
  - DeleteAllCacheUseCase
- Repositories:
  - CacheRepository (memory)
  - LocalRepository (Room)
  - RemoteRepository (Retrofit)
- Coroutines
- DataBinding
- Navigation
- Hilt
- Leak Canary
- Timber
- Unit Test
- Espresso Test (Barista)
