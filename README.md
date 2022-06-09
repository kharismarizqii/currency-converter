<h1 align="center">Currency Converter</h1>

<p align="center">  
💵 Currency Converter demonstrates Android development with Hilt, Retrofit, RxJava and Jetpack (Room, ViewModel) based on MVVM architecture.
</p>
</br>

<img src="democonverter.gif" align="right" width="32%"/>

## Tech stack & Open-source libraries
- Minimum SDK Level 16
- [Kotlin](https://kotlinlang.org/) based.
- [Hilt](https://dagger.dev/hilt/) for dependency injection.
- Jetpack
  - Lifecycle - Observe Android lifecycles and handle UI states upon the lifecycle changes.
  - ViewModel - Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
  - ViewBinding - Generates a binding class for each XML layout file that allows you to more easily write code that interacts with views.
  - Room Persistence - Constructs Database by providing an abstraction layer over SQLite to allow fluent database access.
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - Construct the REST APIs.
- [RxJava](https://github.com/ReactiveX/RxJava) & [RxAndroid](https://github.com/ReactiveX/RxAndroid) - For asynchronous programming with observable streams.
- [RxBinding](https://github.com/JakeWharton/RxBinding) - RxJava binding APIs for Android UI widgets.
- [Lottie](https://github.com/airbnb/lottie-android) - Parses Adobe After Effects animations exported as json with Bodymovin and renders them natively on mobile.

## Architecture
Currency Converter is based on the MVVM architecture and the Repository pattern.

![architecture](https://user-images.githubusercontent.com/24237865/77502018-f7d36000-6e9c-11ea-92b0-1097240c8689.png)

# License
```xml
Designed and developed by 2021 kharismarizqii

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
