# Image Gallery

### Table of Contents

- [Description](#description)
- [Architecture](#architecture)
- [How To Use](#how-to-use)
- [License](#license)
- [Acknowledgments](#acknowledgments)
- [Author Info](#author-info)

---

## Description

In this project I tried to make a simple image list and details presentation application with Native Android. Backend is powered by [Unsplash API](https://unsplash.com/documentation#list-photos). Used minimal third party library support. This project covers di, api response cache, unit test coverage.

#### Tech stack

- MVVM Design Pattern
- Viewmodel
- Flow
- Coroutine
- Paging 3
- Dagger Hilt
- Jetpack Navigation
- Data binding
- Glide
- Okhttp
- Retrofit
- Diffutil
- Mockwebserver
- Unit Test
- Truth
- CI with Github actions

## Architecture

Multi modular project structure

- [ app ]
  - [ android test ]
    - [ mocprovider ]
  - [ main ]
    - [ data ]
    - [ model ]
    - [ view ]
      - [ gallery ]
      - [ imagedetails ]
  - [ test ]
    - [ data ]
    - [ view ]
      - [ gallery ]
  - [ test-common ]
- [ base ]
  - [ main ]
    - [ event ]
    - [ network ]
      - [ model ]
    - [ utils ]
    - [ view ]
    - [ viewmodel ]

[Back To The Top](#read-me-template)

## How To Use

- Clone this repository from `master` branch.
- Get a client authorization key from [this link](https://unsplash.com/developers).
- Replace the UnsplashApiToken from the gradle.properties
- Build the application

[Back To The Top](#read-me-template)

---

## License

MIT License

Copyright (c) [2022] [Amit Kundu]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

[Back To The Top](#read-me-template)

---

## Acknowledgments

- [NetworkResponseAdapter](https://github.com/haroldadmin/NetworkResponseAdapter)
- [PagingWithNetworkSample](https://github.com/android/architecture-components-samples/tree/main/PagingWithNetworkSample)
- [Unit Testing With MockWebServer](https://github.com/Farhandroid/Pexel)
- [Testing Retrofit converter with Mock Webserver](https://proandroiddev.com/testing-retrofit-converter-with-mock-webserver-50f3e1f54013)

## Author Info

- Twitter - [@amit517](https://twitter.com/amit517)
- Linkedin - [Amit Kundu](https://www.linkedin.com/in/amit-kundu-345a79119/)

[Back To The Top](#read-me-template)
