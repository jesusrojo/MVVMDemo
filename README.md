#### MVVMDemo

A playground project using [Android JetPack app architecture guide](https://developer.android.com/jetpack/guide).

The app fetch `Repos` from the [Github Api](https://api.github.com/).

The Endpoint is [here](https://api.github.com/search/repositories?sort=stars&q=Kotlin:name,description&page=1).


Use:
- Kotlin
- MVVM.
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
- Coroutines
- DataBinding
- Navigation
- Hilt
- Leak Canary
- Timber
- Unit Test
- Espresso Test (Barista)
